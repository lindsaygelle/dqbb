import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestRunResister {
    private class RunResister : dqbb.RunResister {
        override var agility: Int = 0
        override var runRangeMaximum: Int = 0
        override var runRangeMinimum: Int = 0
        override var runShift: Int = 0
    }

    private lateinit var runResister: dqbb.RunResister

    @BeforeTest
    fun before() {
        runResister = RunResister()
    }

    @Test
    fun test() {
        runResister.agility = 1
        for (runRangeMaximum in (0..63)) {
            runResister.runRangeMaximum = runRangeMaximum
            runResister.runRangeMinimum = runResister.runRangeMaximum
            assertEquals(runRangeMaximum, runResister.runResistance)
        }
    }

    @Test
    fun testRunShift() {
        runResister.agility = 1
        runResister.runShift = 2
        runResister.runRangeMaximum = 1
        runResister.runRangeMinimum = runResister.runRangeMaximum
        assertEquals(0, runResister.runResistance)
    }
}