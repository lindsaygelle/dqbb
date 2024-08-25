package dqbb

interface HurtApplier: HurtRanger {
    val hurt: Int
        get() = maxOf(0, ((hurtRangeValue and hurtShift) + hurtScale))
    var hurtScale: Int
    var hurtShift: Int
}