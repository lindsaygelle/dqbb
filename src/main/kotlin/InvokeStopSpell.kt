package dqbb

class InvokeStopSpell<A : StopSpellInvoker, B : StopSpellReceiver>(
    magicCost: Int,
) : RequirementMagic<A, B>(
    magicCost = magicCost
) {
    override fun applyEffect(invoker: A, receiver: B): Boolean {
        if (receiver.armor?.blocksStopSpell == true) {
            return false
        }
        receiver.turnsStopSpell = 1
        val statusStopSpell = receiver.statusStopSpell
        logger.info(
            "invoker.uuid={} receiver.statusStopSpell={} receiver.turnsStopSpell={} receiver.turnsStopSpellMaximum={} receiver.uuid={}",
            invoker.uuid,
            statusStopSpell,
            receiver.turnsStopSpell,
            receiver.turnsStopSpellMaximum,
            receiver.uuid
        )
        return statusStopSpell
    }

    override fun checkReceiver(receiver: B): Boolean {
        val statusStopSpell = receiver.statusStopSpell
        logger.debug(
            "receiver.statusStopSpell={} receiver.turnsStopSpell={} receiver.turnsStopSpellMaximum={} receiver.uuid={} uuid={}",
            statusStopSpell,
            receiver.turnsStopSpell,
            receiver.turnsStopSpellMaximum,
            receiver.uuid,
            uuid,
        )
        return !statusStopSpell
    }

    override fun getRequirement(invoker: A): Int {
        val stopSpellRequirement = invoker.stopSpellRequirement
        logger.debug(
            "invoker.stopSpellRequirement={} invoker.stopSpellRequirementMaximum={} invoker.stopSpellRequirementMinimum={} invoker.uuid={} uuid={}",
            stopSpellRequirement,
            invoker.stopSpellRequirementMaximum,
            invoker.stopSpellRequirementMinimum,
            invoker.uuid,
            uuid
        )
        return stopSpellRequirement
    }

    override fun getResistance(receiver: B): Int {
        val stopSpellResistance = receiver.stopSpellResistance
        logger.debug(
            "receiver.stopSpellResistance={} receiver.stopSpellResistanceMaximum={} receiver.stopSpellResistanceMinimum={} receiver.uuid={} uuid={}",
            stopSpellResistance,
            receiver.stopSpellResistanceMaximum,
            receiver.stopSpellResistanceMinimum,
            receiver.uuid,
            uuid
        )
        return stopSpellResistance
    }
}