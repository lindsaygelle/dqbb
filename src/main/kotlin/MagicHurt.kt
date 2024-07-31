package dqbb

open class MagicHurt(
<<<<<<< HEAD
    conditionType: ConditionType
) : Magic(
    conditionType = conditionType,
) {
    override val magicPoints: Int = 2
=======
    condition: ConditionType
) : Magic(
    condition = condition,
) {
    override val magicPoints: Int = 5
>>>>>>> 62c97b8d57ae4c9ba761fad9cf044e10d3b47127

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val hurtRangeMaximum = actor.hurtRangeMaximum
        val hurtRangeMinimum = actor.hurtRangeMinimum
        val hurtRangeRandom = (hurtRangeMinimum..hurtRangeMaximum).random()
        val hurtScale = actor.hurtScale
        val hurtShift = actor.hurtShift
        val hurtValue = (hurtRangeRandom and hurtShift) + hurtScale
        val hitPoints = otherActor.hitPoints
        logger.debug(
            "$this: " +
                    "actor.hurtRangeMaximum=$hurtRangeMaximum " +
                    "actor.hurtRangeMinimum=$hurtRangeMinimum " +
                    "actor.hurtRangeRandom=$hurtRangeRandom " +
                    "actor.hurtScale=$hurtScale " +
                    "actor.hurtShift=$hurtShift " +
                    "actor.hurtValue=$hurtValue " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=$hitPoints " +
                    "otherActor.id=$otherActor"
        )
        // TODO: Armor reductions
        otherActor.hitPoints -= hurtValue
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.id=$otherActor"
        )
        return otherActor.hitPoints < hitPoints // This could just be true to indicate the action was performed.
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        val hurtRequirementMaximum = actor.hurtRequirementMaximum
        val hurtRequirementMinimum = actor.hurtRequirementMinimum
        val hurtRequirement = (hurtRequirementMinimum..hurtRequirementMaximum).random()
        val damageResistanceMaximum = otherActor.damageResistanceMaximum
<<<<<<< HEAD
        val hurtResistance = (damageResistanceMaximum shr 28) and 0xF // First nibble: TODO check
=======
        val hurtResistance = (damageResistanceMaximum shr 4) and 0xF
>>>>>>> 62c97b8d57ae4c9ba761fad9cf044e10d3b47127
        println(//logger.debug
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
