package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ActorAggregate(
    private val actorFilters: Collection<ActorFilter>,
) : Identifier {
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun aggregate(actor: Actor, actors: Collection<Actor>): Collection<Actor> {
        logger.info(
            "actor.id={} actorFilters.size={} actors.size={} id={}", actor.id, actorFilters.size, actors.size, id
        )
        val aggregatedActors = mutableSetOf<Actor>()
        actorFilters.forEachIndexed { index, actorFilter ->
            val filteredActors = filterActors(actor, actorFilter, actors, index)
            aggregatedActors.addAll(filteredActors)
        }
        logger.info(
            "actor.id={} aggregatedActors.size={} id={}", actor.id, aggregatedActors.size, id
        )
        return aggregatedActors
    }

    private fun filterActors(
        actor: Actor, actorFilter: ActorFilter, actors: Collection<Actor>, index: Int,
    ): Set<Actor> {
        logger.info(
            "actor.id={} actorFilter.id={} actors.size={} id={} index={}",
            actor.id,
            actorFilter.id,
            actors.size,
            id,
            index
        )
        return actorFilter.filter(actor, actors)
    }

    override fun toString(): String {
        return "actorFilters.size=${actorFilters.size} id=$id simpleName=$simpleName"
    }
}