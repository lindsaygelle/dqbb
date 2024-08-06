package dqbb

class SpellHurt<A : InvokerHurt, B : ReceiverHurt>(magicCost: Int) : Magic<A, B>(magicCost = magicCost) {

    override fun applyEffect(invoker: A, receiver: B) {
        val hurtRangeMaximum = invoker.hurtRangeMaximum
        val hurtRangeMinimum = invoker.hurtRangeMinimum
        val hurtRange = (hurtRangeMinimum..hurtRangeMaximum)
        val hurtRangeValue = hurtRange.random()
        val hurtScale = invoker.hurtScale
        val hurtShift = invoker.hurtShift
        val hurtValue = ((hurtRangeValue and hurtShift) + hurtScale)
        receiver.hitPoints -= hurtValue
        logger.debug(
            "invoker.hashCode=${invoker.hashCode()} " +
            "invoker.hurtRangeMaximum=$hurtRangeMaximum " +
            "invoker.hurtRangeMinimum=$hurtRangeMinimum " +
            "invoker.hurtRangeValue=$hurtRangeValue " +
            "invoker.hurtScale=$hurtScale " +
            "invoker.hurtShift=$hurtShift " +
            "invoker.hurtValue=$hurtValue " +
            "receiver.hashCode=${receiver.hashCode()} " +
            "receiver.hitPoints=${receiver.hitPoints}"
        )
    }

    override fun checkResistance(invoker: A, receiver: B): Boolean {
        val damageResistance = receiver.damageResistance
        val hurtRequirementMaximum = invoker.hurtRequirementMaximum
        val hurtRequirementMinimum = invoker.hurtRequirementMinimum
        val hurtRequirementRange = (hurtRequirementMinimum..hurtRequirementMaximum)
        val hurtRequirement = hurtRequirementRange.random()
        val hurtResistance = (damageResistance shr 0x0F) and 28
        logger.debug(
            "invoker.hashCode=${invoker.hashCode()} " +
            "invoker.hurtRequirementMaximum=$hurtRequirementMaximum " +
            "invoker.hurtRequirementMinimum=$hurtRequirementMinimum " +
            "invoker.hurtRequirement=$hurtRequirement " +
            "receiver.damageResistance=$damageResistance " +
            "receiver.hashCode=${receiver.hashCode()} " +
            "receiver.hurtResistance=$hurtResistance"
        )
        return hurtResistance > hurtRequirement
    }
}