package dqbb

class Herb<A : HerbInvoker, B : HealReceiver> : AbilityItem<A, B>(
    itemName = ItemName.HERB
) {
    override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        val herbPoints = maxOf(0, getHerbPoints(invoker))
        receiver.hitPoints += herbPoints
        logger.info(
            "herbPoints={} id={} invoker.id={} invoker.simpleName={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.id={} receiver.simpleName={} simpleName={}",
            herbPoints,
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

    override fun checkReceiver(receiver: B): Boolean {
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

    private fun getHerbPoints(invoker: A): Int {
        val herb = invoker.herb
        logger.info(
            "id={} invoker.herb={} invoker.herbRangeMaximum={} invoker.herbRangeMinimum={} invoker.herbScale={} invoker.herbShift={} invoker.id={} invoker.simpleName={} simpleName={}",
            id,
            herb,
            invoker.herbRangeMaximum,
            invoker.herbRangeMinimum,
            invoker.herbScale,
            invoker.herbShift,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return herb
    }
}