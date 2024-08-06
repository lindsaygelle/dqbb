package dqbb

interface HurtRequester {
    val hurtRequirement: Int
        get() = hurtRequirementRange.random()

    var hurtRequirementMaximum: Int

    var hurtRequirementMinimum: Int

    val hurtRequirementRange: IntRange
        get() = (hurtRequirementMinimum..hurtRequirementMaximum)
}
