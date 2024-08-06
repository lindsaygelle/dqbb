import dqbb.SleepResolver

internal class MockSleepResolver : SleepResolver {
    override var sleepResolutionMaximum: Int = 0
    override var sleepResolutionMinimum: Int = 0
}