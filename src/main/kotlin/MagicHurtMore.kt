package dqbb

class MagicHurtMore(
    conditionType: ConditionType
) : MagicHurt(
    conditionType = conditionType,
) {

    override val magicPoints: Int = 5

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val hurtRangeMaximum = actor.hurtRangeMaximum
        val hurtRangeMinimum = actor.hurtRangeMinimum
        val hurtRangeRandom = (hurtRangeMinimum..hurtRangeMaximum).random()
        val hurtMoreScale = actor.hurtMoreScale
        val hurtMoreShift = actor.hurtMoreShift
        val hurtMoreValue = (hurtRangeRandom and hurtMoreShift) + hurtMoreScale
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
                    "actor.hurtMoreScale=$hurtMoreScale " +
                    "actor.hurtMoreShift=$hurtMoreShift " +
                    "actor.hurtMoreValue=$hurtMoreValue " +
                    "actor.hurtRangeMaximum=$hurtRangeMaximum " +
                    "actor.hurtRangeMinimum=$hurtRangeMinimum " +
                    "actor.hurtRangeRandom=$hurtRangeRandom " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=$hitPoints " +
                    "otherActor.hurtReduction=$hurtReduction " +
                    "otherActor.id=$otherActor"
        )
        otherActor.hitPoints -= hurtMoreValue / hurtReduction
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.id=$otherActor"
        )
        return otherActor.hitPoints < hitPoints // This could just be true to indicate the action was performed.
    }
}
