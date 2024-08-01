package dqbb

class MagicSleep(
    conditionType: ConditionType,
    orderType: OrderType? = null,
) : Magic(
    conditionType = conditionType,
    orderType = orderType,
) {

    override val magicPoints: Int = 2

    override val name: String = "SLEEP"

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Other Actor */
        val armor = otherActor.armor
        val turnSleep = otherActor.turnsSleep
        println(//logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "otherActor.armor.id=${armor?.id} " +
                    "otherActor.armor.name=${armor?.name} " +
                    "otherActor.id=$this " +
                    "otherActor.turnSleep=$turnSleep"
        )
        if ((armor != ArmorErdrick) && !otherActor.statusSleep) {
            actor.trail.add(
                Trail(
                    "$actor SLEEPS $otherActor"
                )
            )
            otherActor.turnsSleep = 1
        }
        return true
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val sleepRequirementMaximum = actor.sleepRequirementMaximum
        val sleepRequirementMinimum = actor.sleepRequirementMinimum
        val sleepRequirement = (sleepRequirementMinimum..sleepRequirementMaximum).random()
        /* Other Actor */
        val statusResistanceMaximum = otherActor.statusResistanceMaximum
        val sleepResistanceScale = 0xF
        val sleepResistanceShift = 28
        val sleepResistance =
            (statusResistanceMaximum shr sleepResistanceShift) and sleepResistanceScale // First nibble
        println(//logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "actor.sleepRequirementMaximum=$sleepRequirementMaximum " +
                    "actor.sleepRequirementMinimum=$sleepRequirementMinimum " +
                    "actor.sleepRequirement=$sleepRequirement " +
                    "otherActor.id=$this " +
                    "otherActor.sleepResistance=$sleepResistance " +
                    "sleepResistanceScale=$sleepResistanceScale " +
                    "sleepResistanceShift=$sleepResistanceShift"
        )
        return sleepRequirement > sleepResistance
    }
}
