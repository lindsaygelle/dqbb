package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Shield : DefensePointer,
    Identifier,
    Nameable {
    override var defense: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} defense={}", id, field
            )
        }
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)
    override var name: String? = null
        set(value) {
            field = value
            logger.debug(
                "id={} name={}", id, field
            )
        }

    override fun toString(): String {
        return "defense=$defense id=$id name=$name simpleName=$simpleName"
    }
}