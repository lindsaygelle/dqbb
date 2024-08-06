import dqbb.HerbApplier
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertContains

internal class TestHerbApplier {
    private val herbRanger: HerbApplier = MockHerbApplier()

    @Test
    fun herbRangeMaximum() {
        val herbRangeMaximum = (0..1).random()
        herbRanger.herbRangeMaximum = herbRangeMaximum
        assertEquals(
            herbRangeMaximum, herbRanger.herbRangeMaximum
        )
    }

    @Test
    fun herbRangeMinimum() {
        val herbRangeMinimum = (0..1).random()
        herbRanger.herbRangeMinimum = herbRangeMinimum
        assertEquals(
            herbRangeMinimum, herbRanger.herbRangeMinimum
        )
    }

    @Test
    fun herbRangeRangeFirst() {
        val herbRangeMinimum = (0..1).random()
        herbRanger.herbRangeMaximum = herbRangeMinimum
        assertEquals(
            herbRanger.herbRangeMinimum, herbRanger.herbRange.first
        )

    }

    @Test
    fun herbRangeRangeLast() {
        val herbRangeMaximum = (0..1).random()
        herbRanger.herbRangeMaximum = herbRangeMaximum
        assertEquals(
            herbRanger.herbRangeMaximum, herbRanger.herbRange.last
        )
    }

    @Test
    fun herbRangeValue() {
        assertContains(
            herbRanger.herbRange, herbRanger.herbRangeValue
        )
    }

    @Test
    fun herbScale() {
        val herbScale = (0..1).random()
        herbRanger.herbScale = herbScale
        assertEquals(
            herbScale, herbRanger.herbScale
        )
    }

    @Test
    fun herbShift() {
        val herbShift = (0..1).random()
        herbRanger.herbShift = herbShift
        assertEquals(
            herbShift, herbRanger.herbShift
        )
    }
}