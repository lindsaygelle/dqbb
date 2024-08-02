package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class JSONShield(
    val defense: Int = 1,
    val shieldType: ShieldType?
) {
    fun build(): Shield {
        return when (this.shieldType) {
            ShieldType.LARGE -> ShieldLarge
            ShieldType.SILVER -> ShieldSilver
            ShieldType.SMALL -> ShieldSmall
            ShieldType.CUSTOM,
            null -> Shield(
                defense = defense,
                shieldType = ShieldType.CUSTOM,
            )
        }
    }
}
