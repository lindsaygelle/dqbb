package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.UUID

class ActorCheck(
    private val attributeName: AttributeName,
    private val operatorType: OperatorType,
    val priorityType: PriorityType,
    value: Int,
) : Identifier {
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private val value: Int = maxOf(0, value)

    override val uuid: UUID = UUID.randomUUID()

    fun check(actor: Actor): Boolean {
        logger.info(
            "actor.uuid={} attributeName={} operatorType={} priorityType={} uuid={} value={}",
            actor.uuid,
            attributeName,
            operatorType,
            priorityType,
            uuid,
            value
        )
        val checkResult = checkValue(actor.provideAttribute(attributeName))
        logger.info("actor.uuid={} checkResult={} uuid={}", actor.uuid, checkResult, uuid)
        return checkResult
    }

    private fun checkValue(attributeValue: Int): Boolean {
        logger.debug(
            "attributeValue={} operatorType={} uuid={} value={}", attributeValue, operatorType, uuid, value
        )
        return when (operatorType) {
            OperatorType.EQUAL -> attributeValue == value
            OperatorType.GREATER_THAN -> attributeValue > value
            OperatorType.GREATER_THAN_EQUAL -> attributeValue >= value
            OperatorType.LESS_THAN -> attributeValue < value
            OperatorType.LESS_THAN_EQUAL -> attributeValue <= value
            OperatorType.NOT -> attributeValue != value
        }
    }
}