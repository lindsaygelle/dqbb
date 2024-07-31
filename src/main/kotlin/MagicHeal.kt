package dqbb


open class MagicHeal(
<<<<<<< HEAD
    conditionType: ConditionType,
) : Magic(
    conditionType = conditionType,
) {

    override val magicPoints: Int = 4
=======
    condition: ConditionType,
) : Magic(
    condition = condition,
) {

    override val magicPoints: Int = 2
>>>>>>> 62c97b8d57ae4c9ba761fad9cf044e10d3b47127

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val healRangeMaximum = actor.healRangeMaximum
        val healRangeMinimum = actor.healRangeMinimum
        val healRangeRandom = (healRangeMinimum..healRangeMaximum).random()
        val healScale = actor.healScale
        val healShift = actor.healShift
        val healValue = (healRangeRandom and healShift) + healScale

        val hitPoints = otherActor.hitPoints
        logger.debug(
            "$this: " +
                    "actor.healRangeMaximum=$healRangeMaximum " +
                    "actor.healRangeMinimum=$healRangeMinimum " +
                    "actor.healRangeRandom=$healRangeRandom " +
                    "actor.healScale=$healScale " +
                    "actor.healShift=$healShift " +
                    "actor.healValue=$healValue " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=$hitPoints " +
                    "otherActor.id=$otherActor"
        )
        otherActor.hitPoints += healValue
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.id=$otherActor"
        )
        return otherActor.hitPoints > hitPoints
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        return otherActor.isAlive
    }
}
