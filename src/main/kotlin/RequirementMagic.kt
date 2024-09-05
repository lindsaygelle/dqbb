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
        if (!checkRequirement(invoker, receiver)) {
            return false
        }
        return applyEffect(invoker, receiver)
    }

    protected abstract fun applyEffect(invoker: A, receiver: B): Boolean

    private fun checkRequirement(invoker: A, receiver: B): Boolean {
        logger.trace(
            "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return getRequirement(invoker) > getResistance(receiver)
    }

    protected abstract fun getRequirement(invoker: A): Int

    protected abstract fun getResistance(receiver: B): Int
}