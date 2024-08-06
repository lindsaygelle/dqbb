package dqbb

interface RunRequester : AgilityPointer,
    RunRanger {
    val runRequirement: Int
        get() = agility * runRangeValue
}