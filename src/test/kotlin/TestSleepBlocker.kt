import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestSleepBlocker {
    private class SleepBlocker : dqbb.SleepBlocker {
        override var blocksSleep: Boolean = false
    }

    private lateinit var sleepBlocker: dqbb.SleepBlocker

    @BeforeTest
    fun before() {
        sleepBlocker = SleepBlocker()
    }

    @Test
    fun test() {
        val blocksSleep = listOf(true, false).random()
        sleepBlocker.blocksSleep = blocksSleep
        assertEquals(blocksSleep, sleepBlocker.blocksSleep)
    }
}