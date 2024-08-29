package dqbb

fun main() {

    val a1 = Actor()
    a1.hitPointsMaximum = 10
    a1.hitPoints = 5
    a1.magicPointsMaximum = 10
    a1.magicPoints = 2
    val a2 = Actor()
    a2.hitPointsMaximum = 10
    a2.hitPoints = 2
    val a3 = Actor()
    a3.allegiance = 1
    val a4 = Actor()
    a4.allegiance = 1

    Action(
        ability = Heal(2),
        actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 2
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.SELF
                ),

                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN_EQUAL,
                                    value = 25
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.ALLY
                )
            )
        ),
        actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ),
            selectionType = SelectionType.ALLY
        ),
    ).use(a1, listOf(a1, a2, a3, a4))
}