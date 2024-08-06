import dqbb.BreatheFireApplier
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestBreatheFireApplier {
    private val breatheFireApplier: BreatheFireApplier = MockBreatheFireApplier()

    @Test
    fun breatheFireRangeMaximum() {
        val breatheFireRangeMaximum = (0..1).random()
        breatheFireApplier.breatheFireRangeMaximum = breatheFireRangeMaximum
        assertEquals(
            breatheFireRangeMaximum, breatheFireApplier.breatheFireRangeMaximum
        )
    }

    @Test
    fun breatheFireRangeMinimum() {
        val breatheFireRangeMinimum = (0..1).random()
        breatheFireApplier.breatheFireRangeMinimum = breatheFireRangeMinimum
        assertEquals(
            breatheFireRangeMinimum, breatheFireApplier.breatheFireRangeMinimum
        )
    }

    @Test
    fun breatheFireRangeRangeFirst() {
        val breatheFireRangeMinimum = (0..1).random()
        breatheFireApplier.breatheFireRangeMaximum = breatheFireRangeMinimum
        assertEquals(
            breatheFireApplier.breatheFireRangeMinimum, breatheFireApplier.breatheFireRange.first
        )

    }

    @Test
    fun breatheFireRangeRangeLast() {
        val breatheFireRangeMaximum = (0..1).random()
        breatheFireApplier.breatheFireRangeMaximum = breatheFireRangeMaximum
        assertEquals(
            breatheFireApplier.breatheFireRangeMaximum, breatheFireApplier.breatheFireRange.last
        )
    }

    @Test
    fun breatheFireRangeValue() {
        assertContains(
            breatheFireApplier.breatheFireRange, breatheFireApplier.breatheFireRangeValue
        )
    }

    @Test
    fun breatheFireScale() {
        val breatheFireScale = (0..1).random()
        breatheFireApplier.breatheFireScale = breatheFireScale
        assertEquals(
            breatheFireScale, breatheFireApplier.breatheFireScale
        )
    }

    @Test
    fun breatheFireShift() {
        val breatheFireShift = (0..1).random()
        breatheFireApplier.breatheFireShift = breatheFireShift
        assertEquals(
            breatheFireShift, breatheFireApplier.breatheFireShift
        )
    }
}