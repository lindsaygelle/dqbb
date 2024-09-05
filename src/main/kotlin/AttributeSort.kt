package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class AttributeSort<T : AttributeProvider>() : Identifier,
    Nameable {
    var attributeName: AttributeName? = null
        set(value) {
            field = value
            logger.debug(
                "attributeName={} id={} simpleName={}", field, id, simpleName
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

    var sortType: SortType = SortType.ASCENDING
        set(value) {
            field = value
            logger.debug(
                "id={}  simpleName={} sortType={}", id, simpleName, sortType
            )
        }

    constructor(
        attributeName: AttributeName,
        name: String? = null,
        sortType: SortType = SortType.ASCENDING,
    ) : this() {
        this.attributeName = attributeName
        this.name = name
        this.sortType = sortType
    }

    private fun checkAttributeProvider(attributeProvider: T): Int? {
        val attributeValue = attributeName?.let {
            attributeProvider.getAttribute(it)
        }
        logger.info(
            "attributeName={} attributeProvider.id={} attributeProvider.simpleName={} attributeValue={} id={} name={} simpleName={} sortType={}",
            attributeName,
            attributeProvider.id,
            attributeProvider.simpleName,
            attributeValue,
            id,
            name,
            simpleName,
            sortType
        )
        return attributeValue
    }

    fun sort(attributeProviders: Collection<T>): Collection<T> {
        logger.info(
            "attributeProviders.size={} attributeName={} id={} simpleName={} sortType={}",
            attributeProviders.size,
            attributeName,
            id,
            simpleName,
            sortType
        )
        return when (sortType) {
            SortType.ASCENDING -> sortAscending(attributeProviders)
            SortType.DESCENDING -> sortDescending(attributeProviders)
        }
    }

    private fun sortAscending(attributeProviders: Collection<T>): Collection<T> {
        logger.trace(
            "attributeProviders.size={} attributeName={} id={} operation=sortAscending simpleName={}",
            attributeProviders.size,
            attributeName,
            id,
            simpleName,
        )
        return attributeProviders.sortedBy { attributeProvider: T ->
            (checkAttributeProvider(attributeProvider) ?: Int.MAX_VALUE)
        }
    }

    private fun sortDescending(attributeProviders: Collection<T>): Collection<T> {
        logger.trace(
            "attributeProviders.size={} attributeName={} id={} operation=sortDescending simpleName={}",
            attributeProviders.size,
            attributeName,
            id,
            simpleName,
        )
        return attributeProviders.sortedByDescending { attributeProvider: T ->
            (checkAttributeProvider(attributeProvider) ?: Int.MIN_VALUE)
        }
    }

    override fun toString(): String {
        return "attributeName=$attributeName id=$id name=$name simpleName=$simpleName sortType$sortType"
    }
}