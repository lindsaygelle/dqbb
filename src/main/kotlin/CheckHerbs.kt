package dqbb

class CheckHerbs(
    expressionType: ExpressionType,
    operatorType: OperatorType,
    priorityType: PriorityType = PriorityType.HIGHEST,
    value: Int,
) : CheckConsumable(
    expressionType = expressionType,
    item = ItemType.HERB,
    operatorType = operatorType,
    priorityType = priorityType,
    value = value,
)
