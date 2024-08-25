package dqbb

interface HurtMoreApplier: HurtRanger {
    val hurtMore: Int
        get() = maxOf(0, ((hurtRangeValue and hurtMoreShift) + hurtMoreScale))
    var hurtMoreScale: Int
    var hurtMoreShift: Int
}