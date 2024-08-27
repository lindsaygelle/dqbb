import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestHealRanger {
    private class HealRanger : dqbb.HealRanger {
        override var healRangeMaximum: Int = 0
        override var healRangeMinimum: Int = 0

    }

    private lateinit var healRanger: dqbb.HealRanger

    @BeforeTest
    fun before() {
        healRanger = HealRanger()
    }

    @Test
    fun testHealRangeMaximum() {
        for (healRangeMaximum in (0..100)) {
            healRanger.healRangeMaximum = healRangeMaximum
            assertEquals(healRangeMaximum, healRanger.healRangeMaximum)
        }
    }

    @Test
    fun testHealRangeMinimum() {
        for (healRangeMinimum in (0..100)) {
            healRanger.healRangeMinimum = healRangeMinimum
            assertEquals(healRangeMinimum, healRanger.healRangeMinimum)
        }
    }

    @Test
    fun testHealRange() {
        val healRangeMinimum = (1..100).random()
        val healRangeMaximum = ((healRangeMinimum + 1)..(healRangeMinimum * 2)).random()

        healRanger.healRangeMaximum = healRangeMaximum
        healRanger.healRangeMinimum = healRangeMinimum

        assertContains(healRanger.healRange, healRangeMaximum)
        assertContains(healRanger.healRange, healRangeMinimum)
        assertEquals(healRangeMinimum, healRanger.healRange.first)
        assertEquals(healRangeMaximum, healRanger.healRange.last)
    }
}