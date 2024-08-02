package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class JSONQualify(
    val checkers: List<JSONCheckActor> = listOf(),
    val matchType: MatchType,
    val priorityType: PriorityType?,
    val targetType: TargetType,
) {
    fun build(): Qualify {
        return Qualify(
            checkers = this.checkers.map { it.build() },
            matchType = this.matchType,
            priorityType = this.priorityType ?: PriorityType.HIGHEST,
            targetType = this.targetType
        )
    }
}
