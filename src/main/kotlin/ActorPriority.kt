package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ActorPriority(
    private val attributeName: AttributeName,
    private val sortType: SortType,
) : Identifier {
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private fun checkSortType(actors: Collection<Actor>): List<Actor> {
        return when (sortType) {
            SortType.ASCENDING -> sortAscending(actors)
            SortType.DESCENDING -> sortDescending(actors)
        }
    }

    fun prioritize(actors: Collection<Actor>): List<Actor> {
        logger.info(
            "actors.size={} id={} sortType={}", actors.size, id, sortType
        )
        return checkSortType(actors)
    }

    private fun sortActor(actor: Actor): Int {
        val attributeValue = actor.getAttribute(attributeName)
        logger.debug(
            "actor.id={} attributeName={} attributeValue={} id={} sortType={}",
            actor.id,
            attributeName,
            attributeValue,
            id,
            sortType
        )
        return attributeValue
    }

    private fun sortAscending(actors: Collection<Actor>): List<Actor> {
        return actors.sortedBy { actor -> sortActor(actor) }
    }

    private fun sortDescending(actors: Collection<Actor>): List<Actor> {
        return actors.sortedByDescending { actor -> sortActor(actor) }
    }
}