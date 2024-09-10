package dqbb

class Sleep<A : SleepInvoker, B : SleepReceiver>(
    magicCost: Int,
) : RequirementMagic<A, B>(
    magicCost = magicCost
) {
    override fun applyEffect(invoker: A, receiver: B): Boolean {
        receiver.turnsSleep = if (!checkReceiverArmor(receiver)) 1 else 0
        val checkValue: Boolean = receiver.turnsSleep > 0
        logger.info(
            "checkValue={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} receiver.turnsSleep={}",
            checkValue,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            receiver.turnsSleep
        )
        return checkValue
    }

    override fun checkReceiver(receiver: B): Boolean {
        val checkValue: Boolean = checkReceiverHitPoints(receiver) && checkReceiverTurnsSleep(receiver)
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
        val checkValue: Boolean = receiver.armor?.blocksSleep ?: false
        logger.info(
            "checkValue={} id={} receiver.armor.blocksSleep={} receiver.armor.id={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            receiver.armor?.blocksSleep,
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

    private fun checkReceiverTurnsSleep(receiver: B): Boolean {
        val checkValue: Boolean = receiver.turnsSleep == 0
        logger.info(
            "checkValue={} id={} receiver.id={} receiver.simpleName={} receiver.turnsSleep={} simpleName={}",
            checkValue,
            id,
            receiver.id,
            receiver.simpleName,
            receiver.turnsSleep,
            simpleName
        )
        return checkValue
    }

    override fun getInvokerRequirement(invoker: A): Int {
        val sleepRequirement: Int = invoker.sleepRequirement
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} invoker.sleepRequirement={} invoker.sleepRequirementMaximum={} invoker.sleepRequirementMaximum={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            sleepRequirement,
            invoker.sleepRequirementMaximum,
            invoker.sleepRequirementMinimum,
            simpleName
        )
        return sleepRequirement
    }

    override fun getReceiverResistance(receiver: B): Int {
        val sleepResistance: Int = receiver.sleepResistance
        logger.info(
            "id={} receiver.id={} receiver.simpleName={} receiver.sleepResistance={} receiver.sleepResistanceMaximum={} receiver.sleepResistanceMinimum={} simpleName={}",
            id,
            sleepResistance,
            receiver.id,
            receiver.simpleName,
            receiver.sleepResistanceMaximum,
            receiver.sleepResistanceMinimum,
            simpleName
        )
        return sleepResistance
    }
}