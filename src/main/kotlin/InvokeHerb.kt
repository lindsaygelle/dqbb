package dqbb

class InvokeHerb<A : HerbInvoker, B : HerbReceiver> : ConsumeItem<A, B>(itemName = ItemName.HERB) {
    override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        val herbValue = maxOf(1, getHerbValue(invoker))
        receiver.hitPoints += herbValue
        logger.info(
            "invoker.herbValue={} invoker.uuid={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.uuid={} uuid={}",
            herbValue,
            invoker.uuid,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            receiver.uuid,
            uuid
        )
        return receiver.hitPoints > hitPoints
    }

    override fun checkReceiver(receiver: B): Boolean {
        return checkReceiverHitPoints(receiver) && checkReceiverHitPointsMaximum(receiver)
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        return receiver.hasHitPoints
    }

    private fun checkReceiverHitPointsMaximum(receiver: B): Boolean {
        return !receiver.hasHitPointsMaximum
    }

    private fun getHerbValue(invoker: A): Int {
        val herb = invoker.herb
        logger.debug(
            "invoker.herb={} invoker.herbRangeMaximum={} invoker.herbRangeMinimum={} invoker.herbScale={} invoker.herbShift={} invoker.uuid={} uuid={}",
            herb,
            invoker.herbRangeMaximum,
            invoker.herbRangeMinimum,
            invoker.herbScale,
            invoker.herbShift,
            invoker.uuid,
            uuid,
        )
        return herb
    }
}