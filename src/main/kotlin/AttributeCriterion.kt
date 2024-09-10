package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class AttributeCriterion<T : AttributeProvider>() : Identifier,
    Nameable,
    Prioritized {
    var attributeComparisons: Collection<AttributeComparison<T>> = emptyList()
        set(value) {
            field = value.filter {
                (it.attributeName != null) && (it.operatorType != null)
            }.sortedByDescending {
                it.priorityType.ordinal
            }
            logger.debug(
                "attributeComparisons.size={} id={} simpleName={}", field.size, id, simpleName
            )
        }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    var matchType: MatchType = MatchType.ALL
        set(value) {
            field = value
            logger.debug(
                "id={} matchType={} simpleName={}", id, field, simpleName
            )
        }

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
        attributeComparisons: Collection<AttributeComparison<T>>,
        matchType: MatchType = MatchType.ALL,
        name: String? = null,
        priorityType: PriorityType = PriorityType.EQUAL,
    ) : this() {
        this.attributeComparisons = attributeComparisons
        this.matchType = matchType
        this.name = name
        this.priorityType = priorityType
    }

    fun check(attributeProvider: T): Boolean {
        logger.info(
            "attributeComparisons.size={} attributeProvider.id={} attributeProvider.simpleName={} id={} matchType={} priorityType={} simpleName={}",
            attributeComparisons.size,
            attributeProvider.id,
            attributeProvider.simpleName,
            id,
            matchType,
            priorityType,
            simpleName
        )
        val checkValue: Boolean = checkMatchType(attributeProvider)
        logger.info(
            "checkValue={} id={} simpleName={}", checkValue, id, simpleName
        )
        return checkValue
    }

    private fun checkAttributeProvider(
        attributeComparison: AttributeComparison<T>,
        attributeProvider: T,
        index: Int,
    ): Boolean {
        logger.info(
            "attributeComparison.id={} attributeProvider.id={} attributeProvider.simpleName={} id={} index={} matchType={} priorityType={} simpleName={}",
            attributeComparison.id,
            attributeProvider.id,
            attributeProvider.simpleName,
            id,
            index,
            matchType,
            priorityType,
            simpleName
        )
        return attributeComparison.check(attributeProvider)
    }

    private fun checkMatchType(attributeProvider: T): Boolean {
        logger.trace(
            "attributeProvider.id={} attributeProvider.simpleName={} id={} matchType={} simpleName={}",
            attributeProvider.id,
            attributeProvider.simpleName,
            id,
            matchType,
            simpleName
        )
        return when (matchType) {
            MatchType.ALL -> matchAll(
                attributeProvider
            )

            MatchType.ANY -> matchAny(
                attributeProvider
            )
        }
    }

    private fun matchAll(attributeProvider: T): Boolean {
        logger.trace(
            "attributeProvider.id={} attributeProvider.simpleName={} id={} operation=matchAll simpleName={}",
            attributeProvider.id,
            attributeProvider.simpleName,
            id,
            simpleName
        )
        return attributeComparisons.withIndex().all { (index: Int, attributeComparison: AttributeComparison<T>) ->
            checkAttributeProvider(attributeComparison, attributeProvider, index)
        }
    }

    private fun matchAny(attributeProvider: T): Boolean {
        logger.trace(
            "attributeProvider.id={} attributeProvider.simpleName={} id={} operation=matchAny simpleName={}",
            attributeProvider.id,
            attributeProvider.simpleName,
            id,
            simpleName
        )
        return attributeComparisons.withIndex().any { (index: Int, attributeComparison: AttributeComparison<T>) ->
            checkAttributeProvider(attributeComparison, attributeProvider, index)
        }
    }

    override fun toString(): String {
        return "attributeComparisons.size=${attributeComparisons.size} id=$id matchType=$matchType name=$name priorityType=$priorityType simpleName=$simpleName"
    }
}