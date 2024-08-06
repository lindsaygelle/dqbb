import dqbb.HurtApplier
import kotlin.test.assertEquals
import kotlin.test.Test

internal class TestHurtApplier {
    private val hurtApplier: HurtApplier = MockHurtApplier()

    @Test
    fun hurtScale() {
        val hurtScale = (0..1).random()
        hurtApplier.hurtScale = hurtScale
        assertEquals(
            hurtScale, hurtApplier.hurtScale
        )
    }

    @Test
    fun hurtShift() {
        val hurtShift = (0..1).random()
        hurtApplier.hurtShift = hurtShift
        assertEquals(
            hurtShift, hurtApplier.hurtShift
        )
    }
}