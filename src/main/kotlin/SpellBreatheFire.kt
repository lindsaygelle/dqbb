package dqbb

class SpellBreatheFire<A : InvokerBreatheFire, B : Receiver>(magicCost: Int) : Magic<A, B>(magicCost = magicCost) {
    override fun applyEffect(invoker: A, receiver: B) {
        val breatheFireRangeMaximum = invoker.breatheFireRangeMaximum
        val breatheFireRangeMinimum = invoker.breatheFireRangeMinimum
        val breatheFireRange = (invoker.breatheFireRangeMinimum..invoker.breatheFireRangeMaximum)
        val breatheFireRangeValue = breatheFireRange.random()
        val breatheFireScale = invoker.breatheFireScale
        val breatheFireShift = invoker.breatheFireShift
        val breatheFireValue = (breatheFireRangeValue and breatheFireShift) + breatheFireScale
        receiver.hitPoints -= breatheFireValue
        logger.debug(
            "invoker.breatheFireRangeMaximum=$breatheFireRangeMaximum " +
            "invoker.breatheFireRangeMinimum=$breatheFireRangeMinimum " +
            "invoker.breatheFireRangeValue=$breatheFireRangeValue " +
            "invoker.breatheFireScale=$breatheFireScale " +
            "invoker.breatheFireShift=$breatheFireShift " +
            "invoker.breatheFireValue=$breatheFireValue " +
            "invoker.hashCode=${invoker.hashCode()} " +
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