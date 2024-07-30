package dqbb

class MagicStopSpell(
    conditionType: ConditionType,
) : Magic(
    conditionType,
) {
    override val magicPoints: Int = 2

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val turnsStopSpell = otherActor.turnsStopSpell
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.id=$this " +
                    "otherActor.turnsStopSpell=$turnsStopSpell"
        )
        // TODO: Check armor
        if (!otherActor.statusStopSpell) {
            otherActor.turnsStopSpell = 1
        }
        return actor.turnsStopSpell < turnsStopSpell
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        val stopSpellRequirementMaximum = actor.stopSpellRequirementMaximum
        val stopSpellRequirementMinimum = actor.stopSpellRequirementMinimum
        val stopSpellRequirement = (stopSpellRequirementMinimum..stopSpellRequirementMaximum).random()
        val statusResistanceMaximum = otherActor.statusResistanceMaximum
        val stopSpellResistance = (statusResistanceMaximum shr 4) and 0x0F // Second nibble
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "actor.stopSpellRequirementMaximum=$stopSpellRequirementMaximum " +
                    "actor.stopSpellRequirementMinimum=$stopSpellRequirementMinimum " +
                    "actor.stopSpellRequirement=$stopSpellRequirement " +
                    "otherActor.id=$this " +
                    "otherActor.stopSpellResistance=$stopSpellResistance"
        )
        return stopSpellRequirement > stopSpellResistance
    }
}