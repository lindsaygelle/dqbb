package dqbb

abstract class RequirementMagic<A : MagicInvoker, B : Receiver>(magicCost: Int) : Magic<A, B>(magicCost) {
    override fun apply(invoker: A, receiver: B): Boolean {
        if (!checkRequirement(invoker, receiver)) {
            return false
        }
        return applyEffect(invoker, receiver)
    }

    protected abstract fun applyEffect(invoker: A, receiver: B): Boolean

    private fun checkRequirement(invoker: A, receiver: B): Boolean {
        val invokerRequirement = getRequirement(invoker)
        val receiverResistance = getResistance(receiver)
        val checkResult = invokerRequirement > receiverResistance
        logger.info(
            "checkResult={} invoker.requirement={} invoker.uuid={} receiver.resistance={} receiver.uuid={} uuid={}",
            checkResult,
            invokerRequirement,
            invoker.uuid,
            receiverResistance,
            receiver.uuid,
            uuid
        )
        return checkResult
    }

    protected abstract fun getRequirement(invoker: A): Int

    protected abstract fun getResistance(receiver: B): Int
}