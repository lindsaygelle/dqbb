import dqbb.HerbApplier

internal class MockHerbApplier : HerbApplier {
    override var herbRangeMaximum: Int = 0
    override var herbRangeMinimum: Int = 0
    override var herbScale: Int = 0
    override var herbShift: Int = 0
}