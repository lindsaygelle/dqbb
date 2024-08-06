import dqbb.StopSpellResister
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


internal class TestStopSpellResister {
    private lateinit var stopSpellResister: StopSpellResister

    @BeforeTest
    fun createStopSpellResister() {
        stopSpellResister = MockStopSpellResister()
    }

    @Test
    fun stopSpellResistanceMaximum() {
        val stopSpellResistanceMaximum = (0..1).random()
        stopSpellResister.stopSpellResistanceMaximum = stopSpellResistanceMaximum
        assertEquals(
            stopSpellResistanceMaximum, stopSpellResister.stopSpellResistanceMaximum
        )
    }

    @Test
    fun stopSpellResistanceMinimum() {
        val stopSpellResistanceMinimum = (0..1).random()
        stopSpellResister.stopSpellResistanceMinimum = stopSpellResistanceMinimum
        assertEquals(
            stopSpellResistanceMinimum, stopSpellResister.stopSpellResistanceMinimum
        )
    }

    @Test
    fun stopSpellResistanceRangeFirst() {
        stopSpellResister.stopSpellResistanceMinimum = (0..10).random()
        assertEquals(
            stopSpellResister.stopSpellResistanceMinimum, stopSpellResister.stopSpellResistanceRange.first
        )
    }

    @Test
    fun stopSpellResistanceRangeLast() {
        stopSpellResister.stopSpellResistanceMaximum = (0..10).random()
        assertEquals(
            stopSpellResister.stopSpellResistanceMaximum, stopSpellResister.stopSpellResistanceRange.last
        )
    }

    @Test
    fun stopSpellResistance() {
        stopSpellResister.stopSpellResistanceMaximum = (10..20).random()
        stopSpellResister.stopSpellResistanceMinimum = (0..stopSpellResister.stopSpellResistanceMaximum).random()
        assertContains(
            stopSpellResister.stopSpellResistanceRange, stopSpellResister.stopSpellResistance
        )
    }
}