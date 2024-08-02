package dqbb

import kotlinx.serialization.Serializable

@Serializable
enum class TargetType {
    ANY, ALLY, ENEMY, SELF,
}
