package dqbb

interface ReceiverSleep : Receiver {
    var armor: Armor?
    var statusResistance: Int
    val statusSleep: Boolean
        get() = (turnsSleep > 0)
    var turnsSleep: Int
    var turnsSleepMaximum: Int
    var turnsSleepMinimum: Int
    val turnsSleepPercentage: Int
}