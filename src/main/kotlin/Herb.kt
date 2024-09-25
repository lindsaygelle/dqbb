package dqbb

class Herb<A : HerbInvoker, B : HealReceiver> : AbilityItem<A, B>(
    itemName = ItemName.HERB
) {
    override fun applyEffect(invoker: A, receiver: B): Reviewable {
        val herbPoints: Int = maxOf(0, getHerbPoints(invoker))
        receiver.hitPoints += herbPoints
        logger.info(
            "id={} invoker.herb={} invoker.id={} invoker.simpleName={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.id={} receiver.simpleName={} simpleName={}",
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
        return getReviewable(invoker, herbPoints, true, receiver, true)
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

    private fun getReviewable(
        invoker: A,
        invokerHerb: Int?,
        invokerIsValid: Boolean,
        receiver: B,
        receiverIsValid: Boolean,
    ): Reviewable {
        return ReviewHerb(
            id,
            simpleName,
            invokerHerb,
            invoker.id,
            invokerIsValid,
            invoker.items.getOrDefault(itemName, 0),
            invoker.name,
            invoker.simpleName,
            receiver.hitPoints,
            receiver.id,
            receiverIsValid,
            receiver.name,
            receiver.simpleName,
        )
    }

    override fun getReviewableInvokerInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, null, false, receiver, false)
    }

    override fun getReviewableReceiverInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, null, true, receiver, false)
    }
}