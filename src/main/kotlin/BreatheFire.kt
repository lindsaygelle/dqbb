package dqbb

class BreatheFire<A : BreatheFireInvoker, B : BreatheFireReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    magicCost = magicCost
) {
    override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints: Int = receiver.hitPoints
        val breatheFirePoints: Int = maxOf(0, getBreatheFirePoints(invoker))
        val breatheFirePointsReduction: Int = maxOf(0, getBreatheFirePointsReduction(receiver))
        val damagePoints: Int = getDamagePoints(breatheFirePoints, breatheFirePointsReduction)
        receiver.hitPoints -= damagePoints
        val checkValue: Boolean = receiver.hitPoints < hitPoints
        logger.info(
            "breatheFirePoints={} breatheFirePointsReduction={} damagePoints={} id={} invoker.id={} invoker.simpleName={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            breatheFirePoints,
            breatheFirePointsReduction,
            damagePoints,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.hitPoints,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkValue
    }

    override fun checkReceiver(receiver: B): Boolean {
        val checkValue: Boolean = checkReceiverHitPoints(receiver)
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

    private fun getBreatheFirePoints(invoker: A): Int {
        val breatheFire: Int = invoker.breatheFire
        logger.info(
            "id={} invoker.breatheFire={} invoker.breatheFireRangeMaximum={} invoker.breatheFireRangeMinimum={} invoker.breatheFireScale={} invoker.breatheFireShift={} invoker.id={} invoker.simpleName={} simpleName={}",
            id,
            breatheFire,
            invoker.breatheFireRangeMaximum,
            invoker.breatheFireRangeMinimum,
            invoker.breatheFireScale,
            invoker.breatheFireShift,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return breatheFire
    }

    private fun getBreatheFirePointsReduction(receiver: B): Int {
        val breatheFireReduction: Int = receiver.armor?.breatheFireReduction ?: 0
        logger.info(
            "id={} receiver.armor.id={} receiver.armor.breatheFireReduction={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.armor?.id,
            receiver.armor?.breatheFireReduction,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return breatheFireReduction
    }

    private fun getDamagePoints(breatheFirePoints: Int, breatheFirePointsReduction: Int): Int {
        val damagePoints: Double =
            (breatheFirePoints - (breatheFirePoints * (breatheFirePointsReduction.toDouble() / 100)))
        logger.info(
            "damagePoints={} breatheFirePoints={} breatheFirePointsReduction={} id={} simpleName={}",
            damagePoints,
            breatheFirePoints,
            breatheFirePointsReduction,
            id,
            simpleName
        )
        return damagePoints.toInt()
    }
}