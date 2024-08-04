package dqbb

import kotlinx.serialization.Serializable

@Serializable
enum class ConditionType {
    AGILITY,
    HERBS,
    HIT_POINTS,
    MAGIC_POINTS,
    MAGIC_POINTS_MAXIMUM,
    MAGIC_POTIONS,
    STATUS_RESISTANCE,
    TURNS_SLEEP,
    TURNS_STOP_SPELL
}
