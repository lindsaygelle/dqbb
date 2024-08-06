package dqbb

interface SleepRequester {
    val sleepRequirement: Int
        get() = sleepRequirementRange.random()

    var sleepRequirementMaximum: Int

    var sleepRequirementMinimum: Int

    val sleepRequirementRange: IntRange
        get() = (sleepRequirementMinimum..sleepRequirementMaximum)
}
