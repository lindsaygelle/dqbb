package dqbb

class StopSpell<A : StopSpellInvoker, B : StopSpellReceiver>(
    magicCost: Int,
) : RequirementMagic<A, B>(
    magicCost = magicCost
) {
    override fun applyEffect(invoker: A, receiver: B): Boolean {
        if (!checkReceiverArmor(receiver)) {
            receiver.turnsStopSpell = 1
        }
        logger.info(
            "id={} invoker.id={} receiver.id={} receiver.turnsStopSpell={}",
            id,
            invoker.id,
            receiver.id,
            receiver.turnsStopSpell
        )
        return receiver.turnsStopSpell > 0
    }

    override fun checkReceiver(receiver: B): Boolean {
        return checkReceiverHitPoints(receiver) && checkReceiverTurnsStopSpell(receiver)
    }

    private fun checkReceiverArmor(receiver: B): Boolean {
        val blocksStopSpell = receiver.armor?.blocksStopSpell ?: false
        logger.debug(
            "id={} receiver.armor.blocksStopSpell={} receiver.armor.id={} receiver.id={}",
            id,
            blocksStopSpell,
            receiver.armor?.id,
            receiver.id,
        )
        return blocksStopSpell
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.hitPoints={} receiver.id={}", id, receiver.hitPoints, receiver.id
        )
        return receiver.hitPoints > 0
    }

    private fun checkReceiverTurnsStopSpell(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.id={} receiver.turnsStopSpell={}", id, receiver.id, receiver.turnsStopSpell
        )
        return receiver.turnsStopSpell == 0
    }

    override fun getRequirement(invoker: A): Int {
        val stopSpellRequirement = invoker.stopSpellRequirement
        logger.debug(
            "id={} invoker.id={} invoker.stopSpellRequirement={} invoker.stopSpellRequirementMaximum={} invoker.stopSpellRequirementMaximum={}",
            id,
            invoker.id,
            invoker.stopSpellRequirement,
            invoker.stopSpellRequirementMaximum,
            invoker.stopSpellRequirementMinimum
        )
        return stopSpellRequirement
    }

    override fun getResistance(receiver: B): Int {
        val stopSpellResistance = receiver.stopSpellResistance
        logger.debug(
            "id={} receiver.id={} receiver.stopSpellResistance={} receiver.stopSpellResistanceMaximum={} receiver.stopSpellResistanceMinimum={}",
            id,
            stopSpellResistance,
            receiver.id,
            receiver.stopSpellResistanceMaximum,
            receiver.stopSpellResistanceMinimum
        )
        return stopSpellResistance
    }
}