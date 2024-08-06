package dqbb

interface MagicPointer {
    val hasMagicPoints: Boolean
        get() = magicPoints > 0

    var magicPoints: Int

    var magicPointsMaximum: Int

    val magicPointsPercentage: Int
        get() = ((magicPoints.toDouble() / magicPointsMaximum) * 100).toInt()
}
