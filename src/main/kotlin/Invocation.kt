package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.UUID

abstract class Invocation<A : Invoker, B : Receiver> : Invokable<A, B> {
    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    final override val uuid: UUID = UUID.randomUUID()

    protected abstract fun apply(invoker: A, receiver: B): Boolean

    protected abstract fun check(invoker: A, receiver: B): Boolean

    final override fun invoke(invoker: A, receiver: B): Boolean {
        logger.info(
            "invoker.uuid={} receiver.uuid={} uuid={}", invoker.uuid, receiver.uuid, uuid
        )
        val checkResult = check(invoker, receiver) && apply(invoker, receiver)
        logger.info(
            "checkResult={} invoker.uuid={} receiver.uuid={} uuid={}", checkResult, invoker.uuid, receiver.uuid, uuid
        )
        return checkResult
    }
}