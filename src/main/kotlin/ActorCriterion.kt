package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.UUID

class ActorCriterion(
    actorChecks: Collection<ActorCheck>,
    private val matchType: MatchType,
    val priorityType: PriorityType,
) : Identifier {
    private val actorChecks: Collection<ActorCheck> = actorChecks.sortedByDescending { actorCheck ->
        actorCheck.priorityType.ordinal
    }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override val uuid: UUID = UUID.randomUUID()

    fun check(actor: Actor): Boolean {
        logger.info(
            "actor.uuid={} actorChecks.size={} matchType={} priorityType={} uuid={}",
            actor.uuid,
            actorChecks.size,
            matchType,
            priorityType,
            uuid
        )
        val checkResult = checkMatchType(actor)
        logger.info("actor.uuid={} checkResult={} uuid={}", actor.uuid, checkResult, uuid)
        return checkResult
    }

    private fun checkActor(actor: Actor, actorCheck: ActorCheck, index: Int): Boolean {
        logger.debug(
            "actor.uuid={} actorCheck.priorityType={} actorCheck.uuid={} index={} uuid={}",
            actor.uuid,
            actorCheck.priorityType,
            actorCheck.uuid,
            index,
            uuid
        )
        return actorCheck.check(actor)
    }

    private fun checkMatchType(actor: Actor): Boolean {
        return when (matchType) {
            MatchType.ALL -> matchTypeAll(actor)
            MatchType.ANY -> matchTypeAny(actor)
        }
    }

    private fun matchTypeAll(actor: Actor): Boolean {
        return actorChecks.withIndex().all { (index, actorCheck) ->
            checkActor(actor, actorCheck, index)
        }
    }

    private fun matchTypeAny(actor: Actor): Boolean {
        return actorChecks.withIndex().any { (index, actorCheck) ->
            checkActor(actor, actorCheck, index)
        }
    }
}