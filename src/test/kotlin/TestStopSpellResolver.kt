import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestStopSpellResolver {
    private class StopSpellResolver : dqbb.StopSpellResolver {
        override var stopSpellResolutionMaximum: Int = 0
        override var stopSpellResolutionMinimum: Int = 0
    }

    private lateinit var stopSpellResolver: dqbb.StopSpellResolver

    @BeforeTest
    fun before() {
        stopSpellResolver = StopSpellResolver()
    }

    @Test
    fun testStopSpellResolutionMaximum() {
        for (stopSpellResolutionMaximum  in (0..100)) {
            stopSpellResolver.stopSpellResolutionMaximum = stopSpellResolutionMaximum
            assertEquals(stopSpellResolutionMaximum, stopSpellResolver.stopSpellResolutionMaximum)
        }
    }

    @Test
    fun testStopSpellResolutionMinimum() {
        for (stopSpellResolutionMinimum in (0..100)) {
            stopSpellResolver.stopSpellResolutionMinimum = stopSpellResolutionMinimum
            assertEquals(stopSpellResolutionMinimum, stopSpellResolver.stopSpellResolutionMinimum)
        }
    }

    @Test
    fun testStopSpellResolutionRange() {
        val stopSpellResolutionMinimum = (1..100).random()
        val stopSpellResolutionMaximum = ((stopSpellResolutionMinimum + 1)..(stopSpellResolutionMinimum * 2)).random()

        stopSpellResolver.stopSpellResolutionMaximum = stopSpellResolutionMaximum
        stopSpellResolver.stopSpellResolutionMinimum = stopSpellResolutionMinimum

        assertContains(stopSpellResolver.stopSpellResolutionRange, stopSpellResolutionMaximum)
        assertContains(stopSpellResolver.stopSpellResolutionRange, stopSpellResolutionMinimum)
        assertEquals(stopSpellResolutionMinimum, stopSpellResolver.stopSpellResolutionRange.first)
        assertEquals(stopSpellResolutionMaximum, stopSpellResolver.stopSpellResolutionRange.last)
    }

    @Test
    fun testStopSpellResolution() {
        stopSpellResolver.stopSpellResolutionMaximum = 100
        stopSpellResolver.stopSpellResolutionMinimum = (0..<stopSpellResolver.stopSpellResolutionMaximum).random()
        assertContains(stopSpellResolver.stopSpellResolutionRange, stopSpellResolver.stopSpellResolution)
    }
}