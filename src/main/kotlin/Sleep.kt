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
            "id={} invoker.id={} receiver.id={} receiver.turnsSleep={}",
            id,
            invoker.id,
            receiver.id,
            receiver.turnsSleep
        )
        return receiver.turnsSleep > 0
    }

    override fun checkReceiver(receiver: B): Boolean {
        return checkReceiverHitPoints(receiver) && checkReceiverTurnsSleep(receiver)
    }

    private fun checkReceiverArmor(receiver: B): Boolean {
        val blocksSleep = receiver.armor?.blocksSleep ?: false
        logger.debug(
            "id={} receiver.armor.blocksSleep={} receiver.armor.id={} receiver.id={}",
            id,
            blocksSleep,
            receiver.armor?.id,
            receiver.id,
        )
        return blocksSleep
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.hitPoints={} receiver.id={}", id, receiver.hitPoints, receiver.id
        )
        return receiver.hitPoints > 0
    }

    private fun checkReceiverTurnsSleep(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.id={} receiver.turnsSleep={}", id, receiver.id, receiver.turnsSleep
        )
        return receiver.turnsSleep == 0
    }

    override fun getRequirement(invoker: A): Int {
        val sleepRequirement = invoker.sleepRequirement
        logger.debug(
            "id={} invoker.id={} invoker.sleepRequirement={} invoker.sleepRequirementMaximum={} invoker.sleepRequirementMaximum={}",
            id,
            invoker.id,
            sleepRequirement,
            invoker.sleepRequirementMaximum,
            invoker.sleepRequirementMinimum
        )
        return sleepRequirement
    }

    override fun getResistance(receiver: B): Int {
        val sleepResistance = receiver.sleepResistance
        logger.debug(
            "id={} receiver.id={} receiver.sleepResistance={} receiver.sleepResistanceMaximum={} receiver.sleepResistanceMinimum={}",
            id,
            sleepResistance,
            receiver.id,
            receiver.sleepResistanceMaximum,
            receiver.sleepResistanceMinimum
        )
        return sleepResistance
    }
}