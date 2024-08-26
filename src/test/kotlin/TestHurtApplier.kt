import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestHurtApplier {
    private class HurtApplier : dqbb.HurtApplier {
        override var hurtRangeMaximum: Int = 0
        override var hurtRangeMinimum: Int = 0
        override var hurtScale: Int = 0
        override var hurtShift: Int = 0
    }

    private lateinit var hurtApplier: dqbb.HurtApplier

    @BeforeTest
    fun before() {
        hurtApplier = HurtApplier()
    }

    @Test
    fun testHurtScale() {
        val hurtScale = 0x03
        hurtApplier.hurtScale = hurtScale
        assertEquals(hurtScale, hurtApplier.hurtScale)
    }

    @Test
    fun testHurtShift() {
        val hurtShift = 0x07
        hurtApplier.hurtShift = hurtShift
        assertEquals(hurtShift, hurtApplier.hurtShift)
    }

    @Test
    fun testHurtEnemy() {
        hurtApplier.hurtScale = 0x03
        hurtApplier.hurtShift = 0x07
        for (i in (0..255)) {
            val hurt = hurtApplier.hurt
            hurtApplier.hurtRangeMaximum = i
            assertContains((3..10), hurt)
        }
    }

    @Test
    fun testHurtHero() {
        hurtApplier.hurtScale = 0x05
        hurtApplier.hurtShift = 0x07
        for (i in (0..255)) {
            val hurt = hurtApplier.hurt
            hurtApplier.hurtRangeMaximum = i
            assertContains((5..12), hurt)
        }
    }
}
