package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class BattleSystem(
    private val actors: MutableList<Actor>
) {
    private var turns: Int = 0

    fun run() {
        while (true) {

            actors.removeAll { actor ->
                val isAlive = actor.isAlive
                logger.debug(
                    "$this: " +
                            "actor.allegiance=${actor.allegiance} " +
                            "actor.id=$actor " +
                            "actor.isAlive=$isAlive"
                )
                !isAlive
            }

            val allegiancesRemaining = actors.map { it.allegiance }.distinct().size

            logger.debug(
                "$this: " +
                        "actors.size=${actors.size} " +
                        "allegiancesRemaining=$allegiancesRemaining " +
                        "turns=$turns"
            )

            if (allegiancesRemaining < 2) {
                break
            }

            actors.sortedByDescending { actor ->
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
                            "actor.strength=${actor.strength} " +
                            "actor.strengthMaximum=${actor.strengthMaximum} " +
                            "actor.turnsSleep=${actor.turnsSleep} " +
                            "actor.turnsSleepMaximum=${actor.turnsSleepMaximum} " +
                            "actor.turnsStopSpell=${actor.turnsStopSpell} " +
                            "actor.turnsStopSpellMaximum=${actor.turnsStopSpellMaximum} " +
                            "index=$index"
                )

                val actorTurnResult = actor.takeTurn(actors)

                logger.debug(
                    "$this: " +
                            "actor.id=$actor " +
                            "actor.takeTurn=$actorTurnResult"
                )
            }

            this.turns += 1
        }
        logger.debug(
            "$this: " +
                    "Battle ended after $turns turns"
        )
        logger.debug(
            "$this: " +
                    "Winning team: ${actors.firstOrNull()?.allegiance ?: "None"}"
        )
    }
}
