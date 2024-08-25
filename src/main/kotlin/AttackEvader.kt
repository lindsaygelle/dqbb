package dqbb

interface AttackEvader {
    val evasionRequirement: Int
        get() = evasionRequirementRange.random()
    var evasionRequirementMaximum: Int
    var evasionRequirementMinimum: Int
    val evasionRequirementRange: IntRange
        get() = (evasionRequirementMinimum..evasionRequirementMaximum)
}