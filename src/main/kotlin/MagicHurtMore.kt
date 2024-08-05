package dqbb

class MagicHurtMore(
    conditionType: ConditionType,
    orderType: OrderType,
) : MagicHurt(
    conditionType = conditionType,
    orderType = orderType,
) {

    override val actionType: ActionType = ActionType.HURT_MORE

    override val magicPoints: Int = 5

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val hurtRangeMaximum = actor.hurtRangeMaximum
        val hurtRangeMinimum = actor.hurtRangeMinimum
        val hurtRange = (hurtRangeMinimum..hurtRangeMaximum)
        val hurtRangeValue = hurtRange.random()
        val hurtMoreScale = actor.hurtMoreScale
        val hurtMoreShift = actor.hurtMoreShift
        val hurtMoreValue = (hurtRangeValue and hurtMoreShift) + hurtMoreScale
        /* Other Actor */
        val armor = actor.armor
        val hitPoints = otherActor.hitPoints
        val hurtReduction = this.getHurtReduction(armor)
        /* Done */
        val hurtMoreValueReduced = hurtMoreValue / hurtReduction
        otherActor.hitPoints -= hurtMoreValueReduced
        logger.debug(
            "$this: " +
                    "actor.hurtMoreScale=$hurtMoreScale " +
                    "actor.hurtMoreShift=$hurtMoreShift " +
                    "actor.hurtMoreValue=$hurtMoreValue " +
                    "actor.hurtMoreValueReduced=$hurtMoreValueReduced " +
                    "actor.hurtRangeMaximum=$hurtRangeMaximum " +
                    "actor.hurtRangeMinimum=$hurtRangeMinimum " +
                    "actor.hurtRangeValue=$hurtRangeValue " +
                    "actor.id=${actor.id} " +
                    "otherActor.armor.id=${armor?.id} " +
                    "otherActor.armor.name=${armor?.name} " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.hitPointsPrevious=$hitPoints " +
                    "otherActor.hurtReduction=$hurtReduction " +
                    "otherActor.id=${otherActor.id}"
        )
        actor.trail.add(
            Trail(
                "${actor.name}(${actor.id}) HURT ${otherActor.name}(${otherActor.id}) for $hurtReduction HIT POINTS"
            )
        )
        return true
    }
}
