package dqbb

interface StopSpellResolver {
    val stopSpellResolution: Int
        get() = stopSpellResolutionRange.random()
    var stopSpellResolutionMaximum: Int
    var stopSpellResolutionMinimum: Int
    val stopSpellResolutionRange: IntRange
        get() = (stopSpellResolutionMinimum..stopSpellResolutionMaximum)
}