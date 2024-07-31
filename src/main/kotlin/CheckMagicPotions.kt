package dqbb

class CheckMagicPotions(
    expressionType: ExpressionType,
    operatorType: OperatorType,
    priorityType: PriorityType = PriorityType.HIGHEST,
    value: Int,
) : CheckConsumable(
    expressionType = expressionType,
    item = ItemType.MAGIC_POTION,
    operatorType = operatorType,
    priorityType = priorityType,
    value = value,
)