package dqbb

class Herb<A : HerbInvoker, B : HealReceiver> : AbilityItem<A, B>(
    itemName = ItemName.HERB
) {
    override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints: Int = receiver.hitPoints
        val herbPoints: Int = maxOf(0, getHerbPoints(invoker))
        receiver.hitPoints += herbPoints
        val checkValue: Boolean = receiver.hitPoints > hitPoints
        logger.info(
            "checkValue={} herbPoints={} id={} invoker.id={} invoker.simpleName={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
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
        return checkValue
    }

    override fun checkReceiver(receiver: B): Boolean {
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

    private fun getHerbPoints(invoker: A): Int {
        val herb: Int = invoker.herb
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