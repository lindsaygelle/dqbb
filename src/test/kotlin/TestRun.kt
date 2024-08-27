import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestRun {
    private class RunInvoker : dqbb.RunInvoker {
        override var agility: Int = 0
        override var isRunning: Boolean = false
        override var runRangeMaximum: Int = 0
        override var runRangeMinimum: Int = 0
        override var turnsSleep: Int = 0
    }

    private class RunReceiver : dqbb.RunReceiver {
        override var agility: Int = 0
        override var hitPoints: Int = 0
        override var runShift: Int = 0
        override var runRangeMaximum: Int = 0
        override var runRangeMinimum: Int = 0
        override var turnsSleep: Int = 0
    }

    private lateinit var run: dqbb.Run<dqbb.RunInvoker, dqbb.RunReceiver>
    private lateinit var runInvoker: dqbb.RunInvoker
    private lateinit var runReceiver: dqbb.RunReceiver

    @BeforeTest()
    fun before() {
        run = dqbb.Run()
        runInvoker = RunInvoker()
        runReceiver = RunReceiver()
    }

    @Test()
    fun testRequirement() {
        runInvoker.agility = 1
        runInvoker.isRunning = false
        runInvoker.runRangeMaximum = 1
        runInvoker.runRangeMinimum = runInvoker.runRangeMaximum
        assertEquals(true, run.use(runInvoker, runReceiver))
    }

    @Test()
    fun testResistance() {
        runReceiver.agility = 1
        runReceiver.runRangeMaximum = 1
        runReceiver.runRangeMinimum = runReceiver.runRangeMaximum
        runReceiver.runShift = 0
        runReceiver.turnsSleep = 0
        assertEquals(false, run.use(runInvoker, runReceiver))
    }
}