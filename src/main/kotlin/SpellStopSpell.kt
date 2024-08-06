package dqbb

class SpellStopSpell<A : InvokerStopSpell, B : ReceiverStopSpell>(magicCost: Int) : Magic<A, B>(magicCost = magicCost) {

    override fun applyEffect(invoker: A, receiver: B) {
        val statusStopSpell = receiver.statusStopSpell
        logger.debug(
            "receiver.hashCode=${receiver.hashCode()} " +
            "receiver.turnsStopSpell=${receiver.turnsStopSpell} " +
            "receiver.statusStopSpell=$statusStopSpell"
        )
        if (!statusStopSpell) {
            receiver.turnsStopSpell = 1
        }
    }

    override fun checkResistance(invoker: A, receiver: B): Boolean {
        val statusResistance = receiver.statusResistance
        val stopSpellRequirementMaximum = invoker.stopSpellRequirementMaximum
        val stopSpellRequirementMinimum = invoker.stopSpellRequirementMinimum
        val stopSpellRequirementRange = (stopSpellRequirementMinimum..stopSpellRequirementMaximum)
        val stopSpellRequirement = stopSpellRequirementRange.random()
        val stopSpellResistance = (statusResistance and 0x0F)
        logger.debug(
            "invoker.hashCode=${invoker.hashCode()} " +
            "invoker.stopSpellRequirementMaximum=$stopSpellRequirementMaximum " +
            "invoker.stopSpellRequirementMinimum=$stopSpellRequirementMinimum " +
            "invoker.stopSpellRequirement=$stopSpellRequirement " +
            "receiver.hashCode=${receiver.hashCode()} " +
            "receiver.statusResistance=$statusResistance " +
            "receiver.stopSpellResistance=$stopSpellResistance"
        )
        return stopSpellResistance > stopSpellRequirement
    }
}