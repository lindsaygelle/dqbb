package dqbb

class MagicStopSpell(
    conditionType: ConditionType,
) : Magic(
    conditionType,
) {

    override val magicPoints: Int = 2

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val armor = otherActor.armor
        val turnsStopSpell = otherActor.turnsStopSpell
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.armor=$armor " +
                    "otherActor.id=$this " +
                    "otherActor.turnsStopSpell=$turnsStopSpell"
        )
        if ((armor != ArmorErdrick) && !otherActor.statusStopSpell) {
            otherActor.turnsStopSpell = 1
        }
        return actor.turnsStopSpell == 1
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val stopSpellRequirementMaximum = actor.stopSpellRequirementMaximum
        val stopSpellRequirementMinimum = actor.stopSpellRequirementMinimum
        val stopSpellRequirement = (stopSpellRequirementMinimum..stopSpellRequirementMaximum).random()
        /* Other Actor */
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
