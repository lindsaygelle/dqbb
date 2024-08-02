package dqbb

open class Weapon(
    attack: Int,
    private val weaponType: WeaponType,
) : Identifier {
    val attack: Int = maxOf(0, attack)

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    val name: String = this.weaponType.toString()
}
