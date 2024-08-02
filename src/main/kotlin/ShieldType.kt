package dqbb

import kotlinx.serialization.Serializable

@Serializable
enum class ShieldType {
    CUSTOM,
    LARGE,
    SILVER,
    SMALL,
}
