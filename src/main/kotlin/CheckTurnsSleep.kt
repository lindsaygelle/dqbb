package dqbb

class CheckTurnsSleep(
    expression: ExpressionType,
    operator: OperatorType,
    value: Int,
) : Check(
    expression = expression,
    operator = operator,
    value = value,
) {
    override fun getExactValue(actor: Actor): Int {
        val turnsSleep = actor.turnsSleep
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "actor.turnsSleep=$turnsSleep"
        )
        return turnsSleep
    }

    override fun getPercentageValue(actor: Actor): Int {
        val turnsSleepPercentage = actor.turnsSleepPercentage
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "actor.turnsSleepPercentage=$turnsSleepPercentage"
        )
        return turnsSleepPercentage
    }
}
