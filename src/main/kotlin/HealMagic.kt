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
            "healPoints={} id={} invoker.id={} invoker.simpleName={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.id={} receiver.simpleName={} simpleName={}",
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
        return receiver.hitPoints > hitPoints
    }

    final override fun checkReceiver(receiver: B): Boolean {
        logger.trace(
            "id={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkReceiverHitPoints(receiver) && checkReceiverHitPointsMaximum(receiver)
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

    private fun checkReceiverHitPointsMaximum(receiver: B): Boolean {
        logger.info(
            "id={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return receiver.hitPoints < receiver.hitPointsMaximum
    }

    protected abstract fun getHealPoints(invoker: A): Int
}