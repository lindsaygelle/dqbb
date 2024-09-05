package dqbb

class MagicPotion<A : MagicPotionInvoker, B : MagicReceiver> : AbilityItem<A, B>(
    itemName = ItemName.MAGIC_POTION
) {
    override fun apply(invoker: A, receiver: B): Boolean {
        val magicPoints = receiver.magicPoints
        val magicPotionPoints = maxOf(0, getInvokerMagicPotionPoints(invoker))
        receiver.magicPoints += magicPotionPoints
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} magicPotionPoints={} receiver.id={} receiver.magicPoints={} receiver.magicPointsMaximum={} receiver.simpleName={} simpleName={}",
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
        return receiver.magicPoints > magicPoints
    }

    override fun checkReceiver(receiver: B): Boolean {
        logger.trace(
            "id={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkReceiverHitPoints(receiver) && checkInvokerMagicPoints(receiver)
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

    private fun checkInvokerMagicPoints(receiver: B): Boolean {
        logger.info(
            "id={} receiver.id={} receiver.magicPoints={} receiver.magicPointsMaximum={} receiver.simpleName={} simpleName={}",
            id,
            receiver.id,
            receiver.magicPoints,
            receiver.magicPointsMaximum,
            receiver.simpleName,
            simpleName,
        )
        return receiver.magicPoints < receiver.magicPointsMaximum
    }

    private fun getInvokerMagicPotionPoints(invoker: A): Int {
        val magicPotion = invoker.magicPotion
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