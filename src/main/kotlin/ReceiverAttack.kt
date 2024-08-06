package dqbb

interface ReceiverAttack : Receiver {
    var agility: Int
    var armor: Armor?
    var damageResistance: Int
    val defense: Int
        get() = ((armor?.defense ?: 0) + (shield?.defense ?: 0))
    var shield: Shield?
}