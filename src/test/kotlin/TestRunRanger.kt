import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestRunRanger {
    private class RunRanger : dqbb.RunRanger {
        override var runRangeMaximum: Int = 0
        override var runRangeMinimum: Int = 0

    }

    private lateinit var runRanger: dqbb.RunRanger

    @BeforeTest
    fun before() {
        runRanger = RunRanger()
    }

    @Test
    fun testRunRangeMaximum() {
        val runRangeMaximum = (0..100).random()
        runRanger.runRangeMaximum = runRangeMaximum
        assertEquals(runRangeMaximum, runRanger.runRangeMaximum)
    }

    @Test
    fun testRunRangeMinimum() {
        val runRangeMinimum = (0..100).random()
        runRanger.runRangeMinimum = runRangeMinimum
        assertEquals(runRangeMinimum, runRanger.runRangeMinimum)
    }

    @Test
    fun testRunRangeRange() {
        val runRangeMinimum = (1..100).random()
        val runRangeMaximum = ((runRangeMinimum + 1)..(runRangeMinimum * 2)).random()

        runRanger.runRangeMaximum = runRangeMaximum
        runRanger.runRangeMinimum = runRangeMinimum

        assertContains(runRanger.runRange, runRangeMaximum)
        assertContains(runRanger.runRange, runRangeMinimum)
        assertEquals(runRangeMinimum, runRanger.runRange.first)
        assertEquals(runRangeMaximum, runRanger.runRange.last)
    }
}