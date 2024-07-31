package dqbb

class CheckConsumable(
    expressionType: ExpressionType,
    operatorType: OperatorType,
    private val item: ItemType,
    value: Int,
) : Check(
    expressionType = expressionType,
    operatorType = operatorType,
    value = value,
) {

    override fun getExactValue(actor: Actor): Int {
        return actor.items.getOrDefault(item, 0)
    }

    override fun getPercentageValue(actor: Actor): Int {
        return 0 // TODO
    }
}
