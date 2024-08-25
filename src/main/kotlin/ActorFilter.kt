package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ActorFilter(
    actorCriteria: Collection<ActorCriterion>,
    private val selectionType: SelectionType,
) : Identifier {
    private val actorCriteria: List<ActorCriterion> = actorCriteria.sortedByDescending { actorCriterion ->
        actorCriterion.priorityType.ordinal
    }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private fun checkActor(actor: Actor, otherActor: Actor, index: Int): Boolean {
        return selectActor(actor, otherActor, index) && checkActorCriteria(actor, index)
    }

    private fun checkActorCriteria(actor: Actor, index: Int): Boolean {
        logger.debug(
            "actor.id={} id={} index={}", actor.id, id, index
        )
        return actorCriteria.withIndex().all { (index, actorCriterion) ->
            checkActorCriterion(actor, actorCriterion, index)
        }
    }

    private fun checkActorCriterion(actor: Actor, actorCriterion: ActorCriterion, index: Int): Boolean {
        logger.debug(
            "actor.id={} actorCriterion.id={} id={} index={}", actor.id, actorCriterion.id, id, index
        )
        return actorCriterion.check(actor)
    }

    fun filter(actor: Actor, actors: Collection<Actor>): Set<Actor> {
        logger.info(
            "actor.id={} actors.size={} id={}", actor.id, actors.size, id
        )
        val filteredActors = mutableSetOf<Actor>()
        actors.forEachIndexed { index, otherActor ->
            if (checkActor(actor, otherActor, index)) {
                filteredActors.add(otherActor)
            }
        }
        logger.info(
            "actor.id={} filteredActors.size={} id={}", actor.id, filteredActors.size, id,
        )
        return filteredActors
    }

    private fun selectActor(actor: Actor, otherActor: Actor, index: Int): Boolean {
        logger.debug(
            "actor.allegiance={} actor.id={} id={} index={} otherActor.allegiance={} otherActor.id={} selectionType={}",
            actor.allegiance,
            actor.id,
            id,
            index,
            otherActor.allegiance,
            otherActor.id,
            selectionType,
        )
        return when (selectionType) {
            SelectionType.ANY -> true
            SelectionType.ALLY -> actor.allegiance == otherActor.allegiance
            SelectionType.ENEMY -> actor.allegiance != otherActor.allegiance
            SelectionType.SELF -> actor == otherActor
        }
    }

    override fun toString(): String {
        return "actorCriteria.size=${actorCriteria.size} id=$id selectionType=$selectionType simpleName=$simpleName"
    }
}