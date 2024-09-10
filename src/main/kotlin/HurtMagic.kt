package dqbb

abstract class HurtMagic<A, B : HurtReceiver>(
    magicCost: Int,
) : RequirementMagic<A, B>(
    magicCost = magicCost
) where A : HurtRanger, A : HurtRequester, A : MagicInvoker {
    final override fun applyEffect(invoker: A, receiver: B): Boolean {
        val hitPoints: Int = receiver.hitPoints
        val hurtPoints: Int = maxOf(0, getHurtPoints(invoker))
        val hurtPointsReduction: Int = maxOf(0, getHurtPointsReduction(receiver))
        val damagePoints: Int = getDamagePoints(hurtPoints, hurtPointsReduction)
        receiver.hitPoints -= damagePoints
        val checkValue: Boolean = receiver.hitPoints < hitPoints
        logger.info(
            "checkValue={} damagePoints={} hurtPoints={} hurtPointsReduction={} id={} invoker.id={} invoker.simpleName={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
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
        return checkValue
    }

    final override fun checkReceiver(receiver: B): Boolean {
        val checkValue: Boolean = checkReceiverHitPoints(receiver)
        logger.info(
            "checkValue={} id={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkValue
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        val checkValue: Boolean = receiver.hitPoints > 0
        logger.info(
            "checkValue={} id={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            receiver.hitPoints,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkValue
    }

    protected abstract fun getHurtPoints(invoker: A): Int

    private fun getHurtPointsReduction(receiver: B): Int {
        val hurtReduction: Int = receiver.armor?.hurtReduction ?: 0
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
        val damagePoints: Double = (hurtPoints - (hurtPoints * (hurtPointsReduction.toDouble() / 100)))
        logger.info(
            "damagePoints={} hurtPoints={} hurtPointsReduction={} id={} simpleName={}",
            damagePoints,
            hurtPoints,
            hurtPointsReduction,
            id,
            simpleName
        )
        return damagePoints.toInt()
    }

    override fun getInvokerRequirement(invoker: A): Int {
        val hurtRequirement: Int = invoker.hurtRequirement
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
        val hurtResistance: Int = receiver.hurtResistance
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