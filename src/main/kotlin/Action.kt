package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class Action<A : Invoker, B : Receiver> {
    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)
    protected abstract fun apply(invoker: A, receiver: B): Boolean
    protected abstract fun check(invoker: A, receiver: B): Boolean
    fun use(invoker: A, receiver: B): Boolean {
        val isNotAlive = invoker.isNotAlive
        val invokerHashCode = invoker.hashCode()
        logger.debug("invoker.hashCode=$invokerHashCode invoker.isNotAlive=$isNotAlive")
        if (isNotAlive) {
            return false
        }
        val statusSleep = invoker.statusSleep
        logger.debug("invoker.hashCode=$invokerHashCode invoker.statusSleep=$statusSleep")
        if (statusSleep) {
            return false
        }
        val check = check(invoker, receiver)
        logger.info("check=$check")
        if (!check(invoker, receiver)) {
            return false
        }
        val apply = apply(invoker, receiver)
        logger.info("apply=$apply")
        return apply
    }
}
