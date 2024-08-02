package dqbb

import kotlinx.serialization.*

@Serializable
data class JSONWeapon(
    val attack: Int = 1,
    val weaponType: WeaponType?
) {
    fun build(): Weapon {
        return when (this.weaponType) {
            WeaponType.BAMBOO_STICK -> WeaponBambooStick
            WeaponType.BROAD_SWORD -> WeaponBroadSword
            WeaponType.CLUB -> WeaponClub
            WeaponType.COPPER_SWORD -> WeaponCopperSword
            WeaponType.ERDRICKS_SWORD -> WeaponErdricksSword
            WeaponType.FLAME_SWORD -> WeaponFlameSword
            WeaponType.HAND_AXE -> WeaponHandAxe
            WeaponType.CUSTOM,
            null -> Weapon(
                attack = this.attack,
                weaponType = WeaponType.CUSTOM,
            )
        }
    }
}
