package dqbb

const val WEAPON_ATTACK_MINIMUM: Int = 1

class Weapon(
    attack: Int
) {
    val attack: Int = maxOf(WEAPON_ATTACK_MINIMUM, attack)
    override fun toString(): String {
        return "attack=$attack class=${super.toString()} hashCode=${hashCode()}"
    }
}