package dqbb

const val SHIELD_DEFENSE_MINIMUM: Int = 1

class Shield(
    defense: Int
) {
    val defense: Int = maxOf(SHIELD_DEFENSE_MINIMUM, defense)
    override fun toString(): String {
        return "class=${super.toString()} defense=$defense hashCode=${hashCode()}"
    }
}