import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestMagicPotionApplier {
    private class MagicPotionApplier : dqbb.MagicPotionApplier {
        override var magicPotionRangeMaximum: Int = 0
        override var magicPotionRangeMinimum: Int = 0
        override var magicPotionScale: Int = 0
        override var magicPotionShift: Int = 0
    }

    private lateinit var magicPotionApplier: dqbb.MagicPotionApplier

    @BeforeTest
    fun before() {
        magicPotionApplier = MagicPotionApplier()
    }

    @Test
    fun testMagicPotionScale() {
        val magicPotionScale = 0x0A // 10
        magicPotionApplier.magicPotionScale = magicPotionScale
        assertEquals(magicPotionScale, magicPotionApplier.magicPotionScale)
    }

    @Test
    fun testMagicPotionShift() {
        val magicPotionShift = 0x07 // 7
        magicPotionApplier.magicPotionShift = magicPotionShift
        assertEquals(magicPotionShift, magicPotionApplier.magicPotionShift)
    }

    @Test
    fun testMagicPotion() {
        magicPotionApplier.magicPotionScale = 0x14
        magicPotionApplier.magicPotionShift = 0x07
        for (i in (0..255)) {
            val magicPotion = magicPotionApplier.magicPotion
            magicPotionApplier.magicPotionRangeMaximum = i
            assertContains((20..27), magicPotion)
        }
    }
}
