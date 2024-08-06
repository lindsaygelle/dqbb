package dqbb

interface InvokerSleep : InvokerMagic {
    var sleepRequirementMaximum: Int
    var sleepRequirementMinimum: Int
}
