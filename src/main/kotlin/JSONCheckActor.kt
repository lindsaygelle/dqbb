package dqbb

import kotlinx.serialization.*

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
            conditionType = conditionType,
            expressionType = expressionType,
            operatorType = operatorType,
            priorityType = priorityType,
            value = value
        )
    }
}
