import dqbb.HealMoreApplier
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertContains

internal class TestHealMoreApplier {
    private val healMoreApplier: HealMoreApplier = MockHealMoreApplier()

    @Test
    fun healMoreScale() {
        val healMoreScale = (0..1).random()
        healMoreApplier.healMoreScale = healMoreScale
        assertEquals(
            healMoreScale, healMoreApplier.healMoreScale
        )
    }

    @Test
    fun healMoreShift() {
        val healMoreShift = (0..1).random()
        healMoreApplier.healMoreShift = healMoreShift
        assertEquals(
            healMoreShift, healMoreApplier.healMoreShift
        )
    }
}