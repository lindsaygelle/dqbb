import dqbb.HealRanger
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestHealRanger {
    private val healRanger: HealRanger = MockHealRanger()

    @Test
    fun healRangeMaximum() {
        val healRangeMaximum = (0..1).random()
        healRanger.healRangeMaximum = healRangeMaximum
        assertEquals(
            healRangeMaximum, healRanger.healRangeMaximum
        )
    }

    @Test
    fun healRangeMinimum() {
        val healRangeMinimum = (0..1).random()
        healRanger.healRangeMinimum = healRangeMinimum
        assertEquals(
            healRangeMinimum, healRanger.healRangeMinimum
        )
    }

    @Test
    fun healRangeRangeFirst() {
        val healRangeMinimum = (0..1).random()
        healRanger.healRangeMaximum = healRangeMinimum
        assertEquals(
            healRanger.healRangeMinimum, healRanger.healRange.first
        )

    }

    @Test
    fun healRangeRangeLast() {
        val healRangeMaximum = (0..1).random()
        healRanger.healRangeMaximum = healRangeMaximum
        assertEquals(
            healRanger.healRangeMaximum, healRanger.healRange.last
        )
    }

    @Test
    fun healRangeValue() {
        assertContains(
            healRanger.healRange, healRanger.healRangeValue
        )
    }
}