package dqbb

class CheckMagicPoints(
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
        val magicPoints = actor.magicPoints
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "actor.magicPoints=$magicPoints"
        )
        return magicPoints
    }

    override fun getPercentageValue(actor: Actor): Int {
        val magicPointsPercentage = actor.magicPointsPercentage
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "actor.magicPointsPercentage=$magicPointsPercentage"
        )
        return magicPointsPercentage
    }
}
