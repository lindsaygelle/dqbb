package dqbb

class MagicHurtMore(
    condition: ConditionType
) : MagicHurt(
    condition = condition,
) {
    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val hurtRangeMaximum = actor.hurtRangeMaximum
        val hurtRangeMinimum = actor.hurtRangeMinimum
        val hurtRangeRandom = (hurtRangeMinimum..hurtRangeMaximum).random()
        val hurtMoreScale = actor.hurtMoreScale
        val hurtMoreShift = actor.hurtMoreShift
        val hurtMoreValue = (hurtRangeRandom and hurtMoreShift) + hurtMoreScale
        val hitPoints = otherActor.hitPoints
        logger.debug(
            "$this: " +
                    "actor.hurtMoreScale=$hurtMoreScale " +
                    "actor.hurtMoreShift=$hurtMoreShift " +
                    "actor.hurtMoreValue=$hurtMoreValue " +
                    "actor.hurtRangeMaximum=$hurtRangeMaximum " +
                    "actor.hurtRangeMinimum=$hurtRangeMinimum " +
                    "actor.hurtRangeRandom=$hurtRangeRandom " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=$hitPoints " +
                    "otherActor.id=$otherActor"
        )
        // TODO: Armor reductions
        otherActor.hitPoints -= hurtMoreValue
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.id=$otherActor"
        )
        return otherActor.hitPoints < hitPoints // This could just be true to indicate the action was performed.
    }
}
