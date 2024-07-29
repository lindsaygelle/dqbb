package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class State(
    private val match: MatchType,
    qualifiers: List<Qualify>,
) {

    val actors: MutableSet<Actor> = mutableSetOf()

    // protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private val qualifiers: List<Qualify> = qualifiers.sortedByDescending { it.priority.ordinal }

    fun check(actor: Actor, otherActors: List<Actor>): Boolean {
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "match=$match " +
                    "otherActors.size=${otherActors.size} " +
                    "qualifiers.size=${qualifiers.size}"
        )
        val matchValue = when (this.match) {
            MatchType.ALL -> matchAll(actor, otherActors)
            MatchType.ANY -> matchAny(actor, otherActors)
        }
        logger.debug(
            "$this: " +
                    "matchValue=$matchValue"
        )
        return matchValue
    }

    private fun matchAll(actor: Actor, otherActors: List<Actor>): Boolean {
        this.qualifiers.forEachIndexed { index, qualify ->
            val actors = performMatch(actor, index, otherActors, qualify)
            if (actors.isEmpty()) {
                return false
            }
            this.actors.addAll(actors)
        }
        return true
    }

    private fun matchAny(actor: Actor, otherActors: List<Actor>): Boolean {
        this.qualifiers.forEachIndexed { index, qualify ->
            val actors = performMatch(actor, index, otherActors, qualify)
            if (actors.isNotEmpty()) {
                this.actors.addAll(actors)
                return true
            }
        }
        return false
    }

    private fun performMatch(actor: Actor, index: Int, otherActors: List<Actor>, qualify: Qualify): Set<Actor> {
        logger.debug(
            "$this: " +
                    "actor.id=$actor" +
                    "index=$index " +
                    "otherActors.size=${otherActors.size} " +
                    "qualify.id=$qualify"
        )
        val actors = qualify.qualify(actor, otherActors)
        logger.debug(
            "$this: " +
                    "actors.size=${actors.size}"
        )
        return actors
    }
}
