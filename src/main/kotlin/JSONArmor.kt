package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class JSONArmor(
    val armorType: ArmorType?,
    val defense: Int = 1
) {
    fun build(): Armor {
        return when (this.armorType) {
            ArmorType.CHAIN_MAIL -> ArmorChainMail
            ArmorType.CLOTHES -> ArmorClothes
            ArmorType.ERDRICK -> ArmorErdrick
            ArmorType.FULL_PLATE -> ArmorFullPlate
            ArmorType.HALF_PLATE -> ArmorHalfPlate
            ArmorType.LEATHER -> ArmorLeather
            ArmorType.MAGIC -> ArmorMagic
            else -> Armor(
                armorType = ArmorType.CUSTOM,
                defense = defense,
            )
        }
    }
}
