import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestHealMoreApplier {
    private class HealMoreApplier : dqbb.HealMoreApplier {
        override var healMoreScale: Int = 0
        override var healMoreShift: Int = 0
        override var healRangeMaximum: Int = 0
        override var healRangeMinimum: Int = 0
    }

    private lateinit var healMoreApplier: dqbb.HealMoreApplier

    @BeforeTest
    fun before() {
        healMoreApplier = HealMoreApplier()
    }

    @Test
    fun testHealMoreScale() {
        val healMoreScale = 0x55 // 85
        healMoreApplier.healMoreScale = healMoreScale
        assertEquals(healMoreScale, healMoreApplier.healMoreScale)
    }

    @Test
    fun testHealMoreShift() {
        val healMoreShift = 0x0F // 15
        healMoreApplier.healMoreShift = healMoreShift
        assertEquals(healMoreShift, healMoreApplier.healMoreShift)
    }

    @Test
    fun testHealMore() {
        healMoreApplier.healMoreScale = 0x55 // 10
        healMoreApplier.healMoreShift = 0x0F // 7
        for (i in (0..255)) {
            val healMore = healMoreApplier.healMore
            healMoreApplier.healRangeMaximum = i
            assertContains((85..100), healMore)
        }
    }
}
