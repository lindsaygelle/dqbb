package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

open class Result : Identifier {
    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)
    var result: Boolean = false
        set(value) {
            field = value
            logger.debug(
                "id={} result={} simpleName={}", id, field, simpleName
            )
        }
}