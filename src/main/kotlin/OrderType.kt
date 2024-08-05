package dqbb

import kotlinx.serialization.Serializable

@Serializable
enum class OrderType {
    MAX,
    MIN
}
