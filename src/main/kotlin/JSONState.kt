package dqbb

import kotlinx.serialization.*

@Serializable
data class JSONState(
    val matchType: MatchType,
    val qualifiers: List<JSONQualify>,
) {
    fun build(): State {
        return State(
            matchType = matchType,
            qualifiers = qualifiers.map { it.build() }
        )
    }
}
