package dqbb

interface HealMoreApplier : HealRanger {
    val healMore: Int
        get() = maxOf(1, (healRangeValue and healMoreShift) + healMoreScale)

    var healMoreScale: Int

    var healMoreShift: Int
}