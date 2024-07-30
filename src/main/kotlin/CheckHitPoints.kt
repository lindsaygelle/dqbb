package dqbb

class CheckHitPoints(
    expressionType: ExpressionType,
    operatorType: OperatorType,
    value: Int,
) : Check(
    expressionType = expressionType,
    operatorType = operatorType,
    value = value,
) {
    override fun getExactValue(actor: Actor): Int {
        val hitPoints = actor.hitPoints
        logger.debug(
            "$this: " +
                    "actor.hitPoints=$hitPoints " +
                    "actor.id=$actor"
        )
        return hitPoints
    }

    override fun getPercentageValue(actor: Actor): Int {
        val hitPointsPercentage = actor.hitPointsPercentage
        logger.debug(
            "$this: " +
                    "actor.hitPointsPercentage=$hitPointsPercentage " +
                    "actor.id=$actor"
        )
        return hitPointsPercentage
    }
}
