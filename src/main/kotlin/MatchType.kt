package dqbb

import kotlinx.serialization.Serializable

@Serializable
enum class MatchType {
    ALL, ANY,
}
