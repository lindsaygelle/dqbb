package dqbb

open class CheckConsumable(
    expressionType: ExpressionType,
    private val item: ItemType,
    operatorType: OperatorType,
    priorityType: PriorityType,
    value: Int,
) : Check(
    expressionType = expressionType,
    operatorType = operatorType,
    priorityType = priorityType,
    value = value,
) {

    override fun getExactValue(actor: Actor): Int {
        return actor.items.getOrDefault(item, 0)
    }

    override fun getPercentageValue(actor: Actor): Int {
        return this.getExactValue(actor) // TODO
    }
}
