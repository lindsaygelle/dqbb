package dqbb

abstract class HurtMagic<A, B : HurtReceiver>(
    magicCost: Int,
) : AbilityMagicRequirement<A, B>(
    magicCost = magicCost
) where A : HurtRanger, A : HurtRequester, A : MagicInvoker {
    final override fun applyUpdate(invoker: A, receiver: B): Reviewable {
        val hurtPointsReduction: Int = maxOf(0, getHurtPointsReduction(receiver))
        val hurtPoints: Int = maxOf(0, getHurtPoints(invoker))
        val hurt: Int = getHurt(hurtPoints, hurtPointsReduction)
        receiver.hitPoints -= hurt
        logger.info(
            "id={} invoker.hurt={} invoker.hurtPoints={} invoker.id={} invoker.simpleName={} receiver.armor.hurtReduction={} receiver.armor.id={} receiver.armor.name={} receiver.armor.simpleName={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            hurt,
            hurtPoints,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.hitPoints,
            receiver.armor?.hurtReduction,
            receiver.armor?.id,
            receiver.armor?.name,
            receiver.armor?.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return getReviewable(invoker, hurt, receiver)
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

    private fun getHurt(hurtPoints: Int, hurtPointsReduction: Int): Int {
        val hurt: Int = (hurtPoints - (hurtPoints * (hurtPointsReduction.toDouble() / 100))).toInt()
        logger.info(
            "hurt={} hurtPoints={} hurtPointsReduction={} id={} simpleName={}",
            hurt,
            hurtPoints,
            hurtPointsReduction,
            id,
            simpleName
        )
        return hurt
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

    private fun getReviewable(invoker: A, invokerHurt: Int?, receiver: B): Reviewable {
        return ReviewHurt(
            id,
            simpleName,
            invokerHurt,
            invoker.id,
            invoker.name,
            invoker.simpleName,
            magicCost,
            receiver.armor?.hurtReduction,
            receiver.armor?.id,
            receiver.armor?.name,
            receiver.armor?.simpleName,
            receiver.hitPoints,
            receiver.id,
            receiver.name,
            receiver.simpleName,
        )
    }

    override fun getReviewableInvokerInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, null, receiver)
    }

    override fun getReviewableReceiverInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, null, receiver)
    }

    override fun getReviewableRequirementInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, null, receiver)
    }
}