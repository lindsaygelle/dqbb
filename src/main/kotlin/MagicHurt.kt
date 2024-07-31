package dqbb

open class MagicHurt(
    conditionType: ConditionType
) : Magic(
    conditionType = conditionType,
) {

    override val magicPoints: Int = 2

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val hurtRangeMaximum = actor.hurtRangeMaximum
        val hurtRangeMinimum = actor.hurtRangeMinimum
        val hurtRangeRandom = (hurtRangeMinimum..hurtRangeMaximum).random()
        val hurtScale = actor.hurtScale
        val hurtShift = actor.hurtShift
        val hurtValue = (hurtRangeRandom and hurtShift) + hurtScale
        /* Other Actor */
        val armor = actor.armor
        val hitPoints = otherActor.hitPoints
        val hurtReduction = when (armor) {
            ArmorErdrick,
            ArmorMagic -> 3

            else -> 1
        }
        logger.debug(
            "$this: " +
                    "actor.hurtRangeMaximum=$hurtRangeMaximum " +
                    "actor.hurtRangeMinimum=$hurtRangeMinimum " +
                    "actor.hurtRangeRandom=$hurtRangeRandom " +
                    "actor.hurtScale=$hurtScale " +
                    "actor.hurtShift=$hurtShift " +
                    "actor.hurtValue=$hurtValue " +
                    "actor.id=$actor " +
                    "otherActor.armor=$armor " +
                    "otherActor.hitPoints=$hitPoints " +
                    "otherActor.hurtReduction=$hurtReduction " +
                    "otherActor.id=$otherActor"
        )
        otherActor.hitPoints -= hurtValue / hurtReduction
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.id=$otherActor"
        )
        return otherActor.hitPoints < hitPoints // This could just be true to indicate the action was performed.
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val hurtRequirementMaximum = actor.hurtRequirementMaximum
        val hurtRequirementMinimum = actor.hurtRequirementMinimum
        val hurtRequirement = (hurtRequirementMinimum..hurtRequirementMaximum).random()
        /* Other Actor */
        val damageResistanceMaximum = otherActor.damageResistanceMaximum
        val hurtResistance = (damageResistanceMaximum shr 28) and 0xF // First nibble: TODO check
        logger.debug(
            "$this: " +
                    "actor.hurtRequirementMaximum=$hurtRequirementMaximum " +
                    "actor.hurtRequirementMinimum=$hurtRequirementMinimum " +
                    "actor.hurtRequirement=$hurtRequirement " +
                    "actor.id=$actor " +
                    "otherActor.hurtResistance=$hurtResistance " +
                    "otherActor.damageResistanceMaximum=$damageResistanceMaximum " +
                    "otherActor.id=$otherActor"
        )
        return hurtRequirement < hurtResistance
    }
}
