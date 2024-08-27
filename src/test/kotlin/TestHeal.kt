import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestHeal {
    private class HealReceiver : dqbb.HealReceiver {
        override var hitPoints: Int = 0
        override var hitPointsMaximum: Int = 0
    }

    private class HealInvoker : dqbb.HealInvoker {
        override var healRangeMaximum: Int = 0
        override var healRangeMinimum: Int = 0
        override var healScale: Int = 0
        override var healShift: Int = 0
        override var magicPoints: Int = 0
        override var turnsSleep: Int = 0
        override var turnsStopSpell: Int = 0
    }

    private lateinit var healReceiver: dqbb.HealReceiver
    private lateinit var heal: dqbb.Heal<dqbb.HealInvoker, dqbb.HealReceiver>
    private lateinit var healInvoker: dqbb.HealInvoker


    @BeforeTest
    fun before() {
        heal = dqbb.Heal(0)
        healInvoker = HealInvoker()
        healReceiver = HealReceiver()
    }

    @Test
    fun test() {
        healInvoker.healRangeMaximum = 1
        healInvoker.healRangeMinimum = healInvoker.healRangeMaximum
        healInvoker.healScale = 0x0A // 10
        healInvoker.healShift = 0x07 // 7
        healReceiver.hitPoints = 1
        healReceiver.hitPointsMaximum = 100
        assertEquals(true, heal.use(healInvoker, healReceiver))
        assertEquals(12, healReceiver.hitPoints)
    }
}