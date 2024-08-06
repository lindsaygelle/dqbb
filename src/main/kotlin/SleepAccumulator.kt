package dqbb

interface SleepAccumulator {
    val statusSleep: Boolean
        get() = turnsSleep > 0

    var turnsSleep: Int

    var turnsSleepMaximum: Int

    var turnsSleepMinimum: Int

    val turnsSleepPercentage: Int
        get() = ((turnsSleep.toDouble() / turnsSleepMaximum) * 100).toInt()
}
