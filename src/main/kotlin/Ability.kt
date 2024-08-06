package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.UUID


open class Ability<A, B : Receiver>(
    private val invokable: Invokable<A, B>,
) : Identifier where A : HitPointer, A : Invoker, A : SleepAccumulator {

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override val uuid: UUID = UUID.randomUUID()

    private fun check(invoker: A, receiver: B): Boolean {
        logger.debug(
            "invoker.uuid={} receiver.uuid={} uuid={}", invoker.uuid, receiver.uuid, uuid
        )
        return checkInvoker(invoker)
    }

    protected open fun checkInvoker(invoker: A): Boolean {
        return checkInvokerHitPoints(invoker) && checkInvokerStatusSleep(invoker)
    }

    private fun checkInvokerHitPoints(invoker: A): Boolean {
        val hasHitPoints = invoker.hasHitPoints
        logger.debug(
            "invoker.hasHitPoints={} invoker.hitPoints={} invoker.uuid={} uuid={}",
            hasHitPoints,
            invoker.hitPoints,
            invoker.uuid,
            uuid
        )
        return hasHitPoints
    }

    private fun checkInvokerStatusSleep(invoker: A): Boolean {
        val statusSleep = invoker.statusSleep
        logger.debug(
            "invoker.statusSleep={} invoker.uuid={} uuid={}", statusSleep, invoker.uuid, uuid
        )
        return !statusSleep
    }

    fun use(invoker: A, receiver: B): Boolean {
        logger.info(
            "invoker.uuid={} receiver.uuid={} uuid={}", invoker.uuid, receiver.uuid, uuid
        )
        val checkResult = check(invoker, receiver) && invokable.invoke(invoker, receiver)
        logger.info(
            "checkResult={} invoker.uuid={} receiver.uuid={} uuid={}", checkResult, invoker.uuid, receiver.uuid, uuid
        )
        return checkResult
    }
}