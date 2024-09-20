package dqbb

abstract class AbilityMagic<A : MagicInvoker, B : Receiver>(
    magicCost: Int,
) : Ability<A, B>() {
    var magicCost: Int = magicCost
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} magicCost={} simpleName={}", id, field, simpleName
            )
        }

    final override fun apply(invoker: A, receiver: B): Reviewable {
        if (!checkReceiver(receiver)) {
            return getReviewableReceiverInvalid(invoker, receiver)
        }
        reduceInvokerMagicPoints(invoker)
        return applyEffect(invoker, receiver)
    }

    protected abstract fun applyEffect(invoker: A, receiver: B): Reviewable

    final override fun checkInvoker(invoker: A): Boolean {
        return super.checkInvoker(invoker) && checkInvokerMagicCost(invoker) && checkInvokerTurnsStopSpell(invoker)
    }

    private fun checkInvokerMagicCost(invoker: A): Boolean {
        val checkValue: Boolean = (invoker.magicPoints - magicCost) >= 0
        logger.info(
            "checkValue={} id={} invoker.id={} invoker.magicPoints={} invoker.simpleName={} magicCost={} simpleName={}",
            checkValue,
            id,
            invoker.id,
            invoker.magicPoints,
            invoker.simpleName,
            magicCost,
            simpleName
        )
        return checkValue
    }

    private fun checkInvokerTurnsStopSpell(invoker: A): Boolean {
        val checkValue: Boolean = invoker.turnsStopSpell == 0
        logger.info(
            "checkValue={} id={} invoker.id={} invoker.simpleName={} invoker.turnsStopSpell={} simpleName={}",
            checkValue,
            id,
            invoker.id,
            invoker.simpleName,
            invoker.turnsStopSpell,
            simpleName
        )
        return checkValue
    }

    protected abstract fun getReviewableReceiverInvalid(invoker: A, receiver: B): Reviewable

    private fun reduceInvokerMagicPoints(invoker: A) {
        invoker.magicPoints -= magicCost
        logger.info(
            "id={} invoker.id={} invoker.magicPoints={} invoker.simpleName={} magicCost={} simpleName={}",
            id,
            invoker.id,
            invoker.magicPoints,
            invoker.simpleName,
            magicCost,
            simpleName
        )
    }

    override fun toString(): String {
        return "id=$id magicCost=$magicCost simpleName=$simpleName"
    }
}