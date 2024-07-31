package dqbb

class MagicSleep(
    conditionType: ConditionType
) : Magic(
    conditionType = conditionType
) {

    override val magicPoints: Int = 2

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val turnSleep = otherActor.turnsSleep
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.id=$this " +
                    "otherActor.turnSleep=$turnSleep"
        )
        // TODO: Check armor
        if (!otherActor.statusSleep) {
            otherActor.turnsSleep = 1
        }
        return actor.statusSleep
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        val sleepRequirementMaximum = actor.sleepRequirementMaximum
        val sleepRequirementMinimum = actor.sleepRequirementMinimum
        val sleepRequirement = (sleepRequirementMinimum..sleepRequirementMaximum).random()
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
