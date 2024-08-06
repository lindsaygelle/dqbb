package dqbb

interface HerbApplier {
    val herb: Int
        get() = maxOf(1, (herbRangeValue and herbShift) + herbScale)

    val herbRange: IntRange
        get() = (herbRangeMinimum..herbRangeMaximum)

    var herbRangeMaximum: Int

    var herbRangeMinimum: Int

    val herbRangeValue: Int
        get() = herbRange.random()

    var herbScale: Int

    var herbShift: Int
}