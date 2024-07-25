package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object OperatorMatcher {

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun match(value: String): Operator? {
        val operator = when (value.uppercase()) {
            Operator.EQUAL.toString() -> Operator.EQUAL
            Operator.GREATER_THAN.toString() -> Operator.GREATER_THAN
            Operator.LESS_THAN.toString() -> Operator.LESS_THAN
            else -> null
        }
        logger.debug("operator=$operator")
        return operator
    }
}