package dqbb

abstract class AbilityMagic<A : MagicInvoker, B : Receiver>(
    val magicCost: Int,
) : Ability<A, B>() {
    final override fun checkInvoker(invoker: A): Boolean {
        return super.checkInvoker(invoker) && checkInvokerMagicCost(invoker) && checkInvokerTurnsStopSpell(invoker)
    }

    private fun checkInvokerMagicCost(invoker: A): Boolean {
        logger.debug(
            "id={} invoker.id={} invoker.magicPoints={} magicCost={}",
            id,
            invoker.id,
            invoker.magicPoints,
            magicCost,
        )
        return (invoker.magicPoints - magicCost) >= 0
    }

    private fun checkInvokerTurnsStopSpell(invoker: A): Boolean {
        logger.debug(
            "id={} invoker.id={} invoker.turnsStopSpell={}", id, invoker.id, invoker.turnsStopSpell
        )
        return invoker.turnsStopSpell == 0
    }

    private fun reduceInvokerMagicPoints(invoker: A) {
        invoker.magicPoints -= magicCost
        logger.debug(
            "id={} invoker.id={} invoker.magicPoints={}", id, invoker.id, invoker.magicPoints
        )
    }

    final override fun use(invoker: A, receiver: B): Boolean {
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