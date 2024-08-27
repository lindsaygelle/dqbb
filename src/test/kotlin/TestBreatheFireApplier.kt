import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestBreatheFireApplier {
    private class BreatheFireApplier : dqbb.BreatheFireApplier {
        override var breatheFireRangeMaximum: Int = 0
        override var breatheFireRangeMinimum: Int = 0
        override var breatheFireScale: Int = 0
        override var breatheFireShift: Int = 0
    }

    private lateinit var breatheFireApplier: dqbb.BreatheFireApplier

    @BeforeTest
    fun before() {
        breatheFireApplier = BreatheFireApplier()
    }

    @Test
    fun testBreatheFireScale() {
        for (breatheFireScale in (0..0x10)) {
            breatheFireApplier.breatheFireScale = breatheFireScale
            assertEquals(breatheFireScale, breatheFireApplier.breatheFireScale)
        }
    }

    @Test
    fun testBreatheFireShift() {
        for (breatheFireShift in (0..0x07)) {
            breatheFireApplier.breatheFireShift = breatheFireShift
            assertEquals(breatheFireShift, breatheFireApplier.breatheFireShift)
        }
    }

    @Test
    fun testBreatheFireWeak() {
        breatheFireApplier.breatheFireScale = 0x10
        breatheFireApplier.breatheFireShift = 0x07
        for (i in (0..255)) {
            val breatheFire = breatheFireApplier.breatheFire
            breatheFireApplier.breatheFireRangeMaximum = i
            assertContains((16..23), breatheFire)
        }
    }

    @Test
    fun testBreatheFireStrong() {
        breatheFireApplier.breatheFireScale = 0x41
        breatheFireApplier.breatheFireShift = 0x07
        for (i in (0..255)) {
            val breatheFire = breatheFireApplier.breatheFire
            breatheFireApplier.breatheFireRangeMaximum = i
            assertContains((65..72), breatheFire)
        }
    }
}
