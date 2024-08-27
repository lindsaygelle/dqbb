import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestRunRequester {
    private class RunRequester : dqbb.RunRequester {
        override var agility: Int = 0
        override var runRangeMaximum: Int = 0
        override var runRangeMinimum: Int = 0
    }

    private lateinit var runRequester: dqbb.RunRequester

    @BeforeTest
    fun before() {
        runRequester = RunRequester()
    }

    @Test
    fun test() {
        runRequester.agility = 1
        for (runRangeMaximum in (0..63)) {
            runRequester.runRangeMaximum = runRangeMaximum
            runRequester.runRangeMinimum = runRequester.runRangeMaximum
            assertEquals(runRangeMaximum, runRequester.runRequirement)
        }
    }
}