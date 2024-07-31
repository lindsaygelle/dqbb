package dqbb

class MagicSleep(
    conditionType: ConditionType
) : Magic(
    conditionType = conditionType
) {

    override val magicPoints: Int = 2

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Other Actor */
        val armor = otherActor.armor
        val turnSleep = otherActor.turnsSleep
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.armor=$armor " +
                    "otherActor.id=$this " +
                    "otherActor.turnSleep=$turnSleep"
        )
        if ((armor != ArmorErdrick) && !otherActor.statusSleep) {
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
        val sleepResistance = (statusResistanceMaximum shr 28) and 0xF // First nibble
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "actor.sleepRequirementMaximum=$sleepRequirementMaximum " +
                    "actor.sleepRequirementMinimum=$sleepRequirementMinimum " +
                    "actor.sleepRequirement=$sleepRequirement " +
                    "otherActor.id=$this " +
                    "otherActor.sleepResistance=$sleepResistance"
        )
        return sleepRequirement > sleepResistance
    }
}
