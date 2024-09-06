package dqbb

abstract class HurtMagic<A, B : HurtReceiver>(
    magicCost: Int,
) : RequirementMagic<A, B>(
    magicCost = magicCost
) where A : HurtRanger, A : HurtRequester, A : MagicInvoker {
    final override fun applyEffect(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        val hurtPoints = maxOf(0, getHurtPoints(invoker))
        val hurtPointsReduction = maxOf(0, getHurtPointsReduction(receiver))
        val damagePoints = getDamagePoints(hurtPoints, hurtPointsReduction)
        receiver.hitPoints -= damagePoints
        logger.info(
            "damagePoints={} hurtPoints={} hurtPointsReduction={} id={} invoker.id={} invoker.simpleName={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            damagePoints,
            hurtPoints,
            hurtPointsReduction,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.hitPoints,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return receiver.hitPoints < hitPoints
    }

    final override fun checkReceiver(receiver: B): Boolean {
        logger.trace(
            "id={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkReceiverHitPoints(receiver)
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        logger.info(
            "id={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.hitPoints,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return receiver.hitPoints > 0
    }

    protected abstract fun getHurtPoints(invoker: A): Int

    private fun getHurtPointsReduction(receiver: B): Int {
        val hurtReduction = receiver.armor?.hurtReduction ?: 0
        logger.info(
            "id={} receiver.armor.id={} receiver.armor.hurtReduction={} receiver.id={} receiver.simpleName={}",
            id,
            receiver.armor?.id,
            receiver.armor?.hurtReduction,
            receiver.id,
            receiver.simpleName
        )
        return hurtReduction
    }

    private fun getDamagePoints(hurtPoints: Int, hurtPointsReduction: Int): Int {
        logger.trace(
            "hurtPoints={} hurtPointsReduction={} id={} simpleName={}", hurtPoints, hurtPointsReduction, id, simpleName
        )
        return (hurtPoints - (hurtPoints * (hurtPointsReduction.toDouble() / 100))).toInt()
    }

    override fun getInvokerRequirement(invoker: A): Int {
        val hurtRequirement = invoker.hurtRequirement
        logger.info(
            "id={} invoker.id={} invoker.hurtRequirement={} invoker.hurtRequirementMaximum={} invoker.hurtRequirementMaximum={} invoker.simpleName={}",
            id,
            invoker.id,
            hurtRequirement,
            invoker.hurtRequirementMaximum,
            invoker.hurtRequirementMinimum,
            invoker.simpleName
            )
        return hurtRequirement
    }

    override fun getReceiverResistance(receiver: B): Int {
        val hurtResistance = receiver.hurtResistance
        logger.info(
            "id={} receiver.id={} receiver.hurtResistance={} receiver.hurtResistanceMaximum={} receiver.hurtResistanceMinimum={} receiver.simpleName={}",
            id,
            receiver.id,
            hurtResistance,
            receiver.hurtResistanceMaximum,
            receiver.hurtResistanceMinimum,
            receiver.simpleName
            )
        return hurtResistance
    }
}