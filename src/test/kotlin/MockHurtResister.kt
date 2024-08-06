import dqbb.HurtResister

internal class MockHurtResister : HurtResister {
    override var hurtResistanceMaximum: Int = 0
    override var hurtResistanceMinimum: Int = 0
}