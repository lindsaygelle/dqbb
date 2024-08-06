package dqbb

class SpellHeal<A : InvokerHeal, B : Receiver>(magicCost: Int) : Magic<A, B>(magicCost = magicCost) {
    override fun applyEffect(invoker: A, receiver: B) {
        val healRangeMaximum = invoker.healRangeMaximum
        val healRangeMinimum = invoker.healRangeMinimum
        val healRange = (invoker.healRangeMinimum..invoker.healRangeMaximum)
        val healRangeValue = healRange.random()
        val healScale = invoker.healScale
        val healShift = invoker.healShift
        val healValue = (healRangeValue and healShift) + healScale
        receiver.hitPoints += healValue
        logger.debug(
            "invoker.hashCode=${invoker.hashCode()} " +
            "invoker.healRangeMaximum=$healRangeMaximum " +
            "invoker.healRangeMinimum=$healRangeMinimum " +
            "invoker.healRangeValue=$healRangeValue " +
            "invoker.healScale=$healScale " +
            "invoker.healShift=$healShift " +
            "invoker.healValue=$healValue " +
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
