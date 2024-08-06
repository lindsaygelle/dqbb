import dqbb.StopSpellResolver
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


internal class TestStopSpellResolver {
    private lateinit var stopSpellResolver: StopSpellResolver

    @BeforeTest
    fun createStopSpellResolver() {
        stopSpellResolver = MockStopSpellResolver()
    }

    @Test
    fun stopSpellResolutionMaximum() {
        val stopSpellResolutionMaximum = (0..1).random()
        stopSpellResolver.stopSpellResolutionMaximum = stopSpellResolutionMaximum
        assertEquals(
            stopSpellResolutionMaximum, stopSpellResolver.stopSpellResolutionMaximum
        )
    }

    @Test
    fun stopSpellResolutionMinimum() {
        val stopSpellResolutionMinimum = (0..1).random()
        stopSpellResolver.stopSpellResolutionMinimum = stopSpellResolutionMinimum
        assertEquals(
            stopSpellResolutionMinimum, stopSpellResolver.stopSpellResolutionMinimum
        )
    }

    @Test
    fun stopSpellResolutionRangeFirst() {
        assertEquals(
            stopSpellResolver.stopSpellResolutionMinimum, stopSpellResolver.stopSpellResolutionRange.first
        )
    }

    @Test
    fun stopSpellResolutionRangeLast() {
        assertEquals(
            stopSpellResolver.stopSpellResolutionMaximum, stopSpellResolver.stopSpellResolutionRange.last
        )
    }

    @Test
    fun stopSpellResolution() {
        assertContains(
            stopSpellResolver.stopSpellResolutionRange, stopSpellResolver.stopSpellResolution
        )
    }
}