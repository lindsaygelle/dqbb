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
        val magicPointsMaximum = (0..100).random()
        magicPointMaximizer.magicPointsMaximum = magicPointsMaximum
        assertEquals(magicPointsMaximum, magicPointMaximizer.magicPointsMaximum)
    }
}