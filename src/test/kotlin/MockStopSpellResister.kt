import dqbb.StopSpellResister

internal class MockStopSpellResister : StopSpellResister {
    override var stopSpellResistanceMaximum: Int = 0
    override var stopSpellResistanceMinimum: Int = 0
}