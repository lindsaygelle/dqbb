package dqbb

class CheckMagicPotions(
    expressionType: ExpressionType,
    operatorType: OperatorType,
    value: Int,
) : CheckConsumable(
    expressionType = expressionType,
    item = ItemType.MAGIC_POTION,
    operatorType = operatorType,
    value = value,
)