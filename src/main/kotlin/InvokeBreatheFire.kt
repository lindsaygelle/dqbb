package dqbb

class InvokeBreatheFire<A : BreatheFireInvoker, B : BreatheFireReceiver>(
    magicCost: Int,
) : Magic<A, B>(
    magicCost = magicCost
) {
    override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        val breatheFireValue = maxOf(1, getBreatheFireValue(invoker))
        val breatheFireReduction = maxOf(1, getBreatheFireReduction(receiver))
        receiver.hitPoints -= (breatheFireValue / breatheFireReduction)
        logger.info(
            "invoker.breatheFireValue={} invoker.uuid={} receiver.breatheFireReduction={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.uuid={} uuid={}",
            breatheFireValue,
            invoker.uuid,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            breatheFireReduction,
            receiver.uuid,
            uuid
        )
        return receiver.hitPoints < hitPoints
    }

    override fun checkReceiver(receiver: B): Boolean {
        val hasHitPoints = receiver.hasHitPoints
        logger.debug(
            "receiver.hasHitPoints={} receiver.hitPoints={} receiver.hitPointsMaximum={} receiver.uuid={} uuid={}",
            hasHitPoints,
            receiver.hitPoints,
            receiver.hitPointsMaximum,
            receiver.uuid,
            uuid
        )
        return hasHitPoints
    }

    private fun getBreatheFireReduction(receiver: B): Int {
        val breatheFireReduction = receiver.armor?.breatheFireReduction ?: 1
        logger.info(
            "receiver.armor={} receiver.breatheFireReduction={} receiver.uuid={} uuid={}",
            receiver.armor,
            breatheFireReduction,
            receiver.uuid,
            uuid
        )
        return breatheFireReduction
    }

    private fun getBreatheFireValue(invoker: A): Int {
        val breatheFire = invoker.breatheFire
        logger.debug(
            "invoker.breatheFire={} invoker.breatheFireRangeMaximum={} invoker.breatheFireRangeMinimum={} invoker.breatheFireScale={} invoker.breatheFireShift={} uuid={}",
            breatheFire,
            invoker.breatheFireRangeMaximum,
            invoker.breatheFireRangeMinimum,
            invoker.breatheFireScale,
            invoker.breatheFireShift,
            uuid
        )
        return breatheFire
    }
}