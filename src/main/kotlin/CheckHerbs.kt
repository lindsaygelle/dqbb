package dqbb

class CheckHerbs(
    expressionType: ExpressionType,
    operatorType: OperatorType,
    value: Int,
) : CheckConsumable(
    expressionType = expressionType,
    item = ItemType.HERB,
    operatorType = operatorType,
    value = value,
)