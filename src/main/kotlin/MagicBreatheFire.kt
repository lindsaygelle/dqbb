package dqbb

class MagicBreatheFire(
    conditionType: ConditionType,
    orderType: OrderType,
) : Magic(
    conditionType = conditionType,
    orderType = orderType,
) {

    override val actionType: ActionType = ActionType.BREATHE_FIRE

    override val magicPoints: Int = 10

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val breatheFireRangeMaximum = actor.breatheFireRangeMaximum
        val breatheFireRangeMinimum = actor.breatheFireRangeMinimum
        val breatheFireRange = (breatheFireRangeMinimum..breatheFireRangeMaximum)
        val breatheFireRangeValue = breatheFireRange.random()
        val breatheFireScale = actor.breatheFireScale
        val breatheFireShift = actor.breatheFireShift
        val breatheFireValue = (breatheFireRangeValue and breatheFireShift) + breatheFireScale
        /* Other Actor */
        val armor = actor.armor
        val hitPoints = otherActor.hitPoints
        val breatheFireReduction = this.getBreatheFireReduction(armor)
        /* Done */
        val breatheFireValueReduced = breatheFireValue / breatheFireReduction
        otherActor.hitPoints -= breatheFireValueReduced
        logger.debug(
            "$this: " +
                    "actor.breatheFireRangeMaximum=$breatheFireRangeMaximum " +
                    "actor.breatheFireRangeMinimum=$breatheFireRangeMinimum " +
                    "actor.breatheFireRangeValue=$breatheFireRangeValue " +
                    "actor.breatheFireScale=$breatheFireScale " +
                    "actor.breatheFireShift=$breatheFireShift " +
                    "actor.breatheFireValue=$breatheFireValue " +
                    "actor.breatheFireValueReduced=$breatheFireValueReduced " +
                    "actor.id=${actor.id} " +
                    "otherActor.armor.id=${armor?.id} " +
                    "otherActor.armor.name=${armor?.name} " +
                    "otherActor.breatheFireReduction=$breatheFireReduction " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.hitPointsPrevious=$hitPoints " +
                    "otherActor.id=${otherActor.id}"
        )
        actor.trail.add(
            Trail(
                "${actor.arn} DAMAGES ${otherActor.arn} for $breatheFireReduction HIT POINTS"
            )
        )
        return true
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        return false
    }

    private fun getBreatheFireReduction(armor: Armor?): Int {
        val reduction = when (armor) {
            ArmorErdrick -> 3

            else -> 1
        }
        logger.debug(
            "$this: " +
                    "armor.id=${armor?.id}" +
                    "armor.name=${armor?.name} " +
                    "reduction=$reduction"
        )
        return reduction
    }
}
