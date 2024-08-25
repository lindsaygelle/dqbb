package dqbb

interface HurtResister {
    val hurtResistance: Int
        get() = maxOf(0, hurtResistanceRange.random())
    var hurtResistanceMaximum: Int
    var hurtResistanceMinimum: Int
    val hurtResistanceRange: IntRange
        get() = (hurtResistanceMinimum..hurtResistanceMaximum)
}