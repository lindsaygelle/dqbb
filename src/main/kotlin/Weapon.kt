package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Weapon() : AttackPointer,
    Identifier,
    Nameable {
    override var attack: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "attack={} id={} simpleName={}", field, id, simpleName
            )
        }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override var name: String? = null
        set(value) {
            field = value
            logger.debug(
                "id={} name={} simpleName={}", id, field, simpleName
            )
        }

    constructor(
        attack: Int,
        name: String? = null
    ) : this() {
        this.attack = attack
        this.name = name
    }

    override fun toString(): String {
        return "attack=$attack id=$id name=$name simpleName=$simpleName"
    }
}