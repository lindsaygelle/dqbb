package dqbb

abstract class HealMagic<A, B : HealReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    magicCost = magicCost
) where A : HealRanger, A : MagicInvoker {
    final override fun applyEffect(invoker: A, receiver: B): Reviewable {
        val heal: Int = getHealPoints(invoker)
        receiver.hitPoints += heal
        logger.info(
            "id={} invoker.heal={} invoker.id={} invoker.simpleName={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.id={} receiver.simpleName={} simpleName={}",
            heal,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return getReviewable(invoker, heal, receiver)
    }

    final override fun checkReceiver(receiver: B): Boolean {
        val checkValue: Boolean = checkReceiverHitPoints(receiver) && checkReceiverHitPointsMaximum(receiver)
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

    private fun checkReceiverHitPointsMaximum(receiver: B): Boolean {
        val checkValue: Boolean = receiver.hitPoints < receiver.hitPointsMaximum
        logger.info(
            "checkValue={} id={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkValue
    }

    protected abstract fun getHealPoints(invoker: A): Int

    private fun getReviewable(invoker: A, invokerHeal: Int?, receiver: B): Reviewable {
        return ReviewHeal(
            id,
            simpleName,
            invokerHeal,
            invoker.id,
            invoker.magicPoints,
            invoker.name,
            invoker.simpleName,
            magicCost,
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
}