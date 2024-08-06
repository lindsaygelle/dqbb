package dqbb

interface InvokerHurtMore : InvokerMagic {
    val hurtMoreRangeMaximum: Int
    val hurtMoreRangeMinimum: Int
    var hurtMoreRequirementMaximum: Int
    var hurtMoreRequirementMinimum: Int
    var hurtMoreScale: Int
    var hurtMoreShift: Int
}