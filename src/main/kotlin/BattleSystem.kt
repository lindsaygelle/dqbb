package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.*

class BattleSystem(
    actors: Collection<Actor>,
) : Identifier,
    Runnable {
    private val actors: MutableList<Actor> =
        actors.filter { actor -> actor.isAlive }.sortedByDescending { actor -> actor.agility }.toMutableList()

    private val allegiances: MutableSet<Int> = mutableSetOf()

    private var isActive: Boolean = true

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    var turnsBattle: Int = 0

    override val uuid: UUID = UUID.randomUUID()

    private fun checkActor(actor: Actor, index: Int) {
        actor.index = index
        actor.turnsBattle = turnsBattle
        logger.info(
            "actor.index={} actor.turnsBattle={} actor.uuid={} index={} turnsBattle={} uuid={}",
            actor.index,
            actor.turnsBattle,
            actor.uuid,
            index,
            turnsBattle,
            uuid
        )
        if (checkActorAlive(actor)) {
            checkActorSleep(actor)
            checkActorStopSpell(actor)
            checkActorAction(actor)
        }
    }

    private fun checkActorAction(actor: Actor) {
        logger.info(
            "actor.actorActions.size={} actor.index={} actor.turnsBattle={} actor.uuid={} turnsBattle={} uuid={}",
            actor.actorActions.size,
            actor.index,
            actor.turnsBattle,
            actor.uuid,
            turnsBattle,
            uuid
        )
        val checkResult = actor.performAction(actors)
        logger.info(
            "actor.uuid={} checkResult={} turnsBattle={} uuid={}", actor.uuid, checkResult, turnsBattle, uuid
        )
    }

    private fun checkActorAlive(actor: Actor): Boolean {
        val isAlive = actor.isAlive
        logger.debug(
            "actor.hitPoints={} actor.index={} actor.isAlive={} actor.turnsBattle={} actor.uuid={} turnsBattle={} uuid={}",
            actor.hitPoints,
            actor.index,
            isAlive,
            actor.turnsBattle,
            actor.uuid,
            turnsBattle,
            uuid
        )
        return isAlive
    }

    private fun checkActorRemoval(actor: Actor): Boolean {
        val checkResult = !checkActorAlive(actor) || checkActorStatusRunning(actor)
        logger.info(
            "actor.index={} actor.turnsBattle={} actor.uuid={} checkResult={} turnsBattle={} uuid={}",
            actor.index,
            actor.turnsBattle,
            actor.uuid,
            checkResult,
            turnsBattle,
            uuid
        )
        return checkResult
    }

    private fun checkActorSleep(actor: Actor) {
        if (checkActorStatusSleep(actor)) {
            actor.turnsSleep += 1
        }
        if (checkActorStatusSleep(actor)) {
            if (checkActorTurnsSleepMinimum(actor) && checkActorSleepResolution(actor)) {
                actor.turnsSleep = 0
            }
        }
        logger.info(
            "actor.index={} actor.turnsSleep={} actor.uuid={} turnsBattle={} uuid={}",
            actor.index,
            actor.turnsSleep,
            actor.uuid,
            turnsBattle,
            uuid
        )
    }

    private fun checkActorSleepResolution(actor: Actor): Boolean {
        val sleepResolution = actor.sleepResolution
        val checkResult = sleepResolution == actor.sleepResolutionMaximum
        logger.debug(
            "actor.index={} actor.sleepResolution={} actor.sleepResolutionMaximum={} actor.sleepResolutionMinimum={} actor.uuid={} checkResult={} turnsBattle={} uuid={}",
            actor.index,
            sleepResolution,
            actor.sleepResolutionMaximum,
            actor.sleepResolutionMinimum,
            actor.uuid,
            checkResult,
            turnsBattle,
            uuid
        )
        return checkResult
    }

    private fun checkActorStatusRunning(actor: Actor): Boolean {
        val statusRunning = actor.statusRunning
        logger.debug(
            "actor.index={} actor.statusRunning={} actor.turnsBattle={} actor.uuid={} turnsBattle={} uuid={}",
            actor.index,
            statusRunning,
            actor.turnsBattle,
            actor.uuid,
            turnsBattle,
            uuid
        )
        return statusRunning
    }

    private fun checkActorStatusSleep(actor: Actor): Boolean {
        val statusSleep = actor.statusSleep
        logger.debug(
            "actor.index={} actor.statusSleep={} actor.turnsBattle={} actor.uuid={} turnsBattle={} uuid={}",
            actor.index,
            statusSleep,
            actor.turnsBattle,
            actor.uuid,
            turnsBattle,
            uuid
        )
        return statusSleep
    }

    private fun checkActorStatusStopSpell(actor: Actor): Boolean {
        val statusStopSpell = actor.statusStopSpell
        logger.debug(
            "actor.index={} actor.turnsBattle={} actor.statusStopSpell={} actor.uuid={} turnsBattle={} uuid={}",
            actor.index,
            actor.turnsBattle,
            statusStopSpell,
            actor.uuid,
            turnsBattle,
            uuid
        )
        return statusStopSpell
    }

    private fun checkActorStopSpell(actor: Actor) {
        if (checkActorStatusStopSpell(actor)) {
            actor.turnsStopSpell += 1
        }

        if (checkActorStatusStopSpell(actor)) {
            if (checkActorTurnsStopSpellMinimum(actor) && checkActorStopSpellResolution(actor)) {
                actor.turnsStopSpell = 0
            }
        }
        logger.info(
            "actor.index={} actor.turnsBattle={} actor.turnsStopSpell={} actor.uuid={} turnsBattle={} uuid={}",
            actor.index,
            actor.turnsBattle,
            actor.turnsStopSpell,
            actor.uuid,
            turnsBattle,
            uuid
        )
    }

    private fun checkActorStopSpellResolution(actor: Actor): Boolean {
        val stopSpellResolution = actor.stopSpellResolution
        val checkResult = stopSpellResolution == actor.stopSpellResolutionMaximum
        logger.debug(
            "actor.index={} actor.stopSpellResolution={} actor.stopSpellResolutionMaximum={} actor.stopSpellResolutionMinimum={} actor.turnsBattle={} actor.uuid={} checkResult={} turnsBattle={} uuid={}",
            actor.index,
            stopSpellResolution,
            actor.stopSpellResolutionMinimum,
            actor.stopSpellResolutionMaximum,
            actor.turnsBattle,
            actor.uuid,
            checkResult,
            turnsBattle,
            uuid
        )
        return checkResult
    }

    private fun checkActorTurnsSleepMinimum(actor: Actor): Boolean {
        val checkResult = actor.turnsSleep > actor.turnsSleepMinimum
        logger.debug(
            "actor.index={} actor.turnsBattle={} actor.turnsSleep={} actor.turnsSleepMinimum={} actor.uuid={} checkResult={} turnsBattle={} uuid={}",
            actor.index,
            actor.turnsBattle,
            actor.turnsSleep,
            actor.turnsSleepMinimum,
            actor.uuid,
            checkResult,
            turnsBattle,
            uuid
        )
        return checkResult
    }

    private fun checkActorTurnsStopSpellMinimum(actor: Actor): Boolean {
        val checkResult = actor.turnsStopSpell > actor.turnsStopSpellMinimum
        logger.debug(
            "actor.index={} actor.turnsBattle={} actor.turnsStopSpell={} actor.turnsStopSpellMinimum={} actor.uuid={} checkResult={} turnsBattle={} uuid={}",
            actor.index,
            actor.turnsBattle,
            actor.turnsStopSpell,
            actor.turnsStopSpellMinimum,
            actor.uuid,
            checkResult,
            turnsBattle,
            uuid
        )
        return checkResult
    }

    fun hasNext(): Boolean {
        return isActive
    }

    override fun run() {
        turnsBattle += 1
        logger.info(
            "actors.size={} isActive={} turnsBattle={} uuid={}", actors.size, isActive, turnsBattle, uuid
        )
        allegiances.clear()
        actors.forEachIndexed { index, actor ->
            checkActor(actor, index)
        }
        actors.removeAll { actor ->
            val removeActor = checkActorRemoval(actor)
            if (!removeActor) {
                allegiances.add(actor.allegiance)
            }
            removeActor
        }
        isActive = allegiances.size > 1
        logger.info(
            "allegiances.size={} isActive={} turnsBattle={} uuid={}", allegiances.size, isActive, turnsBattle, uuid
        )
    }
}