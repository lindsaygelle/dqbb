package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class Ability<A : Invoker, B : Receiver> : Identifier {
    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    protected abstract fun apply(invoker: A, receiver: B): Boolean

    protected open fun checkInvoker(invoker: A): Boolean {
        val checkValue: Boolean = checkInvokerTurnsSleep(invoker)
        logger.info(
            "checkValue={} id={} invoker.id={} invoker.simpleName={} simpleName={}",
            checkValue,
            id,
            invoker.id,
            invoker.simpleName,
            simpleName,
        )
        return checkValue
    }

    private fun checkInvokerTurnsSleep(invoker: A): Boolean {
        val checkValue: Boolean = invoker.turnsSleep == 0
        logger.info(
            "checkValue={} id={} invoker.id={} invoker.simpleName={} invoker.turnsSleep={} simpleName={}",
            checkValue,
            id,
            invoker.id,
            invoker.simpleName,
            invoker.turnsSleep,
            simpleName
        )
        return checkValue
    }

    protected abstract fun checkReceiver(receiver: B): Boolean

    abstract fun use(invoker: A, receiver: B): Boolean

    override fun toString(): String {
        return "id=$id simpleName=$simpleName"
    }
}