package dqbb

abstract class AbilityMagicRequirement<A : MagicInvoker, B : Receiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    magicCost = magicCost,
) {
    final override fun applyEffect(invoker: A, receiver: B): Reviewable {
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        if (!checkRequirement(invoker, receiver)) {
            return getReviewableRequirementInvalid(invoker, receiver)
        }
        return applyUpdate(invoker, receiver)
    }

    protected abstract fun applyUpdate(invoker: A, receiver: B): Reviewable

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

    protected abstract fun getReviewableRequirementInvalid(invoker: A, receiver: B): Reviewable
}