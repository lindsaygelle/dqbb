package dqbb

abstract class AbilityMagic<A : MagicInvoker, B : Receiver>(
    val magicCost: Int,
) : Ability<A, B>() {
    final override fun checkInvoker(invoker: A): Boolean {
        logger.trace(
            "id={} invoker.id={} invoker.simpleName={} magicCost={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            magicCost,
            simpleName
        )
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

    final override fun use(invoker: A, receiver: B): Boolean {
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} magicCost={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            magicCost,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        if (!checkInvoker(invoker) || !checkReceiver(receiver)) {
            return false
        }
        reduceInvokerMagicPoints(invoker)
        return apply(invoker, receiver)
    }

    override fun toString(): String {
        return "id=$id magicCost=$magicCost simpleName=$simpleName"
    }
}