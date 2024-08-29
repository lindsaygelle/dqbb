package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class Ability<A : Invoker, B : Receiver> : Identifier {
    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    protected abstract fun apply(invoker: A, receiver: B): Boolean

    protected open fun checkInvoker(invoker: A): Boolean {
        logger.trace(
            "id={} invoker.id={} invoker.simpleName={} simpleName={}", id, invoker.id, invoker.simpleName, simpleName,
        )
        return checkInvokerTurnsSleep(invoker)
    }

    private fun checkInvokerTurnsSleep(invoker: A): Boolean {
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} invoker.turnsSleep={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            invoker.turnsSleep,
            simpleName
        )
        return invoker.turnsSleep == 0
    }

    protected abstract fun checkReceiver(receiver: B): Boolean

    abstract fun use(invoker: A, receiver: B): Boolean

    override fun toString(): String {
        return "id=$id simpleName=$simpleName"
    }
}