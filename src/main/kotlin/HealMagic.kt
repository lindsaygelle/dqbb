package dqbb

abstract class HealMagic<A, B : HealReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    magicCost = magicCost
) where A : HealRanger, A : MagicInvoker {
    final override fun apply(invoker: A, receiver: B): Boolean {
        val healPoints: Int = getHealPoints(invoker)
        val hitPoints: Int = receiver.hitPoints
        receiver.hitPoints += healPoints
        val checkValue: Boolean = receiver.hitPoints > hitPoints
        logger.info(
            "checkValue={} healPoints={} id={} invoker.id={} invoker.simpleName={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            healPoints,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkValue
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
}