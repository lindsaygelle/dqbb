package dqbb

interface ReceiverStopSpell : Receiver {
    var armor: Armor?
    var statusResistance: Int
    val statusStopSpell: Boolean
    var turnsStopSpell: Int
    var turnsStopSpellMaximum: Int
    var turnsStopSpellMinimum: Int
    val turnsStopSpellPercentage: Int
}