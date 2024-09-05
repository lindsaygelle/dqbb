package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class AttributeFilter<T : AttributeProvider>() : Identifier,
    Nameable,
    Prioritized {
    var attributeCriteria: Collection<AttributeCriterion<T>> = emptyList()
        set(value) {
            field = value.filter { attributeCriterion: AttributeCriterion<T> ->
                attributeCriterion.attributeComparisons.isNotEmpty()
            }.sortedByDescending { attributeCriterion: AttributeCriterion<T> ->
                attributeCriterion.priorityType.ordinal
            }
            logger.debug(
                "attributeCriteria.size={} id={} simpleName={}", field.size, id, simpleName
            )
        }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override var name: String? = null
        set(value) {
            field = value
            logger.debug(
                "id={} name={} simpleName={}", id, field, simpleName
            )
        }

    override var priorityType: PriorityType = PriorityType.EQUAL
        set(value) {
            field = value
            logger.debug(
                "id={} priorityType={} simpleName={}", id, field, simpleName
            )
        }

    constructor(
        attributeCriteria: Collection<AttributeCriterion<T>>,
        name: String? = null,
    ) : this() {
        this.attributeCriteria = attributeCriteria
        this.name = name
    }

    private fun checkAttributeCriterion(
        attributeCriterion: AttributeCriterion<T>,
        attributeProvider: T,
        index: Int,
    ): Boolean {
        logger.info(
            "attributeCriterion.id={} attributeCriterion.priorityType={} attributeProvider.id={} attributeProvider.simpleName={} id={} index={} simpleName={}",
            attributeCriterion.id,
            attributeCriterion.priorityType,
            attributeProvider.id,
            attributeProvider.simpleName,
            id,
            index,
            simpleName
        )
        return attributeCriterion.check(attributeProvider)
    }

    private fun checkAttributeProvider(attributeProvider: T, index: Int): Boolean {
        logger.debug(
            "attributeProvider.id={} attributeProvider.simpleName={} id={} index={}",
            attributeProvider.id,
            attributeProvider.simpleName,
            id,
            index
        )
        return attributeCriteria.withIndex().all { (index: Int, attributeCriterion: AttributeCriterion<T>) ->
            checkAttributeCriterion(attributeCriterion, attributeProvider, index)
        }
    }

    fun filter(attributeProviders: Collection<T>): Collection<T> {
        logger.info(
            "attributeCriteria.size={} attributeProviders.size={} id={} simpleName={}",
            attributeCriteria.size,
            attributeProviders.size,
            id,
            simpleName
        )
        return attributeProviders.filterIndexed { index: Int, attributeProvider: T ->
            checkAttributeProvider(attributeProvider, index)
        }
    }
}