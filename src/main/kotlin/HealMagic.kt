package dqbb

abstract class HealMagic<A, B : HealReceiver>(
    magicCost: Int,
) : Magic<A, B>(
    magicCost = magicCost
) where A : HealRanger, A : MagicInvoker {
    override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        val healValue = maxOf(1, getHealValue(invoker))
        receiver.hitPoints += healValue
        logger.info(
            "invoker.healValue={} invoker.uuid={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.uuid={} uuid={}",
            healValue,
            invoker.uuid,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            receiver.uuid,
            uuid
        )
        return receiver.hitPoints > hitPoints
    }

    final override fun checkReceiver(receiver: B): Boolean {
        val checkResult = checkReceiverHitPoints(receiver) && checkReceiverHitPointsMaximum(receiver)
        logger.info(
            "checkResult={} receiver.uuid={} uuid={}", checkResult, receiver.uuid, uuid
        )
        return checkResult
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        val hasHitPoints = receiver.hasHitPoints
        logger.debug(
            "receiver.hasHitPoints={} receiver.hitPoints={} receiver.uuid={} uuid={}",
            hasHitPoints,
            receiver.hitPoints,
            receiver.uuid,
            uuid
        )
        return hasHitPoints
    }

    private fun checkReceiverHitPointsMaximum(receiver: B): Boolean {
        val hasHitPointsMaximum = receiver.hasHitPointsMaximum
        logger.debug(
            "receiver.hasHitPointsMaximum={} receiver.hitPointsMaximum={} receiver.uuid={} uuid={}",
            hasHitPointsMaximum,
            receiver.hitPointsMaximum,
            receiver.uuid,
            uuid
        )
        return !hasHitPointsMaximum
    }

    protected abstract fun getHealValue(invoker: A): Int
}