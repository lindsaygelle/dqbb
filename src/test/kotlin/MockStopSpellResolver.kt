import dqbb.StopSpellResolver

internal class MockStopSpellResolver : StopSpellResolver {
    override var stopSpellResolutionMaximum: Int = 0
    override var stopSpellResolutionMinimum: Int = 0
}