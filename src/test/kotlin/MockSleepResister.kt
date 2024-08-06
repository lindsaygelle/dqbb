import dqbb.SleepResister

internal class MockSleepResister : SleepResister {
    override var sleepResistanceMaximum: Int = 0
    override var sleepResistanceMinimum: Int = 0
}