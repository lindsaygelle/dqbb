package dqbb

interface InvokerHurt : InvokerMagic {
    var hurtRangeMaximum: Int
    var hurtRangeMinimum: Int
    var hurtRequirementMaximum: Int
    var hurtRequirementMinimum: Int
    var hurtScale: Int
    var hurtShift: Int
}