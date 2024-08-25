import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestSleepAccumulator {
    private class SleepAccumulator : dqbb.SleepAccumulator {
        override var turnsSleep: Int = 0
    }

    private lateinit var sleepAccumulator: dqbb.SleepAccumulator

    @BeforeTest
    fun before() {
        sleepAccumulator = SleepAccumulator()
    }

    @Test
    fun test() {
        val turnsSleep = (0..100).random()
        sleepAccumulator.turnsSleep = turnsSleep
        assertEquals(turnsSleep, sleepAccumulator.turnsSleep)
    }
}