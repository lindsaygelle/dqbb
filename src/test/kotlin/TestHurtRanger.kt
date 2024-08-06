import dqbb.HurtRanger
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertContains

internal class TestHurtRanger {
    private val hurtRanger: HurtRanger = MockHurtRanger()

    @Test
    fun hurtRangeMaximum() {
        val hurtRangeMaximum = (0..1).random()
        hurtRanger.hurtRangeMaximum = hurtRangeMaximum
        assertEquals(
            hurtRangeMaximum, hurtRanger.hurtRangeMaximum
        )
    }

    @Test
    fun hurtRangeMinimum() {
        val hurtRangeMinimum = (0..1).random()
        hurtRanger.hurtRangeMinimum = hurtRangeMinimum
        assertEquals(
            hurtRangeMinimum, hurtRanger.hurtRangeMinimum
        )
    }

    @Test
    fun hurtRangeRangeFirst() {
        val hurtRangeMinimum = (0..1).random()
        hurtRanger.hurtRangeMinimum = hurtRangeMinimum
        assertEquals(
            hurtRanger.hurtRangeMinimum, hurtRanger.hurtRange.first
        )

    }

    @Test
    fun hurtRangeRangeLast() {
        val hurtRangeMaximum = (0..1).random()
        hurtRanger.hurtRangeMaximum = hurtRangeMaximum
        assertEquals(
            hurtRanger.hurtRangeMaximum, hurtRanger.hurtRange.last
        )
    }

    @Test
    fun hurtRangeValue() {
        assertContains(
            hurtRanger.hurtRange, hurtRanger.hurtRangeValue
        )
    }
}