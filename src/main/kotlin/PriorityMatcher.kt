package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object PriorityMatcher {

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun match(value: String): Priority? {
        val priority = when (value.uppercase()) {
            Priority.LOWEST.toString() -> Priority.LOWEST
            Priority.LOW.toString() -> Priority.LOW
            Priority.EQUAL.toString() -> Priority.EQUAL
            Priority.HIGH.toString() -> Priority.HIGH
            Priority.HIGHEST.toString() -> Priority.HIGHEST
            else -> null
        }
        logger.debug("priority=$priority")
        return priority
    }
}