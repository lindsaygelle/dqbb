package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class JSONState(
    val matchType: MatchType,
    val qualifiers: List<JSONQualify>,
) {
    fun build(): State {
        return State(
            matchType = this.matchType,
            qualifiers = this.qualifiers.map { it.build() }
        )
    }
}
