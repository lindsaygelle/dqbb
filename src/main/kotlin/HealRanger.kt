package dqbb

interface HealRanger {
    val healRange: IntRange
        get() = (healRangeMinimum..healRangeMaximum)
    var healRangeMaximum: Int
    var healRangeMinimum: Int
    val healRangeValue: Int
        get() = healRange.random()
}