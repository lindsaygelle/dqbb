package dqbb

open class Shield(
    defense: Int,
    private val shieldType: ShieldType,
) : Identifier {
    val defense: Int = maxOf(0, defense)

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    val name: String = this.shieldType.toString()
}
