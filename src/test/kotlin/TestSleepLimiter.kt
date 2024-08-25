import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestSleepLimiter {
    private class SleepLimiter : dqbb.SleepLimiter {
        override var turnsSleepMaximum: Int = 0
        override var turnsSleepMinimum: Int = 0
    }

    private lateinit var sleepLimiter: dqbb.SleepLimiter

    @BeforeTest
    fun before() {
        sleepLimiter = SleepLimiter()
    }

    @Test
    fun testTurnsSleepMaximum() {
        val turnsSleepMaximum = (0..100).random()
        sleepLimiter.turnsSleepMaximum = turnsSleepMaximum
        assertEquals(turnsSleepMaximum, sleepLimiter.turnsSleepMaximum)
    }

    @Test
    fun testTurnsSleepMinimum() {
        val turnsSleepMinimum = (0..100).random()
        sleepLimiter.turnsSleepMinimum = turnsSleepMinimum
        assertEquals(turnsSleepMinimum, sleepLimiter.turnsSleepMinimum)
    }
}