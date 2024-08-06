import dqbb.SleepRequester

internal class MockSleepRequester : SleepRequester {
    override var sleepRequirementMaximum: Int = 0
    override var sleepRequirementMinimum: Int = 0
}