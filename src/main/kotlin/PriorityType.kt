package dqbb

import kotlinx.serialization.Serializable

@Serializable
enum class PriorityType {
    LOWEST,
    LOW,
    EQUAL,
    HIGH,
    HIGHEST
}
