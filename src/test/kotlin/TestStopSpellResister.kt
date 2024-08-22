import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestStopSpellResister {
    private class StopSpellResister : dqbb.StopSpellResister {
        override var stopSpellResistanceMaximum: Int = 0
        override var stopSpellResistanceMinimum: Int = 0
    }

    private lateinit var stopSpellResister: dqbb.StopSpellResister

    @BeforeTest
    fun before() {
        stopSpellResister = StopSpellResister()
    }

    @Test
    fun testStopSpellResistanceMaximum() {
        val stopSpellResistanceMaximum = (0..100).random()
        stopSpellResister.stopSpellResistanceMaximum = stopSpellResistanceMaximum
        assertEquals(stopSpellResistanceMaximum, stopSpellResister.stopSpellResistanceMaximum)
    }

    @Test
    fun testStopSpellResistanceMinimum() {
        val stopSpellResistanceMinimum = (0..100).random()
        stopSpellResister.stopSpellResistanceMinimum = stopSpellResistanceMinimum
        assertEquals(stopSpellResistanceMinimum, stopSpellResister.stopSpellResistanceMinimum)
    }

    @Test
    fun testStopSpellResistanceRange() {
        val stopSpellResistanceMinimum = (1..100).random()
        val stopSpellResistanceMaximum = ((stopSpellResistanceMinimum + 1)..(stopSpellResistanceMinimum * 2)).random()

        stopSpellResister.stopSpellResistanceMaximum = stopSpellResistanceMaximum
        stopSpellResister.stopSpellResistanceMinimum = stopSpellResistanceMinimum

        assertContains(stopSpellResister.stopSpellResistanceRange, stopSpellResistanceMaximum)
        assertContains(stopSpellResister.stopSpellResistanceRange, stopSpellResistanceMinimum)
        assertEquals(stopSpellResistanceMinimum, stopSpellResister.stopSpellResistanceRange.first)
        assertEquals(stopSpellResistanceMaximum, stopSpellResister.stopSpellResistanceRange.last)
    }

    @Test
    fun testStopSpellResistance() {
        stopSpellResister.stopSpellResistanceMaximum = 100
        stopSpellResister.stopSpellResistanceMinimum = (0..<stopSpellResister.stopSpellResistanceMaximum).random()
        assertContains(stopSpellResister.stopSpellResistanceRange, stopSpellResister.stopSpellResistance)
    }
}