package dqbb

const val ARMOR_DEFENSE_MINIMUM: Int = 1

class Armor(
    defense: Int
) {
    val defense: Int = maxOf(ARMOR_DEFENSE_MINIMUM, defense)
    override fun toString(): String {
        return "class=${super.toString()} defense=$defense hashCode=${hashCode()} "
    }
}
