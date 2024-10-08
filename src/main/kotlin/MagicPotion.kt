package dqbb

class MagicPotion<A : MagicPotionInvoker, B : MagicReceiver> : AbilityItem<A, B>(
    itemName = ItemName.MAGIC_POTION
) {
    override fun applyEffect(invoker: A, receiver: B): Reviewable {
        val magicPotion: Int = maxOf(0, getMagicPotion(invoker))
        receiver.magicPoints += magicPotion
        logger.info(
            "id={} invoker.id={} invoker.magicPotion={} invoker.simpleName={} receiver.id={} receiver.magicPoints={} receiver.magicPointsMaximum={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            magicPotion,
            invoker.simpleName,
            receiver.id,
            receiver.magicPoints,
            receiver.magicPointsMaximum,
            receiver.simpleName,
            simpleName
        )
        return getReviewable(invoker, true, magicPotion, receiver, true)
    }

    override fun checkReceiver(receiver: B): Boolean {
        val checkValue: Boolean = checkReceiverHitPoints(receiver) && checkInvokerMagicPoints(receiver)
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

    private fun checkInvokerMagicPoints(receiver: B): Boolean {
        val checkValue: Boolean = receiver.magicPoints < receiver.magicPointsMaximum
        logger.info(
            "checkValue={} id={} receiver.id={} receiver.magicPoints={} receiver.magicPointsMaximum={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            receiver.id,
            receiver.magicPoints,
            receiver.magicPointsMaximum,
            receiver.simpleName,
            simpleName,
        )
        return checkValue
    }

    private fun getMagicPotion(invoker: A): Int {
        val magicPotion: Int = invoker.magicPotion
        logger.info(
            "id={} invoker.id={} invoker.magicPotion={} invoker.magicPotionRangeMaximum={} invoker.magicPotionRangeMinimum={} invoker.magicPotionScale={} invoker.magicPotionShift={} invoker.simpleName={} simpleName={}",
            id,
            magicPotion,
            invoker.id,
            invoker.magicPotionRangeMaximum,
            invoker.magicPotionRangeMinimum,
            invoker.magicPotionScale,
            invoker.magicPotionShift,
            invoker.simpleName,
            simpleName
        )
        return magicPotion
    }

    private fun getReviewable(
        invoker: A,
        invokerIsValid: Boolean,
        invokerMagicPotionPoints: Int?,
        receiver: B,
        receiverIsValid: Boolean,
    ): Reviewable {
        return ReviewMagicPotion(
            id,
            simpleName,
            invoker.id,
            invokerIsValid,
            invoker.items.getOrDefault(itemName, 0),
            invokerMagicPotionPoints,
            invoker.name,
            invoker.simpleName,
            receiver.id,
            receiverIsValid,
            receiver.magicPoints,
            receiver.magicPointsMaximum,
            receiver.name,
            receiver.simpleName,
        )
    }

    override fun getReviewableInvokerInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, false, null, receiver, false)
    }

    override fun getReviewableReceiverInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, true, null, receiver, false)
    }
}