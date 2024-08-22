package dqbb

interface SleepRequester {
    val sleepRequirement: Int
        get() = maxOf(0, sleepRequirementRange.random())
    var sleepRequirementMaximum: Int
    var sleepRequirementMinimum: Int
    val sleepRequirementRange: IntRange
        get() = (sleepRequirementMinimum..sleepRequirementMaximum)
}