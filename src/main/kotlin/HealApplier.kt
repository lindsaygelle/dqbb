package dqbb

interface HealApplier : HealRanger {
    val heal: Int
        get() = maxOf(0, ((healRangeValue and healShift) + healScale))
    var healScale: Int
    var healShift: Int
}