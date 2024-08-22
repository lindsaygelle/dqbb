import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestHurtRanger {
    private class HurtRanger : dqbb.HurtRanger {
        override var hurtRangeMaximum: Int = 0
        override var hurtRangeMinimum: Int = 0

    }

    private lateinit var hurtRanger: dqbb.HurtRanger

    @BeforeTest
    fun before() {
        hurtRanger = HurtRanger()
    }

    @Test
    fun testHurtRangeMaximum() {
        val hurtRangeMaximum = (0..100).random()
        hurtRanger.hurtRangeMaximum = hurtRangeMaximum
        assertEquals(hurtRangeMaximum, hurtRanger.hurtRangeMaximum)
    }

    @Test
    fun testHurtRangeMinimum() {
        val hurtRangeMinimum = (0..100).random()
        hurtRanger.hurtRangeMinimum = hurtRangeMinimum
        assertEquals(hurtRangeMinimum, hurtRanger.hurtRangeMinimum)
    }

    @Test
    fun testHurtRange() {
        val hurtRangeMinimum = (1..100).random()
        val hurtRangeMaximum = ((hurtRangeMinimum + 1)..(hurtRangeMinimum * 2)).random()

        hurtRanger.hurtRangeMaximum = hurtRangeMaximum
        hurtRanger.hurtRangeMinimum = hurtRangeMinimum

        assertContains(hurtRanger.hurtRange, hurtRangeMaximum)
        assertContains(hurtRanger.hurtRange, hurtRangeMinimum)
        assertEquals(hurtRangeMinimum, hurtRanger.hurtRange.first)
        assertEquals(hurtRangeMaximum, hurtRanger.hurtRange.last)
    }
}