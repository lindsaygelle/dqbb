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

    private fun checkActorAction(
        action: Action<Actor, Actor>, actor: Actor, actors: Collection<Actor>, index: Int,
    ): Boolean {
        logger.info(
            "action.ability.id={} action.id={} actor.id={} actors.size={} id={} index={} turn={}",
            action.ability?.id,
            action.id,
            actor.id,
            actors.size,
            id,
            index,
            turn
        )
        return action.use(actor, actors)
    }

    private fun checkActorActions(actor: Actor): Boolean {
        logger.info(
            "actor.actions.size={} actor.id={} id={} simpleName={} turn={}",
            actor.actions.size,
            actor.id,
            id,
            simpleName,
            turn
        )
        return actor.actions.isNotEmpty()
    }

    private fun checkActorHitPoints(actor: Actor): Boolean {
        logger.info(
            "actor.hitPoints={} actor.hitPointsMaximum={} actor.id={} id={} simpleName={} turn={}",
            actor.hitPoints,
            actor.hitPointsMaximum,
            actor.id,
            id,
            turn,
            simpleName
        )
        return actor.hitPoints > 0
    }

    private fun checkActorRemoval(actor: Actor): Boolean {
        logger.trace(
            "actor.allegiance={} actor.id={} id={} operation=checkActorRemoval simpleName={} turn={}",
            actor.allegiance,
            actor.id,
            id,
            simpleName,
            turn
        )
        return !checkActorActions(actor) || !checkActorHitPoints(actor) || checkActorRunning(actor)
    }

    private fun checkActorRunning(actor: Actor): Boolean {
        logger.info(
            "actor.id={} actor.isRunning={} id={} simpleName={} turn={}",
            actor.id,
            actor.isRunning,
            id,
            simpleName,
            turn
        )
        return actor.isRunning
    }

    private fun checkActorSleepResolution(actor: Actor): Boolean {
        val sleepResolution = actor.sleepResolution
        logger.info(
            "actor.id={} actor.sleepResolution={} actor.sleepResolutionMaximum={} actor.sleepResolutionMinimum={} id={} simpleName={} turn={}",
            actor.id,
            sleepResolution,
            actor.sleepResolutionMaximum,
            actor.sleepResolutionMinimum,
            id,
            turn,
            simpleName
        )
        return sleepResolution == actor.sleepResolutionMaximum
    }

    private fun checkActorStopSpellResolution(actor: Actor): Boolean {
        val stopSpellResolution = actor.stopSpellResolution
        logger.info(
            "actor.id={} actor.stopSpellResolution={} actor.stopSpellResolutionMaximum={} actor.stopSpellResolutionMinimum={} id={} simpleName={} turn={}",
            actor.id,
            stopSpellResolution,
            actor.stopSpellResolutionMaximum,
            actor.stopSpellResolutionMinimum,
            id,
            turn,
            simpleName
        )
        return stopSpellResolution == actor.stopSpellResolutionMaximum
    }

    private fun checkActorTurnsSleep(actor: Actor): Boolean {
        logger.info(
            "actor.id={} actor.turnsSleep={} id={} simpleName={} turn={}",
            actor.id,
            actor.turnsSleep,
            id,
            simpleName,
            turn
        )
        return actor.turnsSleep > 0
    }

    private fun checkActorTurnsSleepMinimum(actor: Actor): Boolean {
        logger.info(
            "actor.id={} actor.turnsSleep={} actor.turnsSleepMinimum={} id={} simpleName={} turn={}",
            actor.id,
            actor.turnsSleep,
            actor.turnsSleepMinimum,
            id,
            turn,
            simpleName
        )
        return actor.turnsSleep > actor.turnsSleepMinimum
    }

    private fun checkActorTurnsStopSpell(actor: Actor): Boolean {
        logger.info(
            "actor.id={} actor.turnsStopSpell={} id={} simpleName={} turn={}",
            actor.id,
            actor.turnsStopSpell,
            id,
            simpleName,
            turn
        )
        return actor.turnsStopSpell > 0
    }

    private fun checkActorTurnsStopSpellMinimum(actor: Actor): Boolean {
        logger.info(
            "actor.id={} actor.turnsStopSpell={} actor.turnsStopSpellMinimum={} id={} simpleName={} turn={}",
            actor.id,
            actor.turnsStopSpell,
            actor.turnsStopSpellMinimum,
            id,
            turn,
            simpleName
        )
        return actor.turnsStopSpell > actor.turnsStopSpellMinimum
    }

    private fun getActorOrder(actor: Actor): Int? {
        val attributeValue = actor.getAttribute(attributeName)
        logger.info(
            "actor.id={} attributeName={} attributeValue={} id={} simpleName={} turn={}",
            actor.id,
            attributeName,
            attributeValue,
            id,
            turn,
            simpleName
        )
        return attributeValue
    }

    private fun handleActor(actor: Actor, actors: Collection<Actor>, index: Int) {
        logger.info(
            "actor.id={} actors.size={} id={} index={} simpleName={} turn={}",
            actor.id,
            actors.size,
            id,
            index,
            simpleName,
            turn
        )
        actor.index = index
        handleActorSleep(actor)
        handleActorStopSpell(actor)
        handleActorActions(actor, actors)
    }

    private fun handleActors(actors: Collection<Actor>) {
        logger.trace(
            "actors.size={} id={} operation=handleActors simpleName={} turn={}", actors.size, id, simpleName, turn
        )
        actors.forEachIndexed { index, actor ->
            handleActor(actor, actors, index)
        }
    }

    private fun handleActorActions(actor: Actor, actors: Collection<Actor>) {
        logger.info(
            "actor.actions.size={} actor.id={} actors.size={} id={} simpleName={} turn={}",
            actor.actions.size,
            actor.id,
            actors.size,
            id,
            simpleName,
            turn
        )
        actor.actions.withIndex().any { (index: Int, action: Action<Actor, Actor>) ->
            checkActorAction(action, actor, actors, index)
        }
    }

    private fun handleActorSleep(actor: Actor) {
        if (checkActorTurnsSleep(actor)) {
            actor.turnsSleep += 1
            if (checkActorTurnsSleepMinimum(actor) && checkActorSleepResolution(actor)) {
                actor.turnsSleep = 0
            }
        }
        logger.info(
            "actor.id={} actor.turnsSleep={} id={} simpleName={} turn={}",
            actor.id,
            actor.turnsSleep,
            id,
            simpleName,
            turn
        )
    }

    private fun handleActorStopSpell(actor: Actor) {
        if (checkActorTurnsStopSpell(actor)) {
            actor.turnsStopSpell += 1
            if (checkActorTurnsStopSpellMinimum(actor) && checkActorStopSpellResolution(actor)) {
                actor.turnsStopSpell = 0
            }
        }
        logger.info(
            "actor.id={} actor.turnsStopSpell={} id={} simpleName={} turn={}",
            actor.id,
            actor.turnsStopSpell,
            id,
            simpleName,
            turn
        )
    }

    fun hasNext(): Boolean {
        return isActive
    }

    override fun run() {
        logger.info(
            "actors.size={} allegiances.size={} id={} simpleName={} turn={}",
            actors.size,
            allegiances.size,
            id,
            simpleName,
            turn,
        )
        allegiances.clear()
        handleActors(sortActors(actors))
        removeActors(actors, allegiances)
        isActive = allegiances.size > 1
        turn += 1
    }

    private fun removeActors(actors: MutableSet<Actor>, allegiances: MutableSet<Int>) {
        val checkValue = actors.removeAll { actor: Actor ->
            val removeActor = checkActorRemoval(actor)
            if (!removeActor) {
                allegiances.add(actor.allegiance)
            }
            removeActor
        }
        logger.info(
            "actors.size={} allegiances.size={} checkValue={} id={} simpleName={} turn={}",
            actors.size,
            allegiances.size,
            checkValue,
            id,
            simpleName,
            turn
        )
    }

    private fun sortActors(actors: Collection<Actor>): Collection<Actor> {
        logger.info(
            "actors.size={} id={} simpleName={} turn={}", actors.size, id, simpleName, turn
        )
        return actors.sortedByDescending { actor: Actor ->
            getActorOrder(actor)
        }
    }
}