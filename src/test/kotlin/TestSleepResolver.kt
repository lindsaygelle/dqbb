import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestSleepResolver {
    private class SleepResolver : dqbb.SleepResolver {
        override var sleepResolutionMaximum: Int = 0
        override var sleepResolutionMinimum: Int = 0
    }

    private lateinit var sleepResolver: dqbb.SleepResolver

    @BeforeTest
    fun before() {
        sleepResolver = SleepResolver()
    }

    @Test
    fun testSleepResolutionMaximum() {
        for (sleepResolutionMaximum in (0..100)) {
            sleepResolver.sleepResolutionMaximum = sleepResolutionMaximum
            assertEquals(sleepResolutionMaximum, sleepResolver.sleepResolutionMaximum)
        }
    }

    @Test
    fun testSleepResolutionMinimum() {
        for (sleepResolutionMinimum in (0..100)) {
            sleepResolver.sleepResolutionMinimum = sleepResolutionMinimum
            assertEquals(sleepResolutionMinimum, sleepResolver.sleepResolutionMinimum)
        }
    }

    @Test
    fun testSleepResolutionRange() {
        val sleepResolutionMinimum = (1..100).random()
        val sleepResolutionMaximum = ((sleepResolutionMinimum + 1)..(sleepResolutionMinimum * 2)).random()

        sleepResolver.sleepResolutionMaximum = sleepResolutionMaximum
        sleepResolver.sleepResolutionMinimum = sleepResolutionMinimum

        assertContains(sleepResolver.sleepResolutionRange, sleepResolutionMaximum)
        assertContains(sleepResolver.sleepResolutionRange, sleepResolutionMinimum)
        assertEquals(sleepResolutionMinimum, sleepResolver.sleepResolutionRange.first)
        assertEquals(sleepResolutionMaximum, sleepResolver.sleepResolutionRange.last)
    }

    @Test
    fun testSleepResolution() {
        sleepResolver.sleepResolutionMaximum = 100
        sleepResolver.sleepResolutionMinimum = (0..<sleepResolver.sleepResolutionMaximum).random()
        assertContains(sleepResolver.sleepResolutionRange, sleepResolver.sleepResolution)
    }
}