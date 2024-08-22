package dqbb

interface HealMoreApplier : HealRanger {
    val healMore: Int
        get() = maxOf(0, ((healRangeValue and healMoreShift) + healMoreScale))
    var healMoreScale: Int
    var healMoreShift: Int
}