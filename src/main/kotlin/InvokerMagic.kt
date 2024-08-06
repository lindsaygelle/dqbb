package dqbb

interface InvokerMagic : Invoker {
    val hasMagicPoints: Boolean
        get() = magicPoints > 0
    var magicPoints: Int
    var magicPointsMaximum: Int
    val magicPointsPercentage: Int
    val statusStopSpell: Boolean
}