package dqbb

interface RunResister : AgilityPointer,
    RunRanger {
    val runResistance: Int
        get() = agility * (runRangeValue shr runShift)
    var runShift: Int
}