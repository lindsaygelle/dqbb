package dqbb

interface StopSpellRequester {
    val stopSpellRequirement: Int
        get() = maxOf(0, stopSpellRequirementRange.random())
    var stopSpellRequirementMaximum: Int
    var stopSpellRequirementMinimum: Int
    val stopSpellRequirementRange: IntRange
        get() = (stopSpellRequirementMinimum..stopSpellRequirementMaximum)
}