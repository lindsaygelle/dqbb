package dqbb

interface HerbApplier {
    val herb: Int
        get() = maxOf(0, ((herbRangeValue and herbShift) + herbScale))
    var herbRangeMaximum: Int
    var herbRangeMinimum: Int
    val herbRange: IntRange
        get() = (herbRangeMinimum..herbRangeMaximum)
    val herbRangeValue: Int
        get() = herbRange.random()
    var herbScale: Int
    var herbShift: Int
}