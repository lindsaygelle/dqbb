package dqbb


open class MagicHeal(
    conditionType: ConditionType,
    orderType: OrderType,
) : Magic(
    conditionType = conditionType,
    orderType = orderType
) {

    override val actionType: ActionType = ActionType.HEAL

    override val magicPoints: Int = 4

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val healRangeMaximum = actor.healRangeMaximum
        val healRangeMinimum = actor.healRangeMinimum
        val healRange = (healRangeMinimum..healRangeMaximum)
        val healRangeValue = healRange.random()
        val healScale = actor.healScale
        val healShift = actor.healShift
        val healValue = (healRangeValue and healShift) + healScale
        /* Other Actor */
        val hitPoints = otherActor.hitPoints
        otherActor.hitPoints += healValue
        logger.debug(
            "$this: " +
                    "actor.healRangeMaximum=$healRangeMaximum " +
                    "actor.healRangeMinimum=$healRangeMinimum " +
                    "actor.healRangeValue=$healRangeValue " +
                    "actor.healScale=$healScale " +
                    "actor.healShift=$healShift " +
                    "actor.healValue=$healValue " +
                    "actor.id=${actor.id} " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.hitPointsPrevious=$hitPoints " +
                    "otherActor.id=${otherActor.id}"
        )
        actor.trail.add(
            Trail(
                "${actor.arn} HEALED $${otherActor.arn} for $healValue HIT POINTS"
            )
        )
        return true
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        return !otherActor.isAlive
    }
}
