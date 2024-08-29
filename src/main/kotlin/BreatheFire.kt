package dqbb

class BreatheFire<A : BreatheFireInvoker, B : BreatheFireReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    magicCost = magicCost
) {
    override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        val breatheFirePoints = maxOf(0, getBreatheFirePoints(invoker))
        val breatheFirePointsReduction = maxOf(0, getBreatheFirePointsReduction(receiver))
        val damagePoints = getDamagePoints(breatheFirePoints, breatheFirePointsReduction)
        receiver.hitPoints -= damagePoints
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
        return receiver.hitPoints < hitPoints
    }

    override fun checkReceiver(receiver: B): Boolean {
        logger.trace(
            "id={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkReceiverHitPoints(receiver)
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

    private fun getBreatheFirePoints(invoker: A): Int {
        val breatheFire = invoker.breatheFire
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
        val breatheFireReduction = receiver.armor?.breatheFireReduction ?: 0
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
        logger.trace(
            "breatheFirePoints={} breatheFirePointsReduction={} id={} simpleName={}",
            breatheFirePoints,
            breatheFirePointsReduction,
            id,
            simpleName
        )
        return (breatheFirePoints - (breatheFirePoints * (breatheFirePointsReduction.toDouble() / 100))).toInt()
    }
}