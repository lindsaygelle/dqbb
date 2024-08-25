package dqbb

abstract class HealMagic<A, B : HealReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    magicCost = magicCost
) where A : HealRanger, A : MagicInvoker {
    final override fun apply(invoker: A, receiver: B): Boolean {
        val healPoints = getHealPoints(invoker)
        val hitPoints = receiver.hitPoints
        receiver.hitPoints += healPoints
        logger.info(
            "healPoints={} id={} invoker.id={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.id={}",
            healPoints,
            id,
            invoker.id,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            receiver.id
        )
        return receiver.hitPoints > hitPoints
    }

    final override fun checkReceiver(receiver: B): Boolean {
        return checkReceiverHitPoints(receiver) && checkReceiverHitPointsMaximum(receiver)
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.hitPoints={} receiver.id={}", id, receiver.hitPoints, receiver.id,
        )
        return receiver.hitPoints > 0
    }

    private fun checkReceiverHitPointsMaximum(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.id={}",
            id,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            receiver.id,
        )
        return receiver.hitPoints < receiver.hitPointsMaximum
    }

    protected abstract fun getHealPoints(invoker: A): Int
}