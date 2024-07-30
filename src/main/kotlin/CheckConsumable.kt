package dqbb

class CheckConsumable(
    expression: ExpressionType,
    operator: OperatorType,
    private val item: ItemType,
    value: Int,
) : Check(
    expression = expression,
    operator = operator,
    value = value,
) {
    override fun getExactValue(actor: Actor): Int {
        return actor.items.getOrDefault(item, 0)
    }

    override fun getPercentageValue(actor: Actor): Int {
        return 0 // TODO
    }
}
