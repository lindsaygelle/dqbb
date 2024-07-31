package dqbb

class CheckMagicPoints(
    expressionType: ExpressionType,
    operatorType: OperatorType,
    value: Int,
) : Check(
    expressionType = expressionType,
    operatorType = operatorType,
    value = value,
) {

    override fun getExactValue(actor: Actor): Int {
        val magicPoints = actor.magicPoints
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "actor.magicPoints=$magicPoints"
        )
        return magicPoints
    }

    override fun getPercentageValue(actor: Actor): Int {
        val magicPointsPercentage = actor.magicPointsPercentage
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "actor.magicPointsPercentage=$magicPointsPercentage"
        )
        return magicPointsPercentage
    }
}
