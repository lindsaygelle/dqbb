package dqbb

abstract class AbilityMagic<A, B : Receiver>(
    invokable: Magic<A, B>,
) : Ability<A, B>(
    invokable = invokable,
) where A : MagicInvoker, A : HitPointer, A : SleepAccumulator, A : StopSpellAccumulator {
    override fun checkInvoker(invoker: A): Boolean {
        return super.checkInvoker(invoker) &&
                checkInvokerMagicPoints(invoker) &&
                checkInvokerStatusStopSpell(invoker)
    }

    private fun checkInvokerMagicPoints(invoker: A): Boolean {
        logger.debug(
            "invoker.magicPoints={} invoker.uuid={} uuid={}",
            invoker.magicPoints,
            invoker.uuid,
            uuid
        )
        return invoker.hasMagicPoints
    }

    private fun checkInvokerStatusStopSpell(invoker: A): Boolean {
        val statusStopSpell = invoker.statusStopSpell
        logger.debug(
            "invoker.statusStopSpell={} invoker.uuid={} uuid={}",
            statusStopSpell,
            invoker.uuid,
            uuid
        )
        return !statusStopSpell
    }
}