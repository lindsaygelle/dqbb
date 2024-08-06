package dqbb

interface SleepResister {
    val sleepResistance: Int
        get() = sleepResistanceRange.random()

    var sleepResistanceMaximum: Int

    var sleepResistanceMinimum: Int

    val sleepResistanceRange: IntRange
        get() = (sleepResistanceMinimum..sleepResistanceMaximum)
}
