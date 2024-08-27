import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestHealMore {
    private class HealMoreInvoker : dqbb.HealMoreInvoker {
        override var healMoreScale: Int = 0
        override var healMoreShift: Int = 0
        override var healRangeMaximum: Int = 0
        override var healRangeMinimum: Int = 0
        override var magicPoints: Int = 0
        override var turnsSleep: Int = 0
        override var turnsStopSpell: Int = 0
    }

    private class HealReceiver : dqbb.HealReceiver {
        override var hitPoints: Int = 0
        override var hitPointsMaximum: Int = 0
    }

    private lateinit var healReceiver: dqbb.HealReceiver
    private lateinit var heal: dqbb.HealMore<dqbb.HealMoreInvoker, dqbb.HealReceiver>
    private lateinit var healInvoker: dqbb.HealMoreInvoker


    @BeforeTest
    fun before() {
        heal = dqbb.HealMore(0)
        healInvoker = HealMoreInvoker()
        healReceiver = HealReceiver()
    }

    @Test
    fun test() {
        healInvoker.healMoreScale = 0x55 // 10
        healInvoker.healMoreShift = 0x0F // 7
        healInvoker.healRangeMaximum = 1
        healInvoker.healRangeMinimum = healInvoker.healRangeMaximum
        healReceiver.hitPoints = 1
        healReceiver.hitPointsMaximum = 100
        assertEquals(true, heal.use(healInvoker, healReceiver))
        assertEquals(87, healReceiver.hitPoints)
    }
}