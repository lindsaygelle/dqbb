import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestRequirementMagic {
    private class RequirementMagic(
        magicCost: Int,
    ) : dqbb.RequirementMagic<dqbb.MagicInvoker, dqbb.Receiver>(
        magicCost
    ) {
        var requirement: Int = 0
        var resistance: Int = 0

        override fun applyEffect(invoker: dqbb.MagicInvoker, receiver: dqbb.Receiver): Boolean {
            return true
        }

        override fun checkReceiver(receiver: dqbb.Receiver): Boolean {
            return true
        }

        override fun getRequirement(invoker: dqbb.MagicInvoker): Int {
            return requirement
        }

        override fun getResistance(receiver: dqbb.Receiver): Int {
            return resistance
        }
    }

    private class MagicInvoker : dqbb.MagicInvoker {
        override var magicPoints: Int = 0
        override var turnsSleep: Int = 0
        override var turnsStopSpell: Int = 0
    }

    private class Receiver : dqbb.Receiver

    private lateinit var magicInvoker: dqbb.MagicInvoker
    private lateinit var receiver: dqbb.Receiver
    private lateinit var requirementMagic: RequirementMagic

    @BeforeTest
    fun before() {
        magicInvoker = MagicInvoker()
        receiver = Receiver()
        requirementMagic = RequirementMagic((1..100).random())
    }


    @Test
    fun testRequirement() {
        requirementMagic.requirement = 1
        magicInvoker.magicPoints = requirementMagic.magicCost
        assertEquals(true, requirementMagic.use(magicInvoker, receiver))
    }

    @Test
    fun testResistance() {
        requirementMagic.resistance = 1
        magicInvoker.magicPoints = requirementMagic.magicCost
        assertEquals(false, requirementMagic.use(magicInvoker, receiver))
    }
}