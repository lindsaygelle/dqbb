package dqbb

class InvokeSleep<A : SleepInvoker, B : SleepReceiver>(
    magicCost: Int,
) : RequirementMagic<A, B>(
    magicCost = magicCost
) {
    override fun applyEffect(invoker: A, receiver: B): Boolean {
        if (receiver.armor?.blocksSleep == true) {
            return false
        }
        receiver.turnsSleep = 1
        val statusSleep = receiver.statusSleep
        logger.info(
            "invoker.uuid={} receiver.statusSleep={} receiver.turnsSleep={} receiver.turnsSleepMaximum={} receiver.uuid={}",
            invoker.uuid,
            statusSleep,
            receiver.turnsSleep,
            receiver.turnsSleepMaximum,
            receiver.uuid
        )
        return statusSleep
    }

    override fun checkReceiver(receiver: B): Boolean {
        val statusSleep = receiver.statusSleep
        logger.debug(
            "receiver.statusSleep={} receiver.turnsSleep={} receiver.turnsSleepMaximum={} receiver.uuid={} uuid={}",
            statusSleep,
            receiver.turnsSleep,
            receiver.turnsSleepMaximum,
            receiver.uuid,
            uuid,
        )
        return !statusSleep
    }

    override fun getRequirement(invoker: A): Int {
        val sleepRequirement = invoker.sleepRequirement
        logger.debug(
            "invoker.sleepRequirement={} invoker.sleepRequirementMaximum={} invoker.sleepRequirementMinimum={} invoker.uuid={} uuid={}",
            sleepRequirement,
            invoker.sleepRequirementMaximum,
            invoker.sleepRequirementMinimum,
            invoker.uuid,
            uuid
        )
        return sleepRequirement
    }

    override fun getResistance(receiver: B): Int {
        val sleepResistance = receiver.sleepResistance
        logger.debug(
            "receiver.sleepResistance={} receiver.sleepResistanceMaximum={} receiver.sleepResistanceMinimum={} receiver.uuid={} uuid={}",
            sleepResistance,
            receiver.sleepResistanceMaximum,
            receiver.sleepResistanceMinimum,
            receiver.uuid,
            uuid
        )
        return sleepResistance
    }
}