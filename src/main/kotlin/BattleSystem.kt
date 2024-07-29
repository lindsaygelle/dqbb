package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class BattleSystem(
    actors: MutableSet<Actor>
) : Runnable {

    val actors: MutableList<Actor> = actors.toMutableList()

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private var turns: Int = 0

    override fun run() {
        while (true) {
            val removeAllValue = this.actors.removeAll { actor ->
                val isAlive = actor.isAlive
                logger.debug(
                    "$this: " +
                            "actor.allegiance=${actor.allegiance} " +
                            "actor.hitPoints=${actor.hitPoints} " +
                            "actor.id=$actor " +
                            "actor.isAlive=$isAlive"
                )
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

            if (allegiances.size == 1) {
                break
            }

            this.actors.sortedByDescending { actor ->
                actor.agility
            }.forEachIndexed { index, actor ->

                logger.debug(
                    "$this: " +
                            "actor.actionPoints=${actor.actionPoints} " +
                            "actor.actionPointsMaximum=${actor.actionPointsMaximum} " +
                            "actor.agility=${actor.agility} " +
                            "actor.allegiance=${actor.allegiance} " +
                            "actor.hitPoints=${actor.hitPoints} " +
                            "actor.hitPointsMaximum=${actor.hitPointsMaximum} " +
                            "actor.id=$actor " +
                            "actor.magicPoints=${actor.magicPoints} " +
                            "actor.magicPointsMaximum=${actor.magicPointsMaximum} " +
                            "actor.statusResistance=${actor.statusResistance} " +
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
                }
            }

            this.turns += 1
        }
    }
}
