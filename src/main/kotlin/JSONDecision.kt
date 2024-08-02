package dqbb

import kotlinx.serialization.*

@Serializable
data class JSONDecision(
    val ability: JSONAbility,
    val preCondition: JSONState,
    val priorityType: PriorityType,
    val targetSelection: JSONState,
) {
    fun build(): Decision {
        return Decision(
            ability = ability.build(),
            preCondition = preCondition.build(),
            priorityType = priorityType,
            targetSelection = targetSelection.build(),
        )
    }
}
