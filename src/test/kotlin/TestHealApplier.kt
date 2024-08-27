import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestHealApplier {
    private class HealApplier : dqbb.HealApplier {
        override var healRangeMaximum: Int = 0
        override var healRangeMinimum: Int = 0
        override var healScale: Int = 0
        override var healShift: Int = 0
    }

    private lateinit var healApplier: dqbb.HealApplier

    @BeforeTest
    fun before() {
        healApplier = HealApplier()
    }

    @Test
    fun testHealScale() {
        for (healScale in (0..0x0A)) {
            healApplier.healScale = healScale
            assertEquals(healScale, healApplier.healScale)
        }
    }

    @Test
    fun testHealShift() {
        for (healShift in (0..0x07)) {
            healApplier.healShift = healShift
            assertEquals(healShift, healApplier.healShift)
        }
    }

    @Test
    fun testHealEnemy() {
        healApplier.healScale = 0x0A // 10
        healApplier.healShift = 0x07 // 7
        for (i in (0..255)) {
            val heal = healApplier.heal
            healApplier.healRangeMaximum = i
            assertContains((10..17), heal)
        }
    }

    @Test
    fun testHealHero() {
        healApplier.healScale = 0x14
        healApplier.healShift = 0x07
        for (i in (0..255)) {
            val heal = healApplier.heal
            healApplier.healRangeMaximum = i
            assertContains((20..27), heal)
        }
    }
}
