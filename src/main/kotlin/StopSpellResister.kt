package dqbb

interface StopSpellResister {
    val stopSpellResistance: Int
        get() = stopSpellResistanceRange.random()

    var stopSpellResistanceMaximum: Int

    var stopSpellResistanceMinimum: Int

    val stopSpellResistanceRange: IntRange
        get() = (stopSpellResistanceMinimum..stopSpellResistanceMaximum)
}
