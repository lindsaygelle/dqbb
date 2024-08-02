package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class JSONDecision(
    val ability: JSONAbility,
    val preCondition: JSONState,
    val priorityType: PriorityType?,
    val targetSelection: JSONState,
) {
    fun build(): Decision {
        return Decision(
            ability = this.ability.build(),
            preCondition = this.preCondition.build(),
            priorityType = this.priorityType ?: PriorityType.HIGHEST,
            targetSelection = this.targetSelection.build(),
        )
    }
}
