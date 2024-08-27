package dqbb

class Herb<A : HerbInvoker, B : HealReceiver> : AbilityItem<A, B>(
    itemName = ItemName.HERB
) {
    override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        val herbPoints = maxOf(0, getHerbPoints(invoker))
        receiver.hitPoints += herbPoints
        logger.info(
            "herbPoints={} id={} invoker.id={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.id={}",
            herbPoints,
            id,
            invoker.id,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            receiver.id
        )
        return receiver.hitPoints > hitPoints
    }

    override fun checkReceiver(receiver: B): Boolean {
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
    
    private fun getHerbPoints(invoker: A): Int {
        val herb = invoker.herb
        logger.debug(
            "id={} invoker.herb={} invoker.herbRangeMaximum={} invoker.herbRangeMinimum={} invoker.herbScale={} invoker.herbShift={} invoker.id={}",
            id,
            herb,
            invoker.herbRangeMaximum,
            invoker.herbRangeMinimum,
            invoker.herbScale,
            invoker.herbShift,
            invoker.id
        )
        return herb
    }
}