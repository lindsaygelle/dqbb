package dqbb

interface RunRanger {
    var runRangeMaximum: Int
    var runRangeMinimum: Int
    val runRange: IntRange
        get() = (runRangeMinimum..runRangeMaximum)
    val runRangeValue: Int
        get() = runRange.random()
}