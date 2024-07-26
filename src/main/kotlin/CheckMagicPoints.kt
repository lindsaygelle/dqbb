package dqbb

class CheckMagicPoints(
    expression: ExpressionType,
    operator: OperatorType,
    value: Int,
) : Check(
    expression = expression,
    operator = operator,
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
