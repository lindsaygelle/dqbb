package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class AttributeComparison<T : AttributeProvider>() : Identifier,
    Nameable,
    Prioritized {
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

    var operatorType: OperatorType? = null
        set(value) {
            field = value
            logger.debug(
                "id={} operatorType={} simpleName={}", id, field, simpleName
            )
        }

    override var priorityType: PriorityType = PriorityType.EQUAL
        set(value) {
            field = value
            logger.debug(
                "id={} priorityType={} simpleName={}", id, field, simpleName
            )
        }

    var value: Int? = null
        set(value) {
            field = maxOf(0, (value ?: 0))
            logger.debug(
                "id={} simpleName={} value={}", id, simpleName, value
            )
        }

    constructor(
        attributeName: AttributeName,
        name: String? = null,
        operatorType: OperatorType,
        priorityType: PriorityType = PriorityType.EQUAL,
        value: Int,
    ) : this() {
        this.attributeName = attributeName
        this.name = name
        this.operatorType = operatorType
        this.priorityType = priorityType
        this.value = value
    }

    fun check(attributeProvider: T): Boolean {
        val attributeValue = attributeName?.let {
            attributeProvider.getAttribute(it)
        }
        logger.info(
            "attributeName={} attributeProvider.id={} attributeProvider.simpleName={} attributeValue={} id={} name={} operatorType={} priorityType={} simpleName={} value={}",
            attributeName,
            attributeProvider.id,
            attributeProvider.simpleName,
            attributeValue,
            id,
            name,
            operatorType,
            priorityType,
            simpleName,
            value,
        )
        val checkValue = attributeValue?.let {
            checkValue(it)
        }
        logger.info(
            "checkValue={} id={} simpleName={}", checkValue, id, simpleName
        )
        return checkValue ?: false
    }

    private fun checkValue(attributeValue: Int): Boolean {
        logger.info(
            "attributeValue={} id={} simpleName={} value={}", attributeValue, id, simpleName, value
        )
        return when (operatorType) {
            OperatorType.EQUAL -> attributeValue == (value ?: return false)
            OperatorType.GREATER_THAN -> attributeValue > (value ?: return false)
            OperatorType.GREATER_THAN_EQUAL -> attributeValue >= (value ?: return false)
            OperatorType.LESS_THAN -> attributeValue < (value ?: return false)
            OperatorType.LESS_THAN_EQUAL -> attributeValue <= (value ?: return false)
            OperatorType.NOT -> attributeValue != (value ?: return false)
            else -> false
        }
    }

    override fun toString(): String {
        return "attributeName=$attributeName id=$id name=$name operatorType=$operatorType priorityType=$priorityType simpleName=$simpleName value=$value"
    }
}