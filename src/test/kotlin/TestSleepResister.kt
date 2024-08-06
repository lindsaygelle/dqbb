import dqbb.SleepResister
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


internal class TestSleepResister {
    private val sleepResister: SleepResister = MockSleepResister()

    @Test
    fun sleepResistanceMaximum() {
        val sleepResistanceMaximum = (0..1).random()
        sleepResister.sleepResistanceMaximum = sleepResistanceMaximum
        assertEquals(
            sleepResistanceMaximum, sleepResister.sleepResistanceMaximum
        )
    }

    @Test
    fun sleepResistanceMinimum() {
        val sleepResistanceMinimum = (0..1).random()
        sleepResister.sleepResistanceMinimum = sleepResistanceMinimum
        assertEquals(
            sleepResistanceMinimum, sleepResister.sleepResistanceMinimum
        )
    }

    @Test
    fun sleepResistanceRangeFirst() {
        assertEquals(
            sleepResister.sleepResistanceMinimum, sleepResister.sleepResistanceRange.first
        )
    }

    @Test
    fun sleepResistanceRangeLast() {
        assertEquals(
            sleepResister.sleepResistanceMaximum, sleepResister.sleepResistanceRange.last
        )
    }

    @Test
    fun sleepResistance() {
        assertContains(
            sleepResister.sleepResistanceRange, sleepResister.sleepResistance
        )
    }
}