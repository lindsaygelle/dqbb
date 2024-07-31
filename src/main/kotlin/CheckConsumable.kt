package dqbb

class CheckConsumable(
<<<<<<< HEAD
    expressionType: ExpressionType,
    operatorType: OperatorType,
    private val item: ItemType,
    value: Int,
) : Check(
    expressionType = expressionType,
    operatorType = operatorType,
=======
    expression: ExpressionType,
    operator: OperatorType,
    private val item: ItemType,
    value: Int,
) : Check(
    expression = expression,
    operator = operator,
>>>>>>> 62c97b8d57ae4c9ba761fad9cf044e10d3b47127
    value = value,
) {
    override fun getExactValue(actor: Actor): Int {
        return actor.items.getOrDefault(item, 0)
    }

    override fun getPercentageValue(actor: Actor): Int {
        return 0 // TODO
    }
}
