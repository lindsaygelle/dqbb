package dqbb

interface ExcellentAttacker {
    val excellentAttackRequirement: Int
        get() = excellentAttackRequirementRange.random()
    val excellentAttackRequirementRange: IntRange
        get() = (excellentAttackRequirementMinimum..excellentAttackRequirementMaximum)
    var excellentAttackRequirementMaximum: Int
    var excellentAttackRequirementMinimum: Int
}