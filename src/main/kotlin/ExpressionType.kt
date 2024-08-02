package dqbb

import kotlinx.serialization.Serializable

@Serializable
enum class ExpressionType {
    EXACT,
    PERCENTAGE
}
