package dqbb

abstract class Magic<A : MagicInvoker, B : Receiver>(
    magicCost: Int,
) : Invocation<A, B>() {
    private val magicCost: Int = maxOf(0, magicCost)

    override fun check(invoker: A, receiver: B): Boolean {
        if (!checkInvoker(invoker) || !checkReceiver(receiver)) {
            return false
        }
        invoker.magicPoints -= magicCost
        logger.debug(
            "invoker.magicPoints={} invoker.uuid={} uuid={}", invoker.magicPoints, invoker.uuid, uuid
        )
        return apply(invoker, receiver)
    }

    private fun checkInvoker(invoker: A): Boolean {
        return checkInvokerMagicCost(invoker)
    }

    private fun checkInvokerMagicCost(invoker: A): Boolean {
        val checkResult = (invoker.magicPoints - magicCost) >= 0
        logger.debug(
            "checkResult={} invoker.magicPoints={} invoker.uuid={} magicCost={} uuid={}",
            checkResult,
            invoker.magicPoints,
            invoker.uuid,
            magicCost,
            uuid
        )
        return checkResult
    }

    protected abstract fun checkReceiver(receiver: B): Boolean
}