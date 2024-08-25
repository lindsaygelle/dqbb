package dqbb

interface BreatheFireApplier {
    val breatheFire: Int
        get() = maxOf(1, (breatheFireRangeValue and breatheFireShift) + breatheFireScale)
    val breatheFireRange: IntRange
        get() = (breatheFireRangeMinimum..breatheFireRangeMaximum)
    var breatheFireRangeMaximum: Int
    var breatheFireRangeMinimum: Int
    val breatheFireRangeValue: Int
        get() = breatheFireRange.random()
    var breatheFireScale: Int
    var breatheFireShift: Int
}