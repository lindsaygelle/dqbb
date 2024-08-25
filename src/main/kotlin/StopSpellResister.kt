package dqbb

interface StopSpellResister {
    val stopSpellResistance: Int
        get() = maxOf(0, stopSpellResistanceRange.random())
    var stopSpellResistanceMaximum: Int
    var stopSpellResistanceMinimum: Int
    val stopSpellResistanceRange: IntRange
        get() = (stopSpellResistanceMinimum..stopSpellResistanceMaximum)
}