import dqbb.HurtMoreApplier
import kotlin.test.assertEquals
import kotlin.test.Test

internal class TestHurtMoreApplier {
    private val hurtMoreApplier: HurtMoreApplier = MockHurtMoreApplier()

    @Test
    fun hurtMoreScale() {
        val hurtMoreScale = (0..1).random()
        hurtMoreApplier.hurtMoreScale = hurtMoreScale
        assertEquals(
            hurtMoreScale, hurtMoreApplier.hurtMoreScale
        )
    }

    @Test
    fun hurtMoreShift() {
        val hurtMoreShift = (0..1).random()
        hurtMoreApplier.hurtMoreShift = hurtMoreShift
        assertEquals(
            hurtMoreShift, hurtMoreApplier.hurtMoreShift
        )
    }
}