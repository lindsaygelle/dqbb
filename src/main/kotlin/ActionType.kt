package dqbb

import kotlinx.serialization.Serializable

@Serializable
enum class ActionType {
    ATTACK,
    HEAL,
    HEAL_MORE,
    HERB,
    HURT,
    HURT_MORE,
    MAGIC_POTION,
    SLEEP,
    STOP_SPELL,
}
