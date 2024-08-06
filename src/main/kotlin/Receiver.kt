package dqbb

interface Receiver : Actor {
    var hitPoints: Int
    var hitPointsMaximum: Int
    val hitPointsPercentage: Int
    override val isAlive: Boolean
        get() = (hitPoints > 0)
    override val isNotAlive: Boolean
        get() = !isAlive
}