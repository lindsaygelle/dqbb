package dqbb

interface StopSpellAccumulator {
    var turnsStopSpell: Int

    var turnsStopSpellMaximum: Int

    var turnsStopSpellMinimum: Int

    val turnsStopSpellPercentage: Int
        get() = ((turnsStopSpell.toDouble() / turnsStopSpellMaximum) * 100).toInt()

    val statusStopSpell: Boolean
        get() = turnsStopSpell > 0
}
