import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestHerbApplier {
    private class HerbApplier : dqbb.HerbApplier {
        override var herbRangeMaximum: Int = 0
        override var herbRangeMinimum: Int = 0
        override var herbScale: Int = 0
        override var herbShift: Int = 0
    }

    private lateinit var herbApplier: dqbb.HerbApplier

    @BeforeTest
    fun before() {
        herbApplier = HerbApplier()
    }

    @Test
    fun testHerbScale() {
        val herbScale = 0x17
        herbApplier.herbScale = herbScale
        assertEquals(herbScale, herbApplier.herbScale)
    }

    @Test
    fun testHerbShift() {
        val herbShift = 0x0F
        herbApplier.herbShift = herbShift
        assertEquals(herbShift, herbApplier.herbShift)
    }

    @Test
    fun testHerb() {
        herbApplier.herbScale = 0x17
        herbApplier.herbShift = 0x0F
        for (i in (0..255)) {
            val herb = herbApplier.herb
            herbApplier.herbRangeMaximum = i
            assertContains((23..38), herb) // The formula is showing different values
        }
    }
}
