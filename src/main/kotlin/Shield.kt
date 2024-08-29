package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Shield() : DefensePointer,
    Identifier,
    Nameable {
    override var defense: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "defense={} id={} simpleName={}", field, id, simpleName
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
        defense: Int,
        name: String? = null,
    ) : this() {
        this.defense = defense
        this.name = name
    }

    override fun toString(): String {
        return "defense=$defense id=$id name=$name simpleName=$simpleName"
    }
}