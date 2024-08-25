import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestHitPointMaximizer {
    private class HitPointMaximizer : dqbb.HitPointMaximizer {
        override var hitPointsMaximum: Int = 0
    }

    private lateinit var hitPointMaximizer: dqbb.HitPointMaximizer

    @BeforeTest
    fun before() {
        hitPointMaximizer = HitPointMaximizer()
    }

    @Test
    fun test() {
        val hitPointsMaximum = (0..100).random()
        hitPointMaximizer.hitPointsMaximum = hitPointsMaximum
        assertEquals(hitPointsMaximum, hitPointMaximizer.hitPointsMaximum)
    }
}