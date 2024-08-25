package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ActorCriterion(
    actorChecks: Collection<ActorCheck>,
    private val matchType: MatchType = MatchType.ALL,
    override val priorityType: PriorityType = PriorityType.EQUAL,
) : Identifier,
    Prioritized {
    private val actorChecks: List<ActorCheck> =
        actorChecks.sortedByDescending { actorCheck -> actorCheck.priorityType.ordinal }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun check(actor: Actor): Boolean {
        logger.info(
            "actor.id={} actorChecks.size={} id={} matchType={} priorityType={}",
            actor.id,
            actorChecks.size,
            id,
            matchType,
            priorityType
        )
        return checkMatchType(actor)
    }

    private fun checkActor(actor: Actor, actorCheck: ActorCheck, index: Int): Boolean {
        logger.info(
            "actor.id={} actorCheck.id={} id={} index={} matchType={} priorityType={}",
            actor.id,
            actorCheck.id,
            id,
            index,
            matchType,
            priorityType
        )
        return actorCheck.check(actor)
    }

    private fun checkMatchType(actor: Actor): Boolean {
        return when (matchType) {
            MatchType.ALL -> matchAll(actor)
            MatchType.ANY -> matchAny(actor)
        }
    }

    private fun matchAll(actor: Actor): Boolean {
        return actorChecks.withIndex().all { (index, actorCheck) ->
            checkActor(actor, actorCheck, index)
        }
    }

    private fun matchAny(actor: Actor): Boolean {
        return actorChecks.withIndex().any { (index, actorCheck) ->
            checkActor(actor, actorCheck, index)
        }
    }

    override fun toString(): String {
        return "actorChecks.size=${actorChecks.size} id=$id matchType=$matchType priorityType=$priorityType simpleName=$simpleName"
    }
}