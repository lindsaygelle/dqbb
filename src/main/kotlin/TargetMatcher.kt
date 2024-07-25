package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object TargetMatcher {

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun match(value: String): Target? {
        val target =  when (value.uppercase()) {
            Target.ALLY.toString() -> Target.ALLY
            Target.ANY.toString() -> Target.ANY
            Target.ENEMY.toString() -> Target.ENEMY
            Target.SELF.toString() -> Target.SELF
            else -> null
        }
        logger.debug("target=$target")
        return target
    }
}