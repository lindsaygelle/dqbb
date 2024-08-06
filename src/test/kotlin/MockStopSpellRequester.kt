import dqbb.StopSpellRequester

internal class MockStopSpellRequester : StopSpellRequester {
    override var stopSpellRequirementMaximum: Int = 0
    override var stopSpellRequirementMinimum: Int = 0
}