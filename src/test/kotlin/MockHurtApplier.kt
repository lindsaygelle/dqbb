import dqbb.HurtApplier

internal class MockHurtApplier: HurtApplier {
    override var hurtRangeMaximum: Int = 0
    override var hurtRangeMinimum: Int = 0
    override var hurtScale: Int = 0
    override var hurtShift: Int = 0
}