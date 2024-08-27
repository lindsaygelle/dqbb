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
        for (turnsSleep in (0..100)) {
            sleepAccumulator.turnsSleep = turnsSleep
            assertEquals(turnsSleep, sleepAccumulator.turnsSleep)
        }
    }
}