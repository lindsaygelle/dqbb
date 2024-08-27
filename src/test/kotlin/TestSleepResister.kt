import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestSleepResister {
    private class SleepResister : dqbb.SleepResister {
        override var sleepResistanceMaximum: Int = 0
        override var sleepResistanceMinimum: Int = 0
    }

    private lateinit var sleepResister: dqbb.SleepResister

    @BeforeTest
    fun before() {
        sleepResister = SleepResister()
    }

    @Test
    fun testSleepResistanceMaximum() {
        for (sleepResistanceMaximum in (0..100)) {
            sleepResister.sleepResistanceMaximum = sleepResistanceMaximum
            assertEquals(sleepResistanceMaximum, sleepResister.sleepResistanceMaximum)
        }
    }

    @Test
    fun testSleepResistanceMinimum() {
        for (sleepResistanceMinimum in (0..100)) {
            sleepResister.sleepResistanceMinimum = sleepResistanceMinimum
            assertEquals(sleepResistanceMinimum, sleepResister.sleepResistanceMinimum)
        }
    }

    @Test
    fun testSleepResistanceRange() {
        val sleepResistanceMinimum = (1..100).random()
        val sleepResistanceMaximum = ((sleepResistanceMinimum + 1)..(sleepResistanceMinimum * 2)).random()

        sleepResister.sleepResistanceMaximum = sleepResistanceMaximum
        sleepResister.sleepResistanceMinimum = sleepResistanceMinimum

        assertContains(sleepResister.sleepResistanceRange, sleepResistanceMaximum)
        assertContains(sleepResister.sleepResistanceRange, sleepResistanceMinimum)
        assertEquals(sleepResistanceMinimum, sleepResister.sleepResistanceRange.first)
        assertEquals(sleepResistanceMaximum, sleepResister.sleepResistanceRange.last)
    }

    @Test
    fun testSleepResistance() {
        sleepResister.sleepResistanceMaximum = 100
        sleepResister.sleepResistanceMinimum = (0..<sleepResister.sleepResistanceMaximum).random()
        assertContains(sleepResister.sleepResistanceRange, sleepResister.sleepResistance)
    }
}