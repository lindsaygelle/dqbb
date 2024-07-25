package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object ExpressionMatcher {

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun match(value: String): Expression? {
        val expression = when (value.uppercase()) {
            Expression.EXACT.toString() -> Expression.EXACT
            Expression.PERCENTAGE.toString() -> Expression.PERCENTAGE
            else -> null
        }
        logger.debug("expression=$expression")
        return expression
    }
}