package dqbb

class MagicHealMore(
    conditionType: ConditionType,
    orderType: OrderType? = null,
) : MagicHeal(
    conditionType = conditionType,
    orderType = orderType,
) {

    override val magicPoints: Int = 10

    override val name: String = "${super.name} MORE"

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val healRangeMaximum = actor.healRangeMaximum
        val healRangeMinimum = actor.healRangeMinimum
        val healRange = (healRangeMinimum..healRangeMaximum)
        val healRangeValue = healRange.random()
        val healMoreScale = actor.healMoreScale
        val healMoreShift = actor.healMoreShift
        val healMoreValue = (healRangeValue and healMoreShift) + healMoreScale
        /* Other Actor */
        val hitPoints = otherActor.hitPoints
        otherActor.hitPoints += healMoreValue
        println(//logger.debug(
            "$this: " +
                    "actor.healMoreScale=$healMoreScale " +
                    "actor.healMoreShift=$healMoreShift " +
                    "actor.healMoreValue=$healMoreValue " +
                    "actor.healRangeMaximum=$healRangeMaximum " +
                    "actor.healRangeMinimum=$healRangeMinimum " +
                    "actor.healRangeValue=$healRangeValue " +
                    "actor.id=${actor.id} " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.hitPointsPrevious=$hitPoints " +
                    "otherActor.id=${otherActor.id}"
        )
        actor.trail.add(
            Trail(
                "$actor HEALED $otherActor for $healMoreValue HIT POINTS"
            )
        )
        return true
    }
}
