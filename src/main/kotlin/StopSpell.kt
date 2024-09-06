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
            "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} receiver.turnsStopSpell={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            receiver.turnsStopSpell,
            simpleName
        )
        return receiver.turnsStopSpell > 0
    }

    override fun checkReceiver(receiver: B): Boolean {
        logger.trace(
            "id={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkReceiverHitPoints(receiver) && checkReceiverTurnsStopSpell(receiver)
    }

    private fun checkReceiverArmor(receiver: B): Boolean {
        val blocksStopSpell = receiver.armor?.blocksStopSpell ?: false
        logger.info(
            "id={} receiver.armor.blocksStopSpell={} receiver.armor.id={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            blocksStopSpell,
            receiver.armor?.id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return blocksStopSpell
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

    private fun checkReceiverTurnsStopSpell(receiver: B): Boolean {
        logger.info(
            "id={} receiver.id={} receiver.simpleName={} receiver.turnsStopSpell={} simpleName={}",
            id,
            receiver.id,
            receiver.simpleName,
            receiver.turnsStopSpell,
            simpleName
        )
        return receiver.turnsStopSpell == 0
    }

    override fun getInvokerRequirement(invoker: A): Int {
        val stopSpellRequirement = invoker.stopSpellRequirement
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} invoker.stopSpellRequirement={} invoker.stopSpellRequirementMaximum={} invoker.stopSpellRequirementMaximum={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            invoker.stopSpellRequirement,
            invoker.stopSpellRequirementMaximum,
            invoker.stopSpellRequirementMinimum,
            simpleName
        )
        return stopSpellRequirement
    }

    override fun getReceiverResistance(receiver: B): Int {
        val stopSpellResistance = receiver.stopSpellResistance
        logger.info(
            "id={} receiver.id={} receiver.simpleName={} receiver.stopSpellResistance={} receiver.stopSpellResistanceMaximum={} receiver.stopSpellResistanceMinimum={} simpleName={}",
            id,
            stopSpellResistance,
            receiver.id,
            receiver.simpleName,
            receiver.stopSpellResistanceMaximum,
            receiver.stopSpellResistanceMinimum,
            simpleName
        )
        return stopSpellResistance
    }
}