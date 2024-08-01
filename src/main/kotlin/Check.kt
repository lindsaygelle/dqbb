package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


abstract class Check(
    private val expressionType: ExpressionType,
    private val operatorType: OperatorType,
    override val priorityType: PriorityType,
    private val value: Int,
) : Identifier, Prioritized {

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun check(actor: Actor): Boolean {
        val valueOther = when (this.expressionType) {
            ExpressionType.EXACT -> getExactValue(actor)
            ExpressionType.PERCENTAGE -> getPercentageValue(actor)
        }
        val checkValueResult = checkValue(valueOther)
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "checkValue=$checkValueResult " +
                    "expressionType=${this.expressionType} " +
                    "operatorType=${this.operatorType} " +
                    "priorityType=${this.priorityType} " +
                    "vale=${this.value} " +
                    "valueOther=$valueOther"
        )
        return checkValueResult
    }

    private fun checkValue(valueOther: Int): Boolean {
        return when (this.operatorType) {
            OperatorType.EQUAL -> valueOther == this.value
            OperatorType.GREATER_THAN -> valueOther > this.value
            OperatorType.LESS_THAN -> valueOther < this.value
            OperatorType.NOT -> valueOther != this.value
        }
    }

    protected abstract fun getExactValue(actor: Actor): Int

    protected abstract fun getPercentageValue(actor: Actor): Int
}
