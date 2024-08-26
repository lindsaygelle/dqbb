import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestAbilityMagic {
    private class AbilityMagic(magicCost: Int) : dqbb.AbilityMagic<dqbb.MagicInvoker, dqbb.Receiver>(magicCost) {
        override fun apply(invoker: dqbb.MagicInvoker, receiver: dqbb.Receiver): Boolean {
            return true
        }

        override fun checkReceiver(receiver: dqbb.Receiver): Boolean {
            return true
        }
    }

    private class MagicInvoker : dqbb.MagicInvoker {
        override var magicPoints: Int = 0
        override var turnsSleep: Int = 0
        override var turnsStopSpell: Int = 0
    }

    private class Receiver : dqbb.Receiver

    private lateinit var abilityMagic: dqbb.AbilityMagic<dqbb.MagicInvoker, dqbb.Receiver>
    private lateinit var magicInvoker: dqbb.MagicInvoker
    private lateinit var receiver: dqbb.Receiver

    @BeforeTest
    fun before() {
        abilityMagic = AbilityMagic((1..100).random())
        magicInvoker = MagicInvoker()
        receiver = Receiver()
    }

    @Test
    fun testTurnsSleep() {
        magicInvoker.turnsSleep = 1
        assertEquals(false, abilityMagic.use(magicInvoker, receiver))
    }

    @Test
    fun testTurnsStopSpell() {
        magicInvoker.turnsStopSpell = 1
        assertEquals(false, abilityMagic.use(magicInvoker, receiver))
    }

    @Test
    fun testMagicCost() {
        magicInvoker.magicPoints = abilityMagic.magicCost - 1
        assertEquals(false, abilityMagic.use(magicInvoker, receiver))
    }

    @Test
    fun testReduceMagicPoints() {
        magicInvoker.magicPoints = abilityMagic.magicCost
        abilityMagic.use(magicInvoker, receiver)
        assertEquals(0, magicInvoker.magicPoints)
    }
}