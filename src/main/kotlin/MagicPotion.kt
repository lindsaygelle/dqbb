package dqbb

class MagicPotion<A : MagicPotionInvoker, B : MagicReceiver> : AbilityItem<A, B>(
    itemName = ItemName.MAGIC_POTION
) {
    override fun apply(invoker: A, receiver: B): Boolean {
        val magicPoints: Int = receiver.magicPoints
        val magicPotionPoints: Int = maxOf(0, getInvokerMagicPotionPoints(invoker))
        receiver.magicPoints += magicPotionPoints
        val checkValue: Boolean = receiver.magicPoints > magicPoints
        logger.info(
            "checkValue={} id={} invoker.id={} invoker.simpleName={} magicPotionPoints={} receiver.id={} receiver.magicPoints={} receiver.magicPointsMaximum={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            invoker.id,
            invoker.simpleName,
            magicPotionPoints,
            receiver.id,
            receiver.magicPoints,
            receiver.magicPointsMaximum,
            receiver.simpleName,
            simpleName
        )
        return checkValue
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

    private fun getInvokerMagicPotionPoints(invoker: A): Int {
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
}