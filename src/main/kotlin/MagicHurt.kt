package dqbb

class MagicHurt(
    condition: ConditionType
) : Magic(
    condition = condition,
    magicPoints = 5,
) {
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
        val hurtRequirement = actor.hurtRequirement
        val hurtResistance = otherActor.hurtResistance
        println(//logger.debug
            "$this: " +
                    "actor.hurtRequirementMaximum=${actor.hurtRequirementMaximum} " +
                    "actor.hurtRequirementMinimum=${actor.hurtRequirementMinimum} " +
                    "actor.hurtRequirement=$hurtRequirement " +
                    "actor.id=$actor " + "otherActor.hurtResistance=$hurtResistance " +
                    "otherActor.id=$otherActor"

        )
        return hurtRequirement < hurtResistance
    }
}
