import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestSleep {
    private class SleepInvoker : dqbb.SleepInvoker {
        override var magicPoints: Int = 0
        override var turnsSleep: Int = 0
        override var turnsStopSpell: Int = 0
        override var sleepRequirementMaximum: Int = 0
        override var sleepRequirementMinimum: Int = 0
    }

    private class SleepReceiver : dqbb.SleepReceiver {
        override var armor: dqbb.Armor? = null
        override var hitPoints: Int = 0
        override var sleepResistanceMaximum: Int = 0
        override var sleepResistanceMinimum: Int = 0
        override var turnsSleep: Int = 0
    }

    private lateinit var sleep: dqbb.Sleep<dqbb.SleepInvoker, dqbb.SleepReceiver>
    private lateinit var sleepInvoker: dqbb.SleepInvoker
    private lateinit var sleepReceiver: dqbb.SleepReceiver

    @BeforeTest
    fun before() {
        sleep = dqbb.Sleep(0)
        sleepInvoker = SleepInvoker()
        sleepReceiver = SleepReceiver()
    }

    @Test
    fun testRequirement() {
        sleepInvoker.magicPoints = sleep.magicCost
        sleepInvoker.sleepRequirementMaximum = 1
        sleepInvoker.sleepRequirementMinimum = sleepInvoker.sleepRequirementMaximum
        sleepReceiver.hitPoints = 1
        assertEquals(true, sleep.use(sleepInvoker, sleepReceiver))
    }

    @Test
    fun testResistance() {
        sleepInvoker.magicPoints = sleep.magicCost
        sleepReceiver.hitPoints = 1
        sleepReceiver.sleepResistanceMaximum = 1
        sleepReceiver.sleepResistanceMinimum = sleepReceiver.sleepResistanceMaximum
        assertEquals(false, sleep.use(sleepInvoker, sleepReceiver))
    }

    @Test
    fun testReceiverArmor() {
        sleepInvoker.magicPoints = sleep.magicCost
        sleepInvoker.sleepRequirementMaximum = 1
        sleepInvoker.sleepRequirementMinimum = sleepInvoker.sleepRequirementMaximum
        val armor = dqbb.Armor()
        armor.blocksSleep = true
        sleepReceiver.armor = armor
        sleepReceiver.hitPoints = 1
        assertEquals(false, sleep.use(sleepInvoker, sleepReceiver))
    }
}