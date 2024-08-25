package dqbb

interface RunResister : AgilityPointer,
    RunRanger {
    val runResistance: Int
        get() = maxOf(0, (agility * (runRangeValue shr runShift)))
    var runShift: Int
}