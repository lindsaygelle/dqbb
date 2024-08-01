package dqbb

class CheckTurnsStopSpell(
    expressionType: ExpressionType,
    operatorType: OperatorType,
    priorityType: PriorityType = PriorityType.HIGHEST,
    value: Int,
) : Check(
    expressionType = expressionType,
    operatorType = operatorType,
    priorityType = priorityType,
    value = value,
) {

    override fun getExactValue(actor: Actor): Int {
        val turnsStopSpell = actor.turnsStopSpell
        println(//logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "actor.turnsStopSpell=$turnsStopSpell"
        )
        return turnsStopSpell
    }

    override fun getPercentageValue(actor: Actor): Int {
        val turnsStopSpellPercentage = actor.turnsStopSpellPercentage
        println(//logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "actor.turnsStopSpellPercentage=$turnsStopSpellPercentage"
        )
        return turnsStopSpellPercentage
    }
}
