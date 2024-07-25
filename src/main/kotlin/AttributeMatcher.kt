package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object AttributeMatcher {

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun match(value: String): Attribute? {
        val attribute = when (value.uppercase()) {
            Attribute.ACTION_POINTS.toString() -> Attribute.ACTION_POINTS
            Attribute.HIT_POINTS.toString() -> Attribute.HIT_POINTS
            Attribute.MAGIC_POINTS.toString() -> Attribute.MAGIC_POINTS
            Attribute.TURNS_SLEEP.toString() -> Attribute.TURNS_SLEEP
            Attribute.TURNS_STOP_SPELL.toString() -> Attribute.TURNS_STOP_SPELL
            else -> null
        }
        logger.info("attribute=$attribute")
        return attribute
    }
}
