package dqbb

interface InvokerHealMore : InvokerMagic {
    var healMoreRangeMaximum: Int
    var healMoreRangeMinimum: Int
    var healMoreScale: Int
    var healMoreShift: Int
}