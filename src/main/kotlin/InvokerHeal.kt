package dqbb

interface InvokerHeal : InvokerMagic {
    var healRangeMaximum: Int
    var healRangeMinimum: Int
    var healScale: Int
    var healShift: Int
}