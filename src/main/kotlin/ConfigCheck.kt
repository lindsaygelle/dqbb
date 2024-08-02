package dqbb

import kotlinx.serialization.*

@Serializable
data class ConfigCheckActor(
    val conditionType: ConditionType,
    val expressionType: ExpressionType,
    val operatorType: OperatorType,
    val priorityType: PriorityType,
    val value: Int,
) {
    fun build(): CheckActor {
        return CheckActor(
            conditionType, expressionType, operatorType, priorityType, value
        )
    }
}
