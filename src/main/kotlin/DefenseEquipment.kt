package dqbb

abstract class DefenseEquipment(
    defense: Int,
    name: String,
) : Equipment(
    name = name,
) {
    val defense: Int = maxOf(0, defense)
}
