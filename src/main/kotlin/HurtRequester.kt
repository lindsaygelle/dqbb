package dqbb

interface HurtRequester {
    val hurtRequirement: Int
        get() = maxOf(0, hurtRequirementRange.random())
    var hurtRequirementMaximum: Int
    var hurtRequirementMinimum: Int
    val hurtRequirementRange: IntRange
        get() = (hurtRequirementMinimum..hurtRequirementMaximum)
}