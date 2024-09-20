package dqbb

class BreatheFire<A : BreatheFireInvoker, B : BreatheFireReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    magicCost = magicCost
) {
    override fun applyEffect(invoker: A, receiver: B): Reviewable {
        val breatheFirePointsReduction: Int = getBreatheFirePointsReduction(receiver)
        val breatheFirePoints: Int = getBreatheFirePoints(invoker)
        val breatheFire: Int = getBreatheFire(breatheFirePoints, breatheFirePointsReduction)
        receiver.hitPoints -= breatheFire
        logger.info(
            "id={} invoker.breatheFire={} invoker.breatheFirePoints={} invoker.id={} invoker.simpleName={} receiver.armor.breatheFireReduction={} receiver.armor.id={} receiver.armor.name={} receiver.armor.simpleName={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            breatheFire,
            breatheFirePoints,
            invoker.id,
            invoker.simpleName,
            receiver.hitPoints,
            receiver.armor?.breatheFireReduction,
            receiver.armor?.id,
            receiver.armor?.name,
            receiver.armor?.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return getReviewable(invoker, breatheFire, receiver)
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
        val breatheFire: Int = maxOf(0, invoker.breatheFire)
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
        logger.info(
            "id={} receiver.armor.breatheFireReduction={} receiver.armor.id={} receiver.armor.name={} receiver.armor.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.armor?.id,
            receiver.armor?.breatheFireReduction,
            receiver.armor?.name,
            receiver.armor?.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return receiver.armor?.breatheFireReduction ?: 0
    }

    private fun getBreatheFire(breatheFirePoints: Int, breatheFirePointsReduction: Int): Int {
        val breatheFire: Int =
            (breatheFirePoints - (breatheFirePoints * (breatheFirePointsReduction.toDouble() / 100))).toInt()
        logger.info(
            "breatheFire={} breatheFirePoints={} breatheFirePointsReduction={} id={} simpleName={}",
            breatheFire,
            breatheFirePoints,
            breatheFirePointsReduction,
            id,
            simpleName
        )
        return breatheFire
    }

    private fun getReviewable(invoker: A, invokerBreatheFire: Int?, receiver: B): Reviewable {
        return ReviewBreatheFire(
            id,
            simpleName,
            invokerBreatheFire,
            invoker.id,
            invoker.name,
            invoker.simpleName,
            magicCost,
            receiver.armor?.breatheFireReduction,
            receiver.armor?.id,
            receiver.armor?.name,
            receiver.armor?.simpleName,
            receiver.hitPoints,
            receiver.id,
            receiver.name,
            receiver.simpleName,
        )
    }

    override fun getReviewableInvokerInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, null, receiver)
    }

    override fun getReviewableReceiverInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, null, receiver)
    }
}