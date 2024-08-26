import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestHurtMoreApplier {
    private class HurtMoreApplier : dqbb.HurtMoreApplier {
        override var hurtMoreScale: Int = 0
        override var hurtMoreShift: Int = 0
        override var hurtRangeMaximum: Int = 0
        override var hurtRangeMinimum: Int = 0
    }

    private lateinit var hurtMoreApplier: dqbb.HurtMoreApplier

    @BeforeTest
    fun before() {
        hurtMoreApplier = HurtMoreApplier()
    }

    @Test
    fun testHurtMoreScale() {
        val hurtMoreScale = 0x1E
        hurtMoreApplier.hurtMoreScale = hurtMoreScale
        assertEquals(hurtMoreScale, hurtMoreApplier.hurtMoreScale)
    }

    @Test
    fun testHurtMoreShift() {
        val hurtMoreShift = 0x0F
        hurtMoreApplier.hurtMoreShift = hurtMoreShift
        assertEquals(hurtMoreShift, hurtMoreApplier.hurtMoreShift)
    }

    @Test
    fun testHurtMoreEnemy() {
        hurtMoreApplier.hurtMoreScale = 0x1E
        hurtMoreApplier.hurtMoreShift = 0x0F
        for (i in (0..255)) {
            val hurtMore = hurtMoreApplier.hurtMore
            hurtMoreApplier.hurtRangeMaximum = i
            assertContains((30..45), hurtMore)
        }
    }

    @Test
    fun testHurtMoreHero() {
        hurtMoreApplier.hurtMoreScale = 0x3A
        hurtMoreApplier.hurtMoreShift = 0x07
        for (i in (0..255)) {
            val hurtMore = hurtMoreApplier.hurtMore
            hurtMoreApplier.hurtRangeMaximum = i
            assertContains((58..65), hurtMore)
        }
    }
}
