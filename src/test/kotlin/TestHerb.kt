
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestHerb {
    private class HealReceiver : dqbb.HealReceiver {
        override var hitPoints: Int = 0
        override var hitPointsMaximum: Int = 0
    }

    private class HerbInvoker : dqbb.HerbInvoker {
        override var herbRangeMaximum: Int = 0
        override var herbRangeMinimum: Int = 0
        override var herbScale: Int = 0
        override var herbShift: Int = 0
        override val items: MutableMap<dqbb.ItemName, Int> = mutableMapOf()
        override var turnsSleep: Int = 0
    }

    private lateinit var healReceiver: dqbb.HealReceiver
    private lateinit var herb: dqbb.Herb<dqbb.HerbInvoker, dqbb.HealReceiver>
    private lateinit var herbInvoker: dqbb.HerbInvoker


    @BeforeTest
    fun before() {
        healReceiver = HealReceiver()
        herb = dqbb.Herb()
        herbInvoker = HerbInvoker()
    }

    @Test
    fun test() {
        healReceiver.hitPoints = 1
        healReceiver.hitPointsMaximum = 100
        herbInvoker.herbRangeMaximum = 1
        herbInvoker.herbRangeMinimum = herbInvoker.herbRangeMaximum
        herbInvoker.herbScale = 0x17
        herbInvoker.herbShift = 0x0F
        herbInvoker.items[dqbb.ItemName.HERB] = 1
        assertEquals(true, herb.use(herbInvoker, healReceiver))
        assertEquals(25, healReceiver.hitPoints)
    }
}