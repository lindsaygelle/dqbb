package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object MatchMatcher {

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun match(value: String): Match? {
        val match = when (value.uppercase()) {
            Match.ALL.toString() -> Match.ALL
            Match.OR.toString() -> Match.OR
            else -> null
        }
        logger.debug("match=$match")
        return match
    }
}