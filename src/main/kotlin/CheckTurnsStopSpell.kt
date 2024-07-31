package dqbb

class CheckTurnsStopSpell(
    expressionType: ExpressionType,
    operatorType: OperatorType,
    value: Int,
) : Check(
    expressionType = expressionType,
    operatorType = operatorType,
    value = value,
) {

    override fun getExactValue(actor: Actor): Int {
        val turnsStopSpell = actor.turnsStopSpell
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "actor.turnsStopSpell=$turnsStopSpell"
        )
        return turnsStopSpell
    }

    override fun getPercentageValue(actor: Actor): Int {
        val turnsStopSpellPercentage = actor.turnsStopSpellPercentage
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "actor.turnsStopSpellPercentage=$turnsStopSpellPercentage"
        )
        return turnsStopSpellPercentage
    }
}
