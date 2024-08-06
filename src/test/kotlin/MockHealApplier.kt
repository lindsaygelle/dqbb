import dqbb.HealApplier

internal class MockHealApplier : HealApplier {
    override var healRangeMaximum: Int = 0
    override var healRangeMinimum: Int = 0
    override var healScale: Int = 0
    override var healShift: Int = 0
}