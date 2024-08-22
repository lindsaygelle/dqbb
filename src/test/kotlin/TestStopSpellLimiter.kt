import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestStopSpellLimiter {
    private class StopSpellLimiter : dqbb.StopSpellLimiter {
        override var turnsStopSpellMaximum: Int = 0
        override var turnsStopSpellMinimum: Int = 0
    }

    private lateinit var stopSpellLimiter: dqbb.StopSpellLimiter

    @BeforeTest
    fun before() {
        stopSpellLimiter = StopSpellLimiter()
    }

    @Test
    fun testTurnsStopSpellMaximum() {
        val turnsStopSpellMaximum = (0..100).random()
        stopSpellLimiter.turnsStopSpellMaximum = turnsStopSpellMaximum
        assertEquals(turnsStopSpellMaximum, stopSpellLimiter.turnsStopSpellMaximum)
    }

    @Test
    fun testTurnsStopSpellMinimum() {
        val turnsStopSpellMinimum = (0..100).random()
        stopSpellLimiter.turnsStopSpellMinimum = turnsStopSpellMinimum
        assertEquals(turnsStopSpellMinimum, stopSpellLimiter.turnsStopSpellMinimum)
    }
}