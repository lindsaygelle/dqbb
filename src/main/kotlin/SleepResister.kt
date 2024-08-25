package dqbb

interface SleepResister {
    val sleepResistance: Int
        get() = maxOf(0, sleepResistanceRange.random())
    var sleepResistanceMaximum: Int
    var sleepResistanceMinimum: Int
    val sleepResistanceRange: IntRange
        get() = (sleepResistanceMinimum..sleepResistanceMaximum)
}