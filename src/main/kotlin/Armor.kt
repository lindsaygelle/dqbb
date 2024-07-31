package dqbb


abstract class Armor(
    defense: Int,
) {
    val defense: Int = maxOf(0, defense)
}