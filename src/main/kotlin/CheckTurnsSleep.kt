package dqbb

class CheckTurnsSleep(
<<<<<<< HEAD
    expressionType: ExpressionType,
    operatorType: OperatorType,
    value: Int,
) : Check(
    expressionType = expressionType,
    operatorType = operatorType,
=======
    expression: ExpressionType,
    operator: OperatorType,
    value: Int,
) : Check(
    expression = expression,
    operator = operator,
>>>>>>> 62c97b8d57ae4c9ba761fad9cf044e10d3b47127
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
