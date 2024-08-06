package dqbb

interface SleepResolver {
    val sleepResolution: Int
        get() = sleepResolutionRange.random()

    var sleepResolutionMaximum: Int

    var sleepResolutionMinimum: Int

    val sleepResolutionRange: IntRange
        get() = (sleepResolutionMinimum..sleepResolutionMaximum)
}
