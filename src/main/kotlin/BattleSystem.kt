package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class BattleSystem(
    actors: MutableSet<Actor>
) : Runnable {

    val actors: MutableList<Actor> = actors.toMutableList()
    val actorsDefeated: MutableList<Actor> = mutableListOf()

    var isActive: Boolean = true

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    var turns: Int = 0

    override fun run() {
        val removeAllValue = this.actors.removeAll { actor ->
            val isAlive = actor.isAlive
            logger.debug(
                "$this: " +
                        "actor.allegiance=${actor.allegiance} " +
                        "actor.hitPoints=${actor.hitPoints} " +
                        "actor.id=$actor " +
                        "actor.isAlive=$isAlive"
            )
            if (!isAlive) {
                this.actorsDefeated.add(actor)
            }
            !isAlive
        }

        logger.debug(
            "$this: " +
                    "actors.removeAll=$removeAllValue"
        )

        val allegiances = this.actors.map {
            it.allegiance
        }.distinct()

        logger.debug(
            "$this: " +
                    "actors.size=${this.actors.size} " +
                    "allegiances.size=${allegiances.size} " +
                    "turns=${this.turns}"
        )

        this.isActive = allegiances.size > 1

        this.actors.sortedByDescending { actor ->
            actor.agility
        }.forEachIndexed { index, actor ->

            logger.debug(
                "$this: " +
                        "actor.actionPoints=${actor.actionPoints} " +
                        "actor.actionPointsMaximum=${actor.actionPointsMaximum} " +
                        "actor.agility=${actor.agility} " +
                        "actor.allegiance=${actor.allegiance} " +
                        "actor.damageResistanceMaximum=${actor.damageResistanceMaximum} " +
                        "actor.hitPoints=${actor.hitPoints} " +
                        "actor.hitPointsMaximum=${actor.hitPointsMaximum} " +
                        "actor.id=$actor " +
                        "actor.magicPoints=${actor.magicPoints} " +
                        "actor.magicPointsMaximum=${actor.magicPointsMaximum} " +
                        "actor.statusResistanceMaximum=${actor.statusResistanceMaximum} " +
                        "actor.statusSleep=${actor.statusSleep} " +
                        "actor.statusStopSpell=${actor.statusStopSpell} " +
                        "actor.strength=${actor.strength} " +
                        "actor.strengthMaximum=${actor.strengthMaximum} " +
                        "actor.turnsSleep=${actor.turnsSleep} " +
                        "actor.turnsSleepMaximum=${actor.turnsSleepMaximum} " +
                        "actor.turnsStopSpell=${actor.turnsStopSpell} " +
                        "actor.turnsStopSpellMaximum=${actor.turnsStopSpellMaximum} " +
                        "actors.size=${actors.size} " +
                        "index=$index"
            )

            if (actor.isAlive) {

                val actorTurnResult = actor.takeTurn(actors)

                logger.debug(
                    "$this: " +
                            "actor.id=$actor " +
                            "actor.takeTurn=$actorTurnResult"
                )

                if (actor.statusSleep) {

                    val wakeUpMaximum = actor.wakeUpMaximum
                    val wakeUpMinimum = actor.wakeUpMinimum
                    val wakeUpValue = (wakeUpMinimum..wakeUpMaximum).random()
                    val wakeUpRequirement = 0
                    logger.debug(
                        "$this: " +
                                "actor.id=$actor " +
                                "actor.wakeUpMaximum=$wakeUpMaximum " +
                                "actor.wakeUpMinimum=$wakeUpMinimum " +
                                "actor.wakeUpRequirement=$wakeUpRequirement " +
                                "actor.wakeUpValue=$wakeUpValue"
                    )
                    if (wakeUpRequirement == wakeUpValue) {
                        actor.turnsSleep = 0
                    } else {
                        actor.turnsSleep += 1
                    }
                }

                if (actor.statusStopSpell) {
                    actor.turnsStopSpell
                }
            }
        }

        this.turns += 1
    }
}
