package dqbb

class SpellSleep<A : InvokerSleep, B : ReceiverSleep>(magicCost: Int) : Magic<A, B>(magicCost = magicCost) {

    override fun applyEffect(invoker: A, receiver: B) {
        val statusSleep = receiver.statusSleep
        logger.debug(
            "receiver.hashCode=${receiver.hashCode()} " +
            "receiver.turnsSleep=${receiver.turnsSleep} " +
            "receiver.statusSleep=$statusSleep"
        )
        if (!statusSleep) {
            receiver.turnsSleep = 1
        }
    }

    override fun checkResistance(invoker: A, receiver: B): Boolean {
        val statusResistance = receiver.statusResistance
        val sleepRequirementMaximum = invoker.sleepRequirementMaximum
        val sleepRequirementMinimum = invoker.sleepRequirementMinimum
        val sleepRequirementRange = (sleepRequirementMinimum..sleepRequirementMaximum)
        val sleepRequirement = sleepRequirementRange.random()
        val sleepResistance = (statusResistance shr 4) and 0x0F
        logger.debug(
            "invoker.hashCode=${invoker.hashCode()} " +
            "invoker.sleepRequirementMaximum=$sleepRequirementMaximum " +
            "invoker.sleepRequirementMinimum=$sleepRequirementMinimum " +
            "invoker.sleepRequirement=$sleepRequirement " +
            "receiver.hashCode=${receiver.hashCode()} " +
            "receiver.statusResistance=$statusResistance " +
            "receiver.sleepResistance=$sleepResistance"
        )
        return sleepResistance > sleepRequirement
    }
}