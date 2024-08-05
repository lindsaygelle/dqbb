package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class Qualify(
    val actorCheckers: List<ActorChecker>,
    private val matchType: MatchType,
    override val priorityType: PriorityType = PriorityType.LOWEST,
    private val targetType: TargetType,
) : Identifier, Prioritized {

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private fun checkActor(actor: Actor, index: Int, otherActor: Actor): Boolean {
        logger.debug(
            "$this: " +
                    "actor.allegiance=${actor.allegiance} " +
                    "actor.id=${actor.id} " +
                    "index=$index " +
                    "otherActor.id=${otherActor.id} " +
                    "otherActor.allegiance=${otherActor.allegiance}"
        )
        if (!this.checkTarget(actor, index, otherActor)) {
            return false
        }
        return this.checkMatch(otherActor, index)
    }

    private fun checkMatch(otherActor: Actor, index: Int): Boolean {
        val value = performMatch(otherActor)
        logger.debug(
            "$this: " +
                    "checkMatch=$value " +
                    "index=$index " +
                    "matchType=${this.matchType} " +
                    "otherActor.allegiance=${otherActor.allegiance} " +
                    "otherActor.id=${otherActor.id}"
        )
        return value
    }

    private fun checkTarget(actor: Actor, index: Int, otherActor: Actor): Boolean {
        val value = when (this.targetType) {
            TargetType.ALLY -> actor.allegiance == otherActor.allegiance
            TargetType.ANY -> true
            TargetType.ENEMY -> actor.allegiance != otherActor.allegiance
            TargetType.SELF -> actor == otherActor
        }
        logger.debug(
            "$this: " +
                    "actor.allegiance=${actor.allegiance} " +
                    "actor.id=${actor.id} " +
                    "checkTarget=$value " +
                    "index=$index " +
                    "otherActor.allegiance=${otherActor.allegiance} " +
                    "otherActor.id=${otherActor.id} " +
                    "targetType=${this.targetType}"
        )
        return value
    }

    private fun match(actorChecker: ActorChecker, index: Int, otherActor: Actor): Boolean {
        val checkValue = actorChecker.check(otherActor)
        logger.debug(
            "$this: " +
                    "actorChecker.check=$checkValue " +
                    "actorChecker.id=${actorChecker.id} " +
                    "checkActor.priorityType=${actorChecker.priorityType} " +
                    "index=$index"
        )
        return checkValue
    }

    private fun matchAll(otherActor: Actor): Boolean {
        return this.actorCheckers.withIndex().all { (index, checkActor) ->
            match(checkActor, index, otherActor)
        }
    }

    private fun matchAny(otherActor: Actor): Boolean {
        return this.actorCheckers.withIndex().any { (index, checkActor) ->
            match(checkActor, index, otherActor)
        }
    }

    private fun performMatch(otherActor: Actor): Boolean {
        if (this.actorCheckers.isEmpty()) {
            return true
        }
        return when (this.matchType) {
            MatchType.ALL -> matchAll(otherActor)
            MatchType.ANY -> matchAny(otherActor)
        }
    }

    fun qualify(actor: Actor, otherActors: Collection<Actor>): MutableSet<Actor> {
        val actors = mutableSetOf<Actor>()
        logger.debug(
            "$this: " +
                    "actor.allegiance=${actor.allegiance} " +
                    "actor.id=${actor.id} " +
                    "actorCheckers.size=${this.actorCheckers.size} " +
                    "matchType=${this.matchType} " +
                    "otherActors.size=${otherActors.size} " +
                    "priorityType=${this.priorityType} " +
                    "targetType=${this.targetType}"
        )
        otherActors.forEachIndexed { index, otherActor ->
            if (this.checkActor(actor, index, otherActor)) {
                actors.add(otherActor)
            }
        }
        logger.debug(
            "$this: " +
                    "actors.size=${actors.size}"
        )
        return actors
    }
}
