package dqbb

class SpellHurtMore<A : InvokerHurtMore, B : ReceiverHurt>(magicCost: Int) : Magic<A, B>(magicCost = magicCost) {

    override fun applyEffect(invoker: A, receiver: B) {
        val hurtMoreRangeMaximum = invoker.hurtMoreRangeMaximum
        val hurtMoreRangeMinimum = invoker.hurtMoreRangeMinimum
        val hurtMoreRange = (hurtMoreRangeMinimum..hurtMoreRangeMaximum)
        val hurtMoreRangeValue = hurtMoreRange.random()
        val hurtMoreScale = invoker.hurtMoreScale
        val hurtMoreShift = invoker.hurtMoreShift
        val hurtMoreValue = ((hurtMoreRangeValue and hurtMoreShift) + hurtMoreScale)
        receiver.hitPoints -= hurtMoreValue
        logger.debug(
            "invoker.hashCode=${invoker.hashCode()} " +
            "invoker.hurtMoreRangeMaximum=$hurtMoreRangeMaximum " +
            "invoker.hurtMoreRangeMinimum=$hurtMoreRangeMinimum " +
            "invoker.hurtMoreRangeValue=$hurtMoreRangeValue " +
            "invoker.hurtMoreScale=$hurtMoreScale " +
            "invoker.hurtMoreShift=$hurtMoreShift " +
            "invoker.hurtMoreValue=$hurtMoreValue " +
            "receiver.hashCode=${receiver.hashCode()} " +
            "receiver.hitPoints=${receiver.hitPoints}"
        )
    }

    override fun checkResistance(invoker: A, receiver: B): Boolean {
        val damageResistance = receiver.damageResistance
        val hurtMoreRequirementMaximum = invoker.hurtMoreRequirementMaximum
        val hurtMoreRequirementMinimum = invoker.hurtMoreRequirementMinimum
        val hurtMoreRequirementRange = (hurtMoreRequirementMinimum..hurtMoreRequirementMaximum)
        val hurtMoreRequirement = hurtMoreRequirementRange.random()
        val hurtMoreResistance = (damageResistance shr 0x0F) and 28
        logger.debug(
            "invoker.hashCode=${invoker.hashCode()} " +
            "invoker.hurtMoreRequirementMaximum=$hurtMoreRequirementMaximum " +
            "invoker.hurtMoreRequirementMinimum=$hurtMoreRequirementMinimum " +
            "invoker.hurtMoreRequirement=$hurtMoreRequirement " +
            "receiver.damageResistance=$damageResistance " +
            "receiver.hashCode=${receiver.hashCode()} " +
            "receiver.hurtMoreResistance=$hurtMoreResistance"
        )
        return hurtMoreResistance > hurtMoreRequirement
    }
}