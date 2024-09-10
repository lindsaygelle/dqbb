package dqbb

abstract class RequirementMagic<A : MagicInvoker, B : Receiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    magicCost = magicCost,
) {
    final override fun apply(invoker: A, receiver: B): Boolean {
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkRequirement(invoker, receiver) && applyEffect(invoker, receiver)
    }

    protected abstract fun applyEffect(invoker: A, receiver: B): Boolean

    private fun checkRequirement(invoker: A, receiver: B): Boolean {
        val checkValue: Boolean = getInvokerRequirement(invoker) > getReceiverResistance(receiver)
        logger.info(
            "checkValue={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkValue
    }

    protected abstract fun getInvokerRequirement(invoker: A): Int

    protected abstract fun getReceiverResistance(receiver: B): Int
}