package dqbb

class CheckHitPoints(
    expression: ExpressionType,
    operator: OperatorType,
    value: Int,
) : Check(
    expression = expression,
    operator = operator,
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
