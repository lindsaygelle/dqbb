package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class JSONQualify(
    val actorCheckers: List<JSONActorChecker> = listOf(),
    val matchType: MatchType,
    val priorityType: PriorityType?,
    val targetType: TargetType,
) {
    fun build(): Qualify {
        return Qualify(
            actorCheckers = this.actorCheckers.map { it.build() },
            matchType = this.matchType,
            priorityType = this.priorityType ?: PriorityType.HIGHEST,
            targetType = this.targetType
        )
    }
}
