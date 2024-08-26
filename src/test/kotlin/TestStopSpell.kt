import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestStopSpell {
    private class StopSpellInvoker : dqbb.StopSpellInvoker {
        override var magicPoints: Int = 0
        override var turnsSleep: Int = 0
        override var turnsStopSpell: Int = 0
        override var stopSpellRequirementMaximum: Int = 0
        override var stopSpellRequirementMinimum: Int = 0
    }

    private class StopSpellReceiver : dqbb.StopSpellReceiver {
        override var armor: dqbb.Armor? = null
        override var hitPoints: Int = 0
        override var stopSpellResistanceMaximum: Int = 0
        override var stopSpellResistanceMinimum: Int = 0
        override var turnsStopSpell: Int = 0
    }

    private lateinit var stopSpell: dqbb.StopSpell<dqbb.StopSpellInvoker, dqbb.StopSpellReceiver>
    private lateinit var stopSpellInvoker: dqbb.StopSpellInvoker
    private lateinit var stopSpellReceiver: dqbb.StopSpellReceiver

    @BeforeTest
    fun before() {
        stopSpell = dqbb.StopSpell(0)
        stopSpellInvoker = StopSpellInvoker()
        stopSpellReceiver = StopSpellReceiver()
    }

    @Test
    fun testRequirement() {
        stopSpellInvoker.magicPoints = stopSpell.magicCost
        stopSpellInvoker.stopSpellRequirementMaximum = 1
        stopSpellInvoker.stopSpellRequirementMinimum = stopSpellInvoker.stopSpellRequirementMaximum
        stopSpellReceiver.hitPoints = 1
        assertEquals(true, stopSpell.use(stopSpellInvoker, stopSpellReceiver))
    }

    @Test
    fun testResistance() {
        stopSpellInvoker.magicPoints = stopSpell.magicCost
        stopSpellReceiver.hitPoints = 1
        stopSpellReceiver.stopSpellResistanceMaximum = 1
        stopSpellReceiver.stopSpellResistanceMinimum = stopSpellReceiver.stopSpellResistanceMaximum
        assertEquals(false, stopSpell.use(stopSpellInvoker, stopSpellReceiver))
    }

    @Test
    fun testReceiverArmor() {
        stopSpellInvoker.magicPoints = stopSpell.magicCost
        stopSpellInvoker.stopSpellRequirementMaximum = 1
        stopSpellInvoker.stopSpellRequirementMinimum = stopSpellInvoker.stopSpellRequirementMaximum
        val armor = dqbb.Armor()
        armor.blocksStopSpell = true
        stopSpellReceiver.armor = armor
        stopSpellReceiver.hitPoints = 1
        assertEquals(false, stopSpell.use(stopSpellInvoker, stopSpellReceiver))
    }
}