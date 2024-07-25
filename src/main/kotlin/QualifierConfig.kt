package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class QualifierConfig(
    val checkers: List<CheckConfig>,
    val match: String,
    val priority: String,
    val target: String,
)
