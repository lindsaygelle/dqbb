import dqbb.HealApplier
import kotlin.test.assertEquals
import kotlin.test.Test

internal class TestHealApplier {
    private val healApplier: HealApplier = MockHealApplier()

    @Test
    fun healScale() {
        val healScale = (0..1).random()
        healApplier.healScale = healScale
        assertEquals(
            healScale, healApplier.healScale
        )
    }

    @Test
    fun healShift() {
        val healShift = (0..1).random()
        healApplier.healShift = healShift
        assertEquals(
            healShift, healApplier.healShift
        )
    }
}