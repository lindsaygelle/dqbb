package dqbb

class MagicPotion<A : MagicPotionInvoker, B : MagicReceiver> : AbilityItem<A, B>(
    itemName = ItemName.MAGIC_POTION
) {
    override fun apply(invoker: A, receiver: B): Boolean {
        val magicPoints = receiver.magicPoints
        val magicPotionPoints = getInvokerMagicPotionPoints(invoker)
        receiver.magicPoints += magicPotionPoints
        logger.debug(
            "id={} magicPotionPoints={} receiver.id={} receiver.magicPoints={} receiver.magicPointsMaximum={}",
            id,
            magicPotionPoints,
            receiver.id,
            receiver.magicPoints,
            receiver.magicPointsMaximum,
        )
        return receiver.magicPoints > magicPoints
    }

    override fun checkReceiver(receiver: B): Boolean {
        return checkReceiverHitPoints(receiver) && checkInvokerMagicPoints(receiver)
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.hitPoints={} receiver.id={}", id, receiver.hitPoints, receiver.id,
        )
        return receiver.hitPoints > 0
    }

    private fun checkInvokerMagicPoints(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.id={} receiver.magicPoints={} receiver.magicPointsMaximum={}",
            id,
            receiver.id,
            receiver.magicPoints,
            receiver.magicPointsMaximum,
        )
        return receiver.magicPoints < receiver.magicPointsMaximum
    }

    private fun getInvokerMagicPotionPoints(invoker: A): Int {
        val magicPotion = invoker.magicPotion
        logger.debug(
            "id={} invoker.magicPotion={} invoker.magicPotionRangeMaximum={} invoker.magicPotionRangeMinimum={} invoker.magicPotionScale={} invoker.magicPotionShift={} invoker.id={}",
            id,
            magicPotion,
            invoker.magicPotionRangeMaximum,
            invoker.magicPotionRangeMinimum,
            invoker.magicPotionScale,
            invoker.magicPotionShift,
            invoker.id
        )
        return magicPotion
    }
}