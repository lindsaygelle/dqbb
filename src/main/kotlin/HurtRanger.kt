package dqbb

interface HurtRanger {
    val hurtRange: IntRange
        get() = (hurtRangeMinimum..hurtRangeMaximum)
    var hurtRangeMaximum: Int
    var hurtRangeMinimum: Int
    val hurtRangeValue: Int
        get() = hurtRange.random()
}