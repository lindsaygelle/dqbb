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
        for (magicPotionScale in (0..0x0A)) {
            magicPotionApplier.magicPotionScale = magicPotionScale
            assertEquals(magicPotionScale, magicPotionApplier.magicPotionScale)
        }
    }

    @Test
    fun testMagicPotionShift() {
        for (magicPotionShift in (0..0x07) {
            magicPotionApplier.magicPotionShift = magicPotionShift
            assertEquals(magicPotionShift, magicPotionApplier.magicPotionShift)
        }
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
