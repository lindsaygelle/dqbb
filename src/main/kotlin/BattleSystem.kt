package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class BattleSystem(
    actors: Set<Actor>
) : Runnable {

    private val actors: MutableList<Actor> = actors.sortedByDescending { actor -> actor.agility }.toMutableList()

    var isActive: Boolean = true

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    val trail: MutableList<Trail> = mutableListOf()

    var turns: Int = 0

    private fun checkActor(actor: Actor, index: Int) {
        logger.debug(
            "$this: " +
                    "actor.actionPoints=${actor.actionPoints} " +
                    "actor.actionPointsMaximum=${actor.actionPointsMaximum} " +
                    "actor.agility=${actor.agility} " +
                    "actor.allegiance=${actor.allegiance} " +
                    "actor.armor.id=${actor.armor?.id} " +
                    "actor.armor.name=${actor.armor?.name} " +
                    "actor.damageResistanceMaximum=${actor.damageResistanceMaximum} " +
                    "actor.hitPoints=${actor.hitPoints} " +
                    "actor.hitPointsMaximum=${actor.hitPointsMaximum} " +
                    "actor.id=${actor.id} " +
                    "actor.magicPoints=${actor.magicPoints} " +
                    "actor.magicPointsMaximum=${actor.magicPointsMaximum} " +
                    "actor.shield=${actor.shield} " +
                    "actor.statusResistanceMaximum=${actor.statusResistanceMaximum} " +
                    "actor.strength=${actor.strength} " +
                    "actor.strengthMaximum=${actor.strengthMaximum} " +
                    "actor.turnsAlive=${actor.turnsAlive} " +
                    "actor.turnsSleep=${actor.turnsSleep} " +
                    "actor.turnsSleepMaximum=${actor.turnsSleepMaximum} " +
                    "actor.turnsStopSpell=${actor.turnsStopSpell} " +
                    "actor.turnsStopSpellMaximum=${actor.turnsStopSpellMaximum} " +
                    "actor.weapon=${actor.weapon} " +
                    "actors.size=${actors.size} " +
                    "index=$index"
        )
        if (!actor.isAlive) {
            this.trail.addAll(actor.trail)
            actor.trail.clear()
            return
        }
        actor.turnsAlive = this.turns
        val takeTurnResult = actor.takeTurn(this.actors)
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "actor.takeTurn=$takeTurnResult"
        )
        checkActorSleep(actor)
        checkActorStopSpell(actor)
        this.trail.addAll(actor.trail)
        actor.trail.clear()
    }

    private fun checkActorSleep(actor: Actor) {
        if (!actor.statusSleep) {
            return
        }
        val wakeUpChanceMaximum = actor.wakeUpChanceMaximum
        val wakeUpChanceMinimum = actor.wakeUpChanceMinimum
        val wakeUpRequirement = 0
        val wakeUpScore = (wakeUpChanceMinimum..wakeUpChanceMaximum).random()
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "actor.wakeUpChanceMaximum=$wakeUpChanceMaximum " +
                    "actor.wakeUpChanceMinimum=$wakeUpChanceMinimum " +
                    "actor.wakeUpRequirement=$wakeUpRequirement " +
                    "actor.wakeUpScore=$wakeUpScore"
        )
        val turnsSleep = if (wakeUpScore != wakeUpRequirement) (actor.turnsSleep + 1) else 0
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "turnsSleep=$turnsSleep"
        )
        actor.turnsSleep = turnsSleep
        if (!actor.statusSleep) {
            actor.trail.add(
                Trail(
                    "$actor WOKE UP"
                )
            )
        }
    }

    private fun checkActorStopSpell(actor: Actor) {
        if (!actor.statusStopSpell) {
            return
        }
        actor.turnsStopSpell += 1
        if (!actor.statusStopSpell) {
            actor.trail.add(
                Trail(
                    "$actor SPELLS are no longer BLOCKED"
                )
            )
        }
    }

    private fun clearActors() {
        val removeAllValue = this.actors.removeAll { actor ->
            val isAlive = actor.isAlive
            logger.debug(
                "$this: " +
                        "actor.allegiance=${actor.allegiance} " +
                        "actor.hitPoints=${actor.hitPoints} " +
                        "actor.id=${actor.id} " +
                        "actor.isAlive=$isAlive"
            )
            !isAlive
        }

        logger.debug(
            "$this: " +
                    "actors.removeAll=$removeAllValue " +
                    "actors.size=${actors.size}"
        )
    }

    override fun run() {

        this.turns += 1

        println(
            "$this: " +
                    "actors.size=${actors.size} " +
                    "isActive=${this.isActive} " +
                    "turns=${this.turns}"
        )

        this.clearActors()

        val allegiances = mutableSetOf<Int>()

        this.actors.forEachIndexed { index, actor ->
            allegiances.add(actor.allegiance)
            checkActor(actor, index)
        }

        this.isActive = allegiances.size > 1

        logger.debug(
            "$this: " +
                    "allegiances.size=${allegiances.size} " +
                    "isActive=${this.isActive}"
        )
    }
}
