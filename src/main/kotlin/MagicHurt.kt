package dqbb

open class MagicHurt(
    conditionType: ConditionType,
    orderType: OrderType? = null
) : Magic(
    conditionType = conditionType,
    orderType = orderType,
) {

    override val magicPoints: Int = 2

    override val name: String = "HURT"

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val hurtRangeMaximum = actor.hurtRangeMaximum
        val hurtRangeMinimum = actor.hurtRangeMinimum
        val hurtRange = (hurtRangeMinimum..hurtRangeMaximum)
        val hurtRangeValue = hurtRange.random()
        val hurtScale = actor.hurtScale
        val hurtShift = actor.hurtShift
        val hurtValue = (hurtRangeValue and hurtShift) + hurtScale
        /* Other Actor */
        val armor = actor.armor
        val hitPoints = otherActor.hitPoints
        val hurtReduction = this.getHurtReduction(armor)
        /* Done */
        val hurtValueReduced = hurtValue / hurtReduction
        otherActor.hitPoints -= hurtValueReduced
        println(//logger.debug(
            "$this: " +
                    "actor.hurtRangeMaximum=$hurtRangeMaximum " +
                    "actor.hurtRangeMinimum=$hurtRangeMinimum " +
                    "actor.hurtRangeValue=$hurtRangeValue " +
                    "actor.hurtScale=$hurtScale " +
                    "actor.hurtShift=$hurtShift " +
                    "actor.hurtValue=$hurtValue " +
                    "actor.hurtValueReduced=$hurtValueReduced " +
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
                "$actor HURT $otherActor for $hurtReduction HIT POINTS"
            )
        )
        return true
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val hurtRequirementMaximum = actor.hurtRequirementMaximum
        val hurtRequirementMinimum = actor.hurtRequirementMinimum
        val hurtRequirementRange = (hurtRequirementMinimum..hurtRequirementMaximum)
        val hurtRequirement = hurtRequirementRange.random()
        /* Other Actor */
        val damageResistanceMaximum = otherActor.damageResistanceMaximum
        val hurtResistanceScale = 0xF
        val hurtResistanceShift = 28
        val hurtResistance =
            (damageResistanceMaximum shr hurtResistanceShift) and hurtResistanceScale // First nibble: TODO check
        println(//logger.debug(
            "$this: " +
                    "actor.hurtRequirementMaximum=$hurtRequirementMaximum " +
                    "actor.hurtRequirementMinimum=$hurtRequirementMinimum " +
                    "actor.hurtRequirement=$hurtRequirement " +
                    "actor.id=${actor.id} " +
                    "otherActor.damageResistanceMaximum=$damageResistanceMaximum " +
                    "otherActor.hurtResistance=$hurtResistance " +
                    "otherActor.id=${otherActor.id} " +
                    "hurtResistanceScale=$hurtResistanceScale " +
                    "hurtResistanceShift=$hurtResistanceShift"
        )
        return hurtRequirement < hurtResistance
    }

    protected fun getHurtReduction(armor: Armor?): Int {
        val reduction = when (armor) {
            ArmorErdrick,
            ArmorMagic -> 3

            else -> 1
        }
        println(//logger.debug(
            "$this: " +
                    "armor.id=${armor?.id}" +
                    "armor.name=${armor?.name} " +
                    "reduction=$reduction"
        )
        return reduction
    }
}
