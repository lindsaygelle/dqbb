package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Weapon : AttackPointer,
    Identifier,
    Nameable {
    override var attack: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} attack={}", id, field
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
        return "attack=$attack id=$id name=$name simpleName=$simpleName"
    }
}