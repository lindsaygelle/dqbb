package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class JSONCheckActor(
    val conditionType: ConditionType,
    val expressionType: ExpressionType,
    val operatorType: OperatorType,
    val priorityType: PriorityType,
    val value: Int,
) {
    fun build(): CheckActor {
        return CheckActor(
            conditionType = this.conditionType,
            expressionType = this.expressionType,
            operatorType = this.operatorType,
            priorityType = this.priorityType,
            value = this.value
        )
    }
}
