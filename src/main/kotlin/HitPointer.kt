package dqbb

interface HitPointer {
    val hasHitPoints: Boolean
        get() = hitPoints > 0

    val hasHitPointsMaximum: Boolean
        get() = hitPoints == hitPointsMaximum

    var hitPoints: Int

    var hitPointsMaximum: Int

    val hitPointsPercentage: Int
        get() = ((hitPoints.toDouble() / hitPointsMaximum) * 100).toInt()
}
