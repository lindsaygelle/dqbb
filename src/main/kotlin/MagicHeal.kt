package dqbb


class MagicHeal(
    condition: ConditionType,
) : Magic(
    condition = condition,
    magicPoints = 2,
) {

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val healRangeMaximum = actor.healRangeMaximum
        val healRangeMinimum = actor.healRangeMinimum
        val healRangeRandom = actor.healRangeRandom
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
