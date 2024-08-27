import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestHurt {
    private class HurtInvoker : dqbb.HurtInvoker {
        override var hurtRangeMaximum: Int = 0
        override var hurtRangeMinimum: Int = 0
        override var hurtRequirementMaximum: Int = 0
        override var hurtRequirementMinimum: Int = 0
        override var hurtScale: Int = 0
        override var hurtShift: Int = 0
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

    private lateinit var hurt: dqbb.Hurt<dqbb.HurtInvoker, dqbb.HurtReceiver>
    private lateinit var hurtInvoker: dqbb.HurtInvoker
    private lateinit var hurtReceiver: dqbb.HurtReceiver

    @BeforeTest
    fun before() {
        hurt = dqbb.Hurt(0)
        hurtInvoker = HurtInvoker()
        hurtReceiver = HurtReceiver()
    }

    @Test
    fun test() {
        hurtInvoker.magicPoints = hurt.magicCost
        hurtInvoker.hurtRangeMaximum = 1
        hurtInvoker.hurtRangeMinimum = hurtInvoker.hurtRangeMaximum
        hurtInvoker.hurtRequirementMaximum = 1
        hurtInvoker.hurtRequirementMinimum = hurtInvoker.hurtRequirementMaximum
        hurtInvoker.hurtScale = 0x03
        hurtInvoker.hurtShift = 0x07
        hurtReceiver.hitPoints = 4
        assertEquals(true, hurt.use(hurtInvoker, hurtReceiver))
        assertEquals(0, hurtReceiver.hitPoints)
    }

    @Test
    fun testHurtReduction() {
        hurtInvoker.magicPoints = hurt.magicCost
        hurtInvoker.hurtRangeMaximum = 1
        hurtInvoker.hurtRangeMinimum = hurtInvoker.hurtRangeMaximum
        hurtInvoker.hurtRequirementMaximum = 1
        hurtInvoker.hurtRequirementMinimum = hurtInvoker.hurtRequirementMaximum
        hurtInvoker.hurtScale = 0x03
        hurtInvoker.hurtShift = 0x07
        val armor = dqbb.Armor()
        armor.hurtReduction = 30
        hurtReceiver.armor = armor
        hurtReceiver.hitPoints = 2
        assertEquals(true, hurt.use(hurtInvoker, hurtReceiver))
        assertEquals(0, hurtReceiver.hitPoints)
    }
}