package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class CheckConfig(
    val attribute: String,
    val expression: String,
    val operator: String,
    val priority: String,
    val value: Any
)