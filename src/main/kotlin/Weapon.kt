package dqbb

open class Weapon(
    attack: Int
) {
    val attack: Int = maxOf(0, attack)
}
