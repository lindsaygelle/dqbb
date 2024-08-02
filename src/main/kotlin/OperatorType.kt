package dqbb

import kotlinx.serialization.Serializable

@Serializable
enum class OperatorType {
    EQUAL, GREATER_THAN, LESS_THAN, NOT,
}
