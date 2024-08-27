import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestMagicPointMaximizer {
    private class MagicPointMaximizer : dqbb.MagicPointMaximizer {
        override var magicPointsMaximum: Int = 0
    }

    private lateinit var magicPointMaximizer: dqbb.MagicPointMaximizer

    @BeforeTest
    fun before() {
        magicPointMaximizer = MagicPointMaximizer()
    }

    @Test
    fun test() {
        for (magicPointsMaximum in (0..100)) {
            magicPointMaximizer.magicPointsMaximum = magicPointsMaximum
            assertEquals(magicPointsMaximum, magicPointMaximizer.magicPointsMaximum)
        }
    }
}