package dqbb


open class Armor(
    private val armorType: ArmorType,
    defense: Int,
) : Identifier {
    val defense: Int = maxOf(0, defense)

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    val name: String = this.armorType.toString()
}