package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.UUID

class ActorPriority(
    private val attributeName: AttributeName,
    private val sortedBy: SortType,
) : Identifier {
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override val uuid: UUID = UUID.randomUUID()

    private fun checkActor(actor: Actor): Int {
        val attributeValue = actor.provideAttribute(attributeName)
        logger.debug(
            "actor.uuid={} attributeName={} attributeValue={} sortedBy={} uuid={}",
            actor.uuid,
            attributeName,
            attributeValue,
            sortedBy,
            uuid
        )
        return attributeValue
    }

    fun prioritize(actors: Collection<Actor>): Collection<Actor> {
        logger.info(
            "actors.size={} attributeName={} sortedBy={} uuid={}", actors.size, attributeName, sortedBy, uuid
        )
        return when (sortedBy) {
            SortType.ASCENDING -> sortByAscending(actors)
            SortType.DESCENDING -> sortByDescending(actors)
        }
    }

    private fun sortByAscending(actors: Collection<Actor>): Collection<Actor> {
        return actors.sortedBy { actor ->
            checkActor(actor)
        }
    }

    private fun sortByDescending(actors: Collection<Actor>): Collection<Actor> {
        return actors.sortedByDescending { actor ->
            checkActor(actor)
        }
    }
}