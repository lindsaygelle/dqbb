package dqbb

class MagicStopSpell(
    conditionType: ConditionType,
    orderType: OrderType? = null,
) : Magic(
    conditionType,
    orderType = orderType,
) {

    override val actionType: ActionType = ActionType.STOP_SPELL

    override val magicPoints: Int = 2

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val armor = otherActor.armor
        val turnsStopSpell = otherActor.turnsStopSpell
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "otherActor.armor.id=${armor?.id} " +
                    "otherActor.armor.name=${armor?.name} " +
                    "otherActor.id=$this " +
                    "otherActor.turnsStopSpell=$turnsStopSpell"
        )
        if ((armor != ArmorErdrick) && !otherActor.statusStopSpell) {
            actor.trail.add(
                Trail(
                    "$actor STOPS $otherActor SPELLS"
                )
            )
            otherActor.turnsStopSpell = 1
        }
        return true
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val stopSpellRequirementMaximum = actor.stopSpellRequirementMaximum
        val stopSpellRequirementMinimum = actor.stopSpellRequirementMinimum
        val stopSpellRequirement = (stopSpellRequirementMinimum..stopSpellRequirementMaximum).random()
        /* Other Actor */
        val statusResistance = otherActor.statusResistance
        val stopSpellResistanceScale = 0x0F
        val stopSpellResistanceShift = 4
        val stopSpellResistance =
            (statusResistance shr stopSpellResistanceShift) and stopSpellResistanceScale // Second nibble
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "actor.stopSpellRequirementMaximum=$stopSpellRequirementMaximum " +
                    "actor.stopSpellRequirementMinimum=$stopSpellRequirementMinimum " +
                    "actor.stopSpellRequirement=$stopSpellRequirement " +
                    "otherActor.id=${otherActor.id} " +
                    "otherActor.stopSpellResistance=$stopSpellResistance " +
                    "stopSpellResistanceScale=$stopSpellResistanceScale " +
                    "stopSpellResistanceShift=$stopSpellResistanceShift"
        )
        return stopSpellRequirement > stopSpellResistance
    }
}
