package dqbb

class SpellHealMore<A : InvokerHealMore, B : Receiver>(magicCost: Int) : Magic<A, B>(
    magicCost = magicCost
) {
    override fun applyEffect(invoker: A, receiver: B) {
        val healMoreRangeMaximum = invoker.healMoreRangeMaximum
        val healMoreRangeMinimum = invoker.healMoreRangeMinimum
        val healMoreRange = (invoker.healMoreRangeMinimum..invoker.healMoreRangeMaximum)
        val healMoreRangeValue = healMoreRange.random()
        val healMoreScale = invoker.healMoreScale
        val healMoreShift = invoker.healMoreShift
        val healMoreValue = (healMoreRangeValue and healMoreShift) + healMoreScale
        receiver.hitPoints += healMoreValue
        logger.debug(
            "invoker.hashCode=${invoker.hashCode()} " +
            "invoker.healMoreRangeMaximum=$healMoreRangeMaximum " +
            "invoker.healMoreRangeMinimum=$healMoreRangeMinimum " +
            "invoker.healMoreRangeValue=$healMoreRangeValue " +
            "invoker.healMoreScale=$healMoreScale " +
            "invoker.healMoreShift=$healMoreShift " +
            "invoker.healMoreValue=$healMoreValue " +
            "receiver.hashCode=${receiver.hashCode()} " +
            "receiver.hitPoints=${receiver.hitPoints}"
        )
    }

    override fun checkResistance(invoker: A, receiver: B): Boolean {
        val isNotAlive = receiver.isNotAlive
        logger.debug("receiver.hashCode=${receiver.hashCode()} receiver.isNotAlive=$isNotAlive")
        return isNotAlive
    }
}