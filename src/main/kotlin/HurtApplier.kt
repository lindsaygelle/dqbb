package dqbb

interface HurtApplier : HurtRanger {
    val hurt: Int
        get() = maxOf(1, (hurtRangeValue and hurtShift) + hurtScale)

    var hurtScale: Int

    var hurtShift: Int
}
