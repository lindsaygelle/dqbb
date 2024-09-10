package dqbb

class StopSpell<A : StopSpellInvoker, B : StopSpellReceiver>(
    magicCost: Int,
) : RequirementMagic<A, B>(
    magicCost = magicCost
) {
    override fun applyEffect(invoker: A, receiver: B): Boolean {
        receiver.turnsStopSpell = if (!checkReceiverArmor(receiver)) 1 else 0
        val checkValue: Boolean = receiver.turnsStopSpell > 0
        logger.info(
            "checkValue={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} receiver.turnsStopSpell={} simpleName={}",
            checkValue,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            receiver.turnsStopSpell,
            simpleName
        )
        return checkValue
    }

    override fun checkReceiver(receiver: B): Boolean {
        val checkValue: Boolean = checkReceiverHitPoints(receiver) && checkReceiverTurnsStopSpell(receiver)
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

    private fun checkReceiverArmor(receiver: B): Boolean {
        val checkValue: Boolean = receiver.armor?.blocksStopSpell ?: false
        logger.info(
            "checkValue={} id={} receiver.armor.blocksStopSpell={} receiver.armor.id={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            receiver.armor?.blocksStopSpell,
            receiver.armor?.id,
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

    private fun checkReceiverTurnsStopSpell(receiver: B): Boolean {
        val checkValue: Boolean = receiver.turnsStopSpell == 0
        logger.info(
            "checkValue={} id={} receiver.id={} receiver.simpleName={} receiver.turnsStopSpell={} simpleName={}",
            checkValue,
            id,
            receiver.id,
            receiver.simpleName,
            receiver.turnsStopSpell,
            simpleName
        )
        return checkValue
    }

    override fun getInvokerRequirement(invoker: A): Int {
        val stopSpellRequirement: Int = invoker.stopSpellRequirement
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
        val stopSpellResistance: Int = receiver.stopSpellResistance
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