import dqbb.HurtRequester

internal class MockHurtRequester : HurtRequester {
    override var hurtRequirementMaximum: Int = 0
    override var hurtRequirementMinimum: Int = 0
}