package dqbb


abstract class Armor(
    defense: Int,
) : DefenseEquipment {
    final override val defense: Int = maxOf(0, defense)
}
