package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class BattleSystem : Identifier,
    Runnable,
    TurnAccumulator {
    val actors: MutableSet<Actor> = mutableSetOf()

    private val allegiances: MutableSet<Int> = mutableSetOf()

    private var attributeName: AttributeName = AttributeName.AGILITY
        set(value) {
            field = value
            logger.debug(
                "attributeName={} id={}", field, id
            )
        }

    private var isActive: Boolean = true
        set(value) {
            field = value
            logger.debug(
                "id={} isActive={}", id, field
            )
        }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override var turn: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} turn={}", id, field
            )
        }

    private fun checkActorActions(actor: Actor): Boolean {
        logger.info(
            "actor.actions.size={} actor.id={} id={} turn={}", actor.actions.size, actor.id, id, turn
        )
        return actor.actions.isNotEmpty()
    }

    private fun checkActorHitPoints(actor: Actor): Boolean {
        logger.info(
            "actor.hitPoints={} actor.hitPointsMaximum={} actor.id={} id={} turn={}",
            actor.hitPoints,
            actor.hitPointsMaximum,
            actor.id,
            id,
            turn
        )
        return actor.hitPoints > 0
    }

    private fun checkActorRemoval(actor: Actor): Boolean {
        return !checkActorActions(actor) || !checkActorHitPoints(actor) || checkActorRunning(actor)
    }

    private fun checkActorRunning(actor: Actor): Boolean {
        logger.info(
            "actor.id={} actor.isRunning={} id={} turn={}", actor.id, actor.isRunning, id, turn
        )
        return actor.isRunning
    }

    private fun checkActorSleepResolution(actor: Actor): Boolean {
        val sleepResolution = actor.sleepResolution
        logger.info(
            "actor.id={} actor.sleepResolution={} actor.sleepResolutionMaximum={} actor.sleepResolutionMinimum={} id={} turn={}",
            actor.id,
            sleepResolution,
            actor.sleepResolutionMaximum,
            actor.sleepResolutionMinimum,
            id,
            turn
        )
        return sleepResolution == actor.sleepResolutionMaximum
    }

    private fun checkActorStopSpellResolution(actor: Actor): Boolean {
        val stopSpellResolution = actor.stopSpellResolution
        logger.info(
            "actor.id={} actor.stopSpellResolution={} actor.stopSpellResolutionMaximum={} actor.stopSpellResolutionMinimum={} id={} turn={}",
            actor.id,
            stopSpellResolution,
            actor.stopSpellResolutionMaximum,
            actor.stopSpellResolutionMinimum,
            id,
            turn
        )
        return stopSpellResolution == actor.stopSpellResolutionMaximum
    }

    private fun checkActorTurnsSleep(actor: Actor): Boolean {
        logger.info(
            "actor.id={} actor.turnsSleep={} id={} turn={}", actor.id, actor.turnsSleep, id, turn
        )
        return actor.turnsSleep > 0
    }

    private fun checkActorTurnsSleepMinimum(actor: Actor): Boolean {
        logger.info(
            "actor.id={} actor.turnsSleep={} actor.turnsSleepMinimum={} id={} turn={}",
            actor.id,
            actor.turnsSleep,
            actor.turnsSleepMinimum,
            id,
            turn
        )
        return actor.turnsSleep > actor.turnsSleepMinimum
    }

    private fun checkActorTurnsStopSpell(actor: Actor): Boolean {
        logger.info(
            "actor.id={} actor.turnsStopSpell={} id={} turn={}", actor.id, actor.turnsStopSpell, id, turn
        )
        return actor.turnsStopSpell > 0
    }

    private fun checkActorTurnsStopSpellMinimum(actor: Actor): Boolean {
        logger.info(
            "actor.id={} actor.turnsStopSpell={} actor.turnsStopSpellMinimum={} id={} turn={}",
            actor.id,
            actor.turnsStopSpell,
            actor.turnsStopSpellMinimum,
            id,
            turn
        )
        return actor.turnsStopSpell > actor.turnsStopSpellMinimum
    }

    private fun getActorOrder(actor: Actor): Int? {
        val attributeValue = actor.getAttribute(attributeName)
        logger.info(
            "actor.id={} attributeName={} attributeValue={} id={} turn={}",
            actor.id,
            attributeName,
            attributeValue,
            id,
            turn
        )
        return attributeValue
    }

    private fun handleActor(actor: Actor, actors: Collection<Actor>, index: Int) {
        logger.info(
            "actor.id={} actors.size={} id={} index={} turn={}", actor.id, actors.size, id, index, turn
        )
        actor.index = index
        handleActorSleep(actor)
        handleActorStopSpell(actor)
        handleActorActions(actor, actors)
    }

    private fun handleActors(actors: Collection<Actor>) {
        actors.forEachIndexed { index, actor ->
            handleActor(actor, actors, index)
        }
    }

    private fun handleActorAction(action: Action, actor: Actor, actors: Collection<Actor>, index: Int) {
        logger.info(
            "action.id={} actor.id={} actors.size={} id={} index={} turn={}",
            action.id,
            actor.id,
            actors.size,
            id,
            index,
            turn
        )
    }

    private fun handleActorActions(actor: Actor, actors: Collection<Actor>) {
        actor.actions.forEachIndexed { index, action ->
            handleActorAction(action, actor, actors, index)
        }
    }

    private fun handleActorSleep(actor: Actor) {
        if (checkActorTurnsSleep(actor)) {
            actor.turnsSleep += 1
            if (checkActorTurnsSleepMinimum(actor) && checkActorSleepResolution(actor)) {
                actor.turnsSleep = 0
            }
        }
    }

    private fun handleActorStopSpell(actor: Actor) {
        if (checkActorTurnsStopSpell(actor)) {
            actor.turnsSleep += 1
            if (checkActorTurnsStopSpellMinimum(actor) && checkActorStopSpellResolution(actor)) {
                actor.turnsStopSpell = 0
            }
        }
    }

    fun hasNext(): Boolean {
        return isActive
    }

    override fun run() {
        logger.info(
            "actors.size={} allegiances.size={} id={} turn={}", actors.size, allegiances.size, id, turn,
        )
        allegiances.clear()
        handleActors(sortActors(actors))
        removeActors(actors, allegiances)
        isActive = allegiances.size > 1
        turn += 1
    }

    private fun removeActors(actors: MutableSet<Actor>, allegiances: MutableSet<Int>) {
        actors.removeAll { actor: Actor ->
            val removeActor = checkActorRemoval(actor)
            if (!removeActor) {
                allegiances.add(actor.allegiance)
            }
            removeActor
        }
    }

    private fun sortActors(actors: Collection<Actor>): Collection<Actor> {
        return actors.sortedByDescending { actor: Actor ->
            getActorOrder(actor)
        }
    }
}