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
            "damagePoints={} hurtPoints={} hurtPointsReduction={} id={} invoker.id={} receiver.hitPoints={} receiver.id={}",
            damagePoints,
            hurtPoints,
            hurtPointsReduction,
            id,
            invoker.id,
            receiver.hitPoints,
            receiver.id
        )
        return receiver.hitPoints < hitPoints
    }

    final override fun checkReceiver(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.hitPoints={} receiver.id={}", id, receiver.hitPoints, receiver.id
        )
        return receiver.hitPoints > 0
    }

    protected abstract fun getHurtPoints(invoker: A): Int

    private fun getHurtPointsReduction(receiver: B): Int {
        val hurtReduction = receiver.armor?.hurtReduction ?: 0
        logger.debug(
            "id={} receiver.armor.id={} receiver.armor.hurtReduction={} receiver.id={}",
            id,
            receiver.armor?.id,
            receiver.armor?.hurtReduction,
            receiver.id
        )
        return hurtReduction
    }

    private fun getDamagePoints(hurtPoints: Int, hurtPointsReduction: Int): Int {
        return (hurtPoints - (hurtPoints * (hurtPointsReduction.toDouble() / 100))).toInt()
    }

    override fun getRequirement(invoker: A): Int {
        val hurtRequirement = invoker.hurtRequirement
        logger.debug(
            "id={} invoker.id={} invoker.hurtRequirement={} invoker.hurtRequirementMaximum={} invoker.hurtRequirementMaximum={}",
            id,
            invoker.id,
            hurtRequirement,
            invoker.hurtRequirementMaximum,
            invoker.hurtRequirementMinimum
        )
        return hurtRequirement
    }

    override fun getResistance(receiver: B): Int {
        val hurtResistance = receiver.hurtResistance
        logger.debug(
            "id={} receiver.id={} receiver.hurtResistance={} receiver.hurtResistanceMaximum={} receiver.hurtResistanceMinimum={}",
            id,
            receiver.id,
            hurtResistance,
            receiver.hurtResistanceMaximum,
            receiver.hurtResistanceMinimum
        )
        return hurtResistance
    }
}