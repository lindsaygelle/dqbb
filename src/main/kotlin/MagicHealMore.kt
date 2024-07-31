package dqbb

class MagicHealMore(
<<<<<<< HEAD
    conditionType: ConditionType,
) : MagicHeal(
    conditionType = conditionType,
=======
    condition: ConditionType,
) : MagicHeal(
    condition = condition,
>>>>>>> 62c97b8d57ae4c9ba761fad9cf044e10d3b47127
) {
    override val magicPoints: Int = 10

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val healMoreScale = actor.healMoreScale
        val healMoreShift = actor.healMoreShift
        val healRangeMaximum = actor.healRangeMaximum
        val healRangeMinimum = actor.healRangeMinimum
        val healRangeRandom = (healRangeMinimum..healRangeMaximum).random()
        val hitPoints = otherActor.hitPoints
        val healMoreValue = (healRangeRandom and healMoreShift) + healMoreScale
        logger.debug(
            "$this: " +
                    "actor.healMoreScale=$healMoreScale " +
                    "actor.healMoreShift=$healMoreShift " +
                    "actor.healMoreValue=$healMoreValue " +
                    "actor.healRangeMaximum=$healRangeMaximum " +
                    "actor.healRangeMinimum=$healRangeMinimum " +
                    "actor.healRangeRandom=$healRangeRandom " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=$hitPoints " +
                    "otherActor.id=$otherActor"
        )
        otherActor.hitPoints += healMoreValue
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.id=$otherActor"
        )
        return otherActor.hitPoints > hitPoints
    }
}
