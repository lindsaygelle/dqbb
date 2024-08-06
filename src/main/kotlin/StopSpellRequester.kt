package dqbb

interface StopSpellRequester {
    val stopSpellRequirement: Int
        get() = stopSpellRequirementRange.random()

    var stopSpellRequirementMaximum: Int

    var stopSpellRequirementMinimum: Int

    val stopSpellRequirementRange: IntRange
        get() = (stopSpellRequirementMinimum..stopSpellRequirementMaximum)
}
