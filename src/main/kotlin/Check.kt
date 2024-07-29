package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


abstract class Check(
    private val expression: ExpressionType,
    private val operator: OperatorType,
    private val value: Int,
) {

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun check(actor: Actor): Boolean {
        val valueOther = when (this.expression) {
            ExpressionType.EXACT -> getExactValue(actor)
            ExpressionType.PERCENTAGE -> getPercentageValue(actor)
        }
        val checkValueResult = checkValue(valueOther)
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "checkValue=$checkValueResult " +
                    "expression=$expression " +
                    "operator=$operator " +
                    "value=$value " +
                    "valueOther=$valueOther"
        )
        return checkValueResult
    }

    private fun checkValue(valueOther: Int): Boolean {
        return when (this.operator) {
            OperatorType.EQUAL -> valueOther == this.value
            OperatorType.GREATER_THAN -> valueOther > this.value
            OperatorType.LESS_THAN -> valueOther < this.value
            OperatorType.NOT -> valueOther != this.value
        }
    }

    protected abstract fun getExactValue(actor: Actor): Int

    protected abstract fun getPercentageValue(actor: Actor): Int
}
