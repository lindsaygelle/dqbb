package dqbb

import kotlinx.serialization.*

@Serializable
data class JSONQualify(
    val checkers: List<JSONCheckActor>,
    val matchType: MatchType,
    val priorityType: PriorityType,
    val targetType: TargetType,
) {
    fun build(): Qualify {
        return Qualify(
            checkers = checkers.map { it.build() },
            matchType = matchType,
            priorityType = priorityType,
            targetType = targetType
        )
    }
}
