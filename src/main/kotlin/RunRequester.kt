package dqbb

interface RunRequester : AgilityPointer,
    RunRanger {
    val runRequirement: Int
        get() = maxOf(0, (agility * runRangeValue))
}