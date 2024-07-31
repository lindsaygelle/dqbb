package dqbb

class CheckTurnsSleep(
    expressionType: ExpressionType,
    operatorType: OperatorType,
    value: Int,
) : Check(
    expressionType = expressionType,
    operatorType = operatorType,
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
