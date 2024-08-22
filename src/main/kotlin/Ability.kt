package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class Ability<A : Invoker, B : Receiver> : Identifier {
    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    protected abstract fun apply(invoker: A, receiver: B): Boolean

    protected open fun checkInvoker(invoker: A): Boolean {
        return checkInvokerTurnsSleep(invoker)
    }

    private fun checkInvokerTurnsSleep(invoker: A): Boolean {
        logger.debug(
            "id={} invoker.id={} invoker.turnsSleep={}", id, invoker.id, invoker.turnsSleep
        )
        return invoker.turnsSleep == 0
    }

    protected abstract fun checkReceiver(receiver: B): Boolean

    abstract fun use(invoker: A, receiver: B): Boolean

    override fun toString(): String {
        return "id=$id simpleName=$simpleName"
    }
}