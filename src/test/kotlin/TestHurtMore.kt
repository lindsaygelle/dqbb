import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestHurtMore {
    private class HurtMoreInvoker : dqbb.HurtMoreInvoker {
        override var hurtMoreScale: Int = 0
        override var hurtMoreShift: Int = 0
        override var hurtRangeMaximum: Int = 0
        override var hurtRangeMinimum: Int = 0
        override var hurtRequirementMaximum: Int = 0
        override var hurtRequirementMinimum: Int = 0
        override var magicPoints: Int = 0
        override var turnsSleep: Int = 0
        override var turnsStopSpell: Int = 0
    }

    private class HurtReceiver : dqbb.HurtReceiver {
        override var armor: dqbb.Armor? = null
        override var hitPoints: Int = 0
        override var hurtResistanceMaximum: Int = 0
        override var hurtResistanceMinimum: Int = 0
    }

    private lateinit var hurtMore: dqbb.HurtMore<dqbb.HurtMoreInvoker, dqbb.HurtReceiver>
    private lateinit var hurtMoreInvoker: dqbb.HurtMoreInvoker
    private lateinit var hurtMoreReceiver: dqbb.HurtReceiver

    @BeforeTest
    fun before() {
        hurtMore = dqbb.HurtMore(0)
        hurtMoreInvoker = HurtMoreInvoker()
        hurtMoreReceiver = HurtReceiver()
    }

    @Test
    fun test() {
        hurtMoreInvoker.magicPoints = hurtMore.magicCost
        hurtMoreInvoker.hurtMoreScale = 0x1E
        hurtMoreInvoker.hurtMoreShift = 0x0F
        hurtMoreInvoker.hurtRangeMaximum = 1
        hurtMoreInvoker.hurtRangeMinimum = hurtMoreInvoker.hurtRangeMaximum
        hurtMoreInvoker.hurtRequirementMaximum = 1
        hurtMoreInvoker.hurtRequirementMinimum = hurtMoreInvoker.hurtRequirementMaximum
        hurtMoreReceiver.hitPoints = 31
        assertEquals(true, hurtMore.use(hurtMoreInvoker, hurtMoreReceiver))
        assertEquals(0, hurtMoreReceiver.hitPoints)
    }

    @Test
    fun testHurtMoreReduction() {
        hurtMoreInvoker.magicPoints = hurtMore.magicCost
        hurtMoreInvoker.hurtMoreScale = 0x1E
        hurtMoreInvoker.hurtMoreShift = 0x0F
        hurtMoreInvoker.hurtRangeMaximum = 1
        hurtMoreInvoker.hurtRangeMinimum = hurtMoreInvoker.hurtRangeMaximum
        hurtMoreInvoker.hurtRequirementMaximum = 1
        hurtMoreInvoker.hurtRequirementMinimum = hurtMoreInvoker.hurtRequirementMaximum
        val armor = dqbb.Armor()
        armor.hurtReduction = 30
        hurtMoreReceiver.armor = armor
        hurtMoreReceiver.hitPoints = 21
        assertEquals(true, hurtMore.use(hurtMoreInvoker, hurtMoreReceiver))
        assertEquals(0, hurtMoreReceiver.hitPoints)
    }
}