package dqbb

class Sleep<A : SleepInvoker, B : SleepReceiver>(
    magicCost: Int,
) : RequirementMagic<A, B>(
    magicCost = magicCost
) {
    override fun applyEffect(invoker: A, receiver: B): Boolean {
        if (!checkReceiverArmor(receiver)) {
            receiver.turnsSleep = 1
        }
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} receiver.turnsSleep={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            receiver.turnsSleep
        )
        return receiver.turnsSleep > 0
    }

    override fun checkReceiver(receiver: B): Boolean {
        logger.trace(
            "id={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkReceiverHitPoints(receiver) && checkReceiverTurnsSleep(receiver)
    }

    private fun checkReceiverArmor(receiver: B): Boolean {
        val blocksSleep = receiver.armor?.blocksSleep ?: false
        logger.info(
            "id={} receiver.armor.blocksSleep={} receiver.armor.id={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            blocksSleep,
            receiver.armor?.id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return blocksSleep
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

    private fun checkReceiverTurnsSleep(receiver: B): Boolean {
        logger.info(
            "id={} receiver.id={} receiver.simpleName={} receiver.turnsSleep={} simpleName={}",
            id,
            receiver.id,
            receiver.simpleName,
            receiver.turnsSleep,
            simpleName
        )
        return receiver.turnsSleep == 0
    }

    override fun getRequirement(invoker: A): Int {
        val sleepRequirement = invoker.sleepRequirement
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

    override fun getResistance(receiver: B): Int {
        val sleepResistance = receiver.sleepResistance
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