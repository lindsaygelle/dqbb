package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class BattleSystem : Identifier,
    Runnable,
    TurnAccumulator {
    val actors: MutableSet<Actor> = mutableSetOf()
    private val allegiances: MutableSet<Int> = mutableSetOf()
    var attributeName: AttributeName = AttributeName.AGILITY
    private var isActive: Boolean = true
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)
    override var turn: Int = 0

    private fun checkActorHitPoints(actor: Actor): Boolean {
        logger.debug(
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
        return !checkActorHitPoints(actor) || checkActorRunning(actor)
    }

    private fun checkActorRunning(actor: Actor): Boolean {
        logger.debug(
            "actor.id={} actor.isRunning={} id={} turn={}", actor.id, actor.isRunning, id, turn
        )
        return actor.isRunning
    }

    private fun checkActorSleepResolution(actor: Actor): Boolean {
        val sleepResolution = actor.sleepResolution
        logger.debug(
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
        logger.debug(
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
        logger.debug(
            "actor.id={} actor.turnsSleep={} id={} turn={}", actor.id, actor.turnsSleep, id, turn
        )
        return actor.turnsSleep > 0
    }

    private fun checkActorTurnsSleepMinimum(actor: Actor): Boolean {
        logger.debug(
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
        logger.debug(
            "actor.id={} actor.turnsStopSpell={} id={} turn={}", actor.id, actor.turnsStopSpell, id, turn
        )
        return actor.turnsStopSpell > 0
    }

    private fun checkActorTurnsStopSpellMinimum(actor: Actor): Boolean {
        logger.debug(
            "actor.id={} actor.turnsStopSpell={} actor.turnsStopSpellMinimum={} id={} turn={}",
            actor.id,
            actor.turnsStopSpell,
            actor.turnsStopSpellMinimum,
            id,
            turn
        )
        return actor.turnsStopSpell > actor.turnsStopSpellMinimum
    }

    private fun getActorOrder(actor: Actor): Int {
        val attributeValue = actor.getAttribute(attributeName)
        logger.debug(
            "actor.id={} attributeName={} attributeValue={} id={} turn={}",
            actor.id,
            attributeName,
            attributeValue,
            id,
            turn
        )
        return attributeValue
    }

    private fun handleActor(actor: Actor) {
        handleActorSleep(actor)
        handleActorStopSpell(actor)
        handleActorAction(actor)
    }

    private fun handleActorAction(actor: Actor) {
        val actionResult = actor.actions.any { it.use(actor, actors) }
        logger.info(
            "actionResult={} actor.id={} id={} turn={}", actionResult, actor.id, id, turn
        )
    }

    private fun handleActorSleep(actor: Actor) {
        if (checkActorTurnsSleep(actor)) {
            actor.turnsSleep += 1
            if (checkActorTurnsSleepMinimum(actor) && checkActorSleepResolution(actor)) {
                actor.turnsSleep = 0
            }
        }
        logger.info(
            "actor.id={} actor.turnsSleep={} id={} turn={}", actor.id, actor.turnsSleep, id, turn
        )
    }

    private fun handleActorStopSpell(actor: Actor) {
        if (checkActorTurnsStopSpell(actor)) {
            actor.turnsSleep += 1
            if (checkActorTurnsStopSpellMinimum(actor) && checkActorStopSpellResolution(actor)) {
                actor.turnsStopSpell = 0
            }
        }
        logger.info(
            "actor.id={} actor.turnsStopSpell={} id={} turn={}", actor.id, actor.turnsStopSpell, id, turn
        )
    }

    fun hasNext(): Boolean {
        return isActive
    }

    override fun run() {
        logger.info(
            "actors.size={} allegiances.size={} id={} turn={}", actors.size, allegiances.size, id, turn,
        )
        allegiances.clear()
        actors.sortedByDescending { actor ->
            getActorOrder(actor)
        }.forEachIndexed { index, actor ->
            actor.turn = turn
            logger.debug(
                "actor.id={} actor.turn={} id={} index={} turn={}", actor.id, actor.turn, id, index, turn
            )
            if (checkActorHitPoints(actor)) {
                handleActor(actor)
            }
        }
        actors.removeAll { actor ->
            val removeActor = checkActorRemoval(actor)
            if (!removeActor) {
                allegiances.add(actor.allegiance)
            }
            removeActor
        }
        isActive = allegiances.size > 1
        turn += 1
    }
}