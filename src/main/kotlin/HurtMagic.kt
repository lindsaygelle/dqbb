package dqbb

abstract class HurtMagic<A, B : HurtReceiver>(
    magicCost: Int,
) : RequirementMagic<A, B>(
    magicCost
) where A : HurtRequester, A : MagicInvoker {
    override fun applyEffect(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        val hurtValue = maxOf(1, getHurtValue(invoker))
        val hurtReduction = maxOf(1, getHurtReduction(receiver))
        receiver.hitPoints -= (hurtValue / hurtReduction)
        logger.info(
            "invoker.hurtValue={} invoker.uuid={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.hurtReduction={} receiver.uuid={} uuid={}",
            hurtValue,
            invoker.uuid,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            hurtReduction,
            receiver.uuid,
            uuid
        )
        return receiver.hitPoints < hitPoints
    }

    final override fun checkReceiver(receiver: B): Boolean {
        val hasHitPoints = receiver.hasHitPoints
        logger.debug(
            "receiver.hasHitPoints={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.uuid={} uuid={}",
            hasHitPoints,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            receiver.uuid,
            uuid
        )
        return hasHitPoints
    }

    private fun getHurtReduction(receiver: B): Int {
        val hurtReduction = receiver.armor?.hurtReduction ?: 1
        logger.info(
            "receiver.armor={} receiver.hurtReduction={} receiver.uuid={} uuid={}",
            receiver.armor,
            hurtReduction,
            receiver.uuid,
            uuid
        )
        return hurtReduction
    }

    abstract fun getHurtValue(invoker: A): Int

    final override fun getRequirement(invoker: A): Int {
        val hurtRequirement = invoker.hurtRequirement
        logger.debug(
            "invoker.hurtRequirement={} invoker.hurtRequirementMaximum={} invoker.hurtRequirementMinimum={} invoker.uuid={} uuid={}",
            hurtRequirement,
            invoker.hurtRequirementMaximum,
            invoker.hurtRequirementMinimum,
            invoker.uuid,
            uuid
        )
        return hurtRequirement
    }

    final override fun getResistance(receiver: B): Int {
        val hurtResistance = receiver.hurtResistance
        logger.debug(
            "receiver.hurtResistance={} receiver.hurtResistanceMaximum={} receiver.hurtResistanceMinimum={} receiver.uuid={} uuid={}",
            hurtResistance,
            receiver.hurtResistanceMaximum,
            receiver.hurtResistanceMinimum,
            receiver.uuid,
            uuid
        )
        return hurtResistance
    }
}