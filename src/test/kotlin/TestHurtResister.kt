import dqbb.HurtResister
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


internal class TestHurtResister {
    private val hurtResister: HurtResister = MockHurtResister()

    @Test
    fun hurtResistanceMaximum() {
        val hurtResistanceMaximum = (0..1).random()
        hurtResister.hurtResistanceMaximum = hurtResistanceMaximum
        assertEquals(
            hurtResistanceMaximum, hurtResister.hurtResistanceMaximum
        )
    }

    @Test
    fun hurtResistanceMinimum() {
        val hurtResistanceMinimum = (0..1).random()
        hurtResister.hurtResistanceMinimum = hurtResistanceMinimum
        assertEquals(
            hurtResistanceMinimum, hurtResister.hurtResistanceMinimum
        )
    }

    @Test
    fun hurtResistanceRangeFirst() {
        assertEquals(
            hurtResister.hurtResistanceMinimum, hurtResister.hurtResistanceRange.first
        )
    }

    @Test
    fun hurtResistanceRangeLast() {
        assertEquals(
            hurtResister.hurtResistanceMaximum, hurtResister.hurtResistanceRange.last
        )
    }

    @Test
    fun hurtResistance() {
        assertContains(
            hurtResister.hurtResistanceRange, hurtResister.hurtResistance
        )
    }
}