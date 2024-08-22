package dqbb

interface MagicPotionApplier {
    val magicPotion: Int
        get() = maxOf(1, ((magicPotionRangeValue and magicPotionShift) + magicPotionScale))
    var magicPotionRangeMaximum: Int
    var magicPotionRangeMinimum: Int
    val magicPotionRange: IntRange
        get() = (magicPotionRangeMinimum..magicPotionRangeMaximum)
    val magicPotionRangeValue: Int
        get() = magicPotionRange.random()
    var magicPotionScale: Int
    var magicPotionShift: Int
}