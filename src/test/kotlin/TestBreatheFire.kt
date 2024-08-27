import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestBreatheFire {
    private class BreatheFireInvoker : dqbb.BreatheFireInvoker {
        override var breatheFireRangeMaximum: Int = 0
        override var breatheFireRangeMinimum: Int = 0
        override var breatheFireScale: Int = 0
        override var breatheFireShift: Int = 0
        override var magicPoints: Int = 0
        override var turnsSleep: Int = 0
        override var turnsStopSpell: Int = 0
    }

    private class BreatheFireReceiver : dqbb.BreatheFireReceiver {
        override var armor: dqbb.Armor? = null
        override var hitPoints: Int = 0
    }

    private lateinit var breatheFire: dqbb.BreatheFire<dqbb.BreatheFireInvoker, dqbb.BreatheFireReceiver>
    private lateinit var breatheFireInvoker: dqbb.BreatheFireInvoker
    private lateinit var breatheFireReceiver: dqbb.BreatheFireReceiver

    @BeforeTest
    fun before() {
        breatheFire = dqbb.BreatheFire(0)
        breatheFireInvoker = BreatheFireInvoker()
        breatheFireReceiver = BreatheFireReceiver()
    }

    @Test
    fun test() {
        breatheFireInvoker.magicPoints = breatheFire.magicCost
        breatheFireInvoker.breatheFireRangeMaximum = 1
        breatheFireInvoker.breatheFireRangeMinimum = breatheFireInvoker.breatheFireRangeMaximum
        breatheFireInvoker.breatheFireScale = 0x10
        breatheFireInvoker.breatheFireShift = 0x07
        breatheFireReceiver.hitPoints = 17
        assertEquals(true, breatheFire.use(breatheFireInvoker, breatheFireReceiver))
        assertEquals(0, breatheFireReceiver.hitPoints)
    }

    @Test
    fun testBreatheFireReduction() {
        breatheFireInvoker.magicPoints = breatheFire.magicCost
        breatheFireInvoker.breatheFireRangeMaximum = 1
        breatheFireInvoker.breatheFireRangeMinimum = breatheFireInvoker.breatheFireRangeMaximum
        breatheFireInvoker.breatheFireScale = 0x10
        breatheFireInvoker.breatheFireShift = 0x07
        val armor = dqbb.Armor()
        armor.breatheFireReduction = 30
        breatheFireReceiver.armor = armor
        breatheFireReceiver.hitPoints = 17
        assertEquals(true, breatheFire.use(breatheFireInvoker, breatheFireReceiver))
        assertEquals(6, breatheFireReceiver.hitPoints)
    }
}