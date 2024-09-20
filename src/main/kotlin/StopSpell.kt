package dqbb

class StopSpell<A : StopSpellInvoker, B : StopSpellReceiver>(
    magicCost: Int,
) : AbilityMagicRequirement<A, B>(
    magicCost = magicCost
) {
    override fun applyUpdate(invoker: A, receiver: B): Reviewable {
        if (!checkReceiverArmor(receiver)) {
            receiver.turnsStopSpell = 1
        }
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} receiver.turnsStopSpell={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            receiver.turnsStopSpell
        )
        return getReviewable(invoker, true, receiver, true)
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
            stopSpellRequirement,
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

    private fun getReviewable(
        invoker: A,
        invokerIsValid: Boolean,
        receiver: B,
        receiverIsValid: Boolean,
    ): Reviewable {
        return ReviewStopSpell(
            id,
            simpleName,
            invoker.id,
            invokerIsValid,
            invoker.name,
            invoker.simpleName,
            magicCost,
            receiver.armor?.blocksStopSpell,
            receiver.armor?.id,
            receiver.armor?.name,
            receiver.armor?.simpleName,
            receiver.id,
            receiverIsValid,
            receiver.name,
            receiver.simpleName,
            receiver.turnsStopSpell,
        )
    }

    override fun getReviewableInvokerInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, false, receiver, false)
    }

    override fun getReviewableReceiverInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, true, receiver, false)
    }

    override fun getReviewableRequirementInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, true, receiver, true)
    }
}