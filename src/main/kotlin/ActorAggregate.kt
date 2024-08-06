package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.*

class ActorAggregate(
    private val actorFilters: Collection<ActorFilter>,
) : Identifier {
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override val uuid: UUID = UUID.randomUUID()

    fun aggregate(actor: Actor, actors: Collection<Actor>): Collection<Actor> {
        logger.info(
            "actor.uuid={} actorFilters.size={} actors.size={} uuid={}",
            actor.uuid,
            actorFilters.size,
            actors.size,
            uuid,
        )
        val aggregateActors = mutableSetOf<Actor>()
        actorFilters.forEachIndexed { index, actorFilter ->
            aggregateActors.addAll(filterActors(actor, actorFilter, actors, index))
        }
        logger.info(
            "aggregateActors.size={} uuid={}", aggregateActors.size, uuid
        )
        return aggregateActors
    }

    private fun filterActors(
        actor: Actor, actorFilter: ActorFilter, actors: Collection<Actor>, index: Int,
    ): Collection<Actor> {
        logger.debug(
            "actor.uuid={} actorFilter.uuid={} index={} uuid={}", actor.uuid, actorFilter.uuid, index, uuid
        )
        return actorFilter.filter(actor, actors)
    }
}