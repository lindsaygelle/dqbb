package dqbb

class BreatheFire<A : BreatheFireInvoker, B : BreatheFireReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    magicCost = magicCost
) {
    override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        val breatheFirePoints = maxOf(1, getBreatheFirePoints(invoker))
        val breatheFirePointsReduction = maxOf(1, getBreatheFirePointsReduction(receiver))
        val damagePoints = getDamagePoints(breatheFirePoints, breatheFirePointsReduction)
        receiver.hitPoints -= damagePoints
        logger.info(
            "breatheFirePoints={} breatheFirePointsReduction={} damagePoints={} id={} invoker.id={} receiver.hitPoints={} receiver.id={}",
            breatheFirePoints,
            breatheFirePointsReduction,
            damagePoints,
            id,
            invoker.id,
            receiver.hitPoints,
            receiver.id
        )
        return receiver.hitPoints < hitPoints
    }

    override fun checkReceiver(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.hitPoints={} receiver.id={}", id, receiver.hitPoints, receiver.id
        )
        return receiver.hitPoints > 0
    }

    private fun getBreatheFirePoints(invoker: A): Int {
        val breatheFire = invoker.breatheFire
        logger.debug(
            "id={} invoker.breatheFire={} invoker.breatheFireRangeMaximum={} invoker.breatheFireRangeMinimum={} invoker.breatheFireScale={} invoker.breatheFireShift={} invoker.id={}",
            id,
            breatheFire,
            invoker.breatheFireRangeMaximum,
            invoker.breatheFireRangeMinimum,
            invoker.breatheFireScale,
            invoker.breatheFireShift,
            invoker.id
        )
        return breatheFire
    }

    private fun getBreatheFirePointsReduction(receiver: B): Int {
        val breatheFireReduction = receiver.armor?.breatheFireReduction ?: 0
        logger.debug(
            "id={} receiver.armor.id={} receiver.armor.breatheFireReduction={} receiver.id={}",
            id,
            receiver.armor?.id,
            receiver.armor?.breatheFireReduction,
            receiver.id
        )
        return breatheFireReduction
    }

    private fun getDamagePoints(breatheFirePoints: Int, breatheFirePointsReduction: Int): Int {
        return (breatheFirePoints - (breatheFirePoints * (breatheFirePointsReduction.toDouble() / 100))).toInt()
    }
}