package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ActorCheck(
    private val attributeName: AttributeName,
    private val operatorType: OperatorType,
    override val priorityType: PriorityType = PriorityType.EQUAL,
    val value: Int,
) : Identifier,
    Prioritized {
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun check(actor: Actor): Boolean {
        val attributeValue = actor.getAttribute(attributeName)
        logger.info(
            "actor.id={} attributeName={} attributeValue={} id={} operatorType={} priorityType={} value={}",
            actor.id,
            attributeName,
            attributeValue,
            id,
            operatorType,
            priorityType,
            value,
        )
        return checkValue(attributeValue)
    }

    private fun checkValue(attributeValue: Int): Boolean {
        return when (operatorType) {
            OperatorType.EQUAL -> attributeValue == value
            OperatorType.GREATER_THAN -> attributeValue > value
            OperatorType.GREATER_THAN_EQUAL -> attributeValue >= value
            OperatorType.LESS_THAN -> attributeValue < value
            OperatorType.LESS_THAN_EQUAL -> attributeValue <= value
            OperatorType.NOT -> attributeValue != value
        }
    }

    override fun toString(): String {
        return "attributeName=$attributeName id=$id operatorType=$operatorType priorityType=$priorityType simpleName=$simpleName value=$value"
    }
}