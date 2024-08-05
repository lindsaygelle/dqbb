package dqbb

class MagicSleep(
    conditionType: ConditionType,
    orderType: OrderType,
) : Magic(
    conditionType = conditionType,
    orderType = orderType,
) {

    override val actionType: ActionType = ActionType.SLEEP

    override val magicPoints: Int = 2

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Other Actor */
        val armor = otherActor.armor
        val turnSleep = otherActor.turnsSleep
        logger.debug(
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
                    "${actor.name}(${actor.id}) SLEEPS ${otherActor.name}(${otherActor.id})"
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
        val statusResistance = otherActor.statusResistance
        val sleepResistanceScale = 0xF
        val sleepResistanceShift = 28
        val sleepResistance =
            (statusResistance shr sleepResistanceShift) and sleepResistanceScale // First nibble
        logger.debug(
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
