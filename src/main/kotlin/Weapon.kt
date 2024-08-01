package dqbb

open class Weapon(
    attack: Int,
    name: String,
) : Equipment(
    name = name,
) {
    val attack: Int = maxOf(0, attack)
}
