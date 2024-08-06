package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.UUID

class ActorFilter(
    actorCriteria: Collection<ActorCriterion>,
    private val selectionType: SelectionType,
) : Identifier {
    private val actorCriteria: Collection<ActorCriterion> =
        actorCriteria.sortedByDescending { actorCriterion -> actorCriterion.priorityType.ordinal }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override val uuid: UUID = UUID.randomUUID()

    private fun checkActor(actor: Actor, index: Int): Boolean {
        logger.debug(
            "actor.uuid={} index={} uuid={}",
            actor.uuid,
            index,
            uuid,
        )
        return actorCriteria.withIndex().all { (index, actorCriterion) ->
            checkActorCriterion(actor, actorCriterion, index)
        }
    }

    private fun checkActorCriterion(actor: Actor, actorCriterion: ActorCriterion, index: Int): Boolean {
        logger.debug(
            "actor.uuid={} actorCriterion.priorityType={} actorCriterion.uuid={} index={} uuid={}",
            actor.uuid,
            actorCriterion.priorityType,
            actorCriterion.uuid,
            index,
            uuid,
        )
        return actorCriterion.check(actor)
    }

    private fun selectActor(actor: Actor, index: Int, otherActor: Actor): Boolean {
        val checkResult = when (selectionType) {
            SelectionType.ALLY -> actor.allegiance == otherActor.allegiance
            SelectionType.ANY -> true
            SelectionType.ENEMY -> actor.allegiance != otherActor.allegiance
            SelectionType.SELF -> actor == otherActor
        }
        logger.info(
            "actor.allegiance={} actor.uuid={} checkResult={} index={} otherActor.allegiance={} otherActor.uuid={} selectionType={} uuid={}",
            actor.allegiance,
            actor.uuid,
            checkResult,
            index,
            otherActor.allegiance,
            otherActor.uuid,
            selectionType,
            uuid
        )
        return checkResult
    }

    fun filter(actor: Actor, actors: Collection<Actor>): Collection<Actor> {
        logger.info(
            "actor.uuid={} actors.size={} selectionType={} uuid={}",
            actor.uuid,
            actors.size,
            selectionType,
            uuid,
        )
        val filteredActors = actors.filterIndexed { index, otherActor ->
            (selectActor(actor, index, otherActor) && checkActor(otherActor, index))
        }
        logger.info(
            "actor.uuid={} filteredActors.size={} uuid={}", actor.uuid, filteredActors.size, uuid
        )
        return filteredActors
    }
}