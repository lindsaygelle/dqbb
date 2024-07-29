package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class Qualify(
    private val checkers: List<Check>,
    private val match: MatchType,
    val priority: PriorityType = PriorityType.LOWEST,
    private val target: TargetType,
) {

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private fun checkMatch(actor: Actor): Boolean {
        if (checkers.isEmpty()) {
            return true
        }
        return when (match) {
            MatchType.ALL -> matchAll(actor)
            MatchType.ANY -> matchAny(actor)
        }
    }

    private fun checkTarget(actor: Actor, otherActor: Actor): Boolean {
        return when (this.target) {
            TargetType.ALLY -> actor.allegiance == otherActor.allegiance
            TargetType.ANY -> true
            TargetType.ENEMY -> actor.allegiance != otherActor.allegiance
            TargetType.SELF -> actor == otherActor
        }
    }

    private fun matchAll(actor: Actor): Boolean {
        return checkers.all { checker -> checker.check(actor) }
    }

    private fun matchAny(actor: Actor): Boolean {
        return checkers.any { checker -> checker.check(actor) }
    }

    fun qualify(actor: Actor, otherActors: List<Actor>): MutableSet<Actor> {
        logger.debug(
            "$this: " +
                    "actor.allegiance=${actor.allegiance} " +
                    "actor.id=$actor " +
                    "checkers.size=${checkers.size} " +
                    "match=$match " +
                    "priority=$priority " +
                    "target=$target"
        )
        val actors = mutableSetOf<Actor>()
        otherActors.forEachIndexed { index, otherActor ->
            val checkTargetValue = this.checkTarget(actor, otherActor)
            logger.debug(
                "$this: " +
                        "actor.allegiance=${actor.allegiance} " +
                        "actor.id=$actor " +
                        "checkTarget=$checkTargetValue " +
                        "index=$index " +
                        "otherActor.allegiance=${otherActor.allegiance} " +
                        "otherActor.id=$otherActor"
            )
            if (checkTargetValue) {
                val checkMatchValue = this.checkMatch(otherActor)
                logger.debug(
                    "$this: " +
                            "actor.allegiance=${actor.allegiance} " +
                            "actor.id=$actor " +
                            "checkMatch=$checkMatchValue " +
                            "otherActor.allegiance=${otherActor.allegiance} " +
                            "otherActor.id=$otherActor"
                )
                if (checkMatchValue) {
                    actors.add(otherActor)
                }
            }
        }
        logger.debug(
            "$this: " + "actors.size=${actors.size}"
        )
        return actors
    }
}
