import dqbb.SleepResolver
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


internal class TestSleepResolver {
    private val sleepResolver: SleepResolver = MockSleepResolver()

    @Test
    fun sleepResolutionMaximum() {
        val sleepResolutionMaximum = (0..1).random()
        sleepResolver.sleepResolutionMaximum = sleepResolutionMaximum
        assertEquals(
            sleepResolutionMaximum, sleepResolver.sleepResolutionMaximum
        )
    }

    @Test
    fun sleepResolutionMinimum() {
        val sleepResolutionMinimum = (0..1).random()
        sleepResolver.sleepResolutionMinimum = sleepResolutionMinimum
        assertEquals(
            sleepResolutionMinimum, sleepResolver.sleepResolutionMinimum
        )
    }

    @Test
    fun sleepResolutionRangeFirst() {
        assertEquals(
            sleepResolver.sleepResolutionMinimum, sleepResolver.sleepResolutionRange.first
        )
    }

    @Test
    fun sleepResolutionRangeLast() {
        assertEquals(
            sleepResolver.sleepResolutionMaximum, sleepResolver.sleepResolutionRange.last
        )
    }

    @Test
    fun sleepResolution() {
        assertContains(
            sleepResolver.sleepResolutionRange, sleepResolver.sleepResolution
        )
    }
}