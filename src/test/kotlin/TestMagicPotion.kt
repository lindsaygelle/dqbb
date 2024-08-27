import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestMagicPotion {
    private class MagicPotionInvoker : dqbb.MagicPotionInvoker {
        override val items: MutableMap<dqbb.ItemName, Int> = mutableMapOf()
        override var magicPotionRangeMaximum: Int = 0
        override var magicPotionRangeMinimum: Int = 0
        override var magicPotionScale: Int = 0
        override var magicPotionShift: Int = 0
        override var turnsSleep: Int = 0
    }

    private class MagicReceiver : dqbb.MagicReceiver {
        override var hitPoints: Int = 0
        override var magicPoints: Int = 0
        override var magicPointsMaximum: Int = 0
    }

    private lateinit var magicPotion: dqbb.MagicPotion<dqbb.MagicPotionInvoker, dqbb.MagicReceiver>
    private lateinit var magicPotionInvoker: dqbb.MagicPotionInvoker
    private lateinit var magicReceiver: dqbb.MagicReceiver

    @BeforeTest
    fun before() {
        magicPotion = dqbb.MagicPotion()
        magicPotionInvoker = MagicPotionInvoker()
        magicReceiver = MagicReceiver()
    }

    @Test
    fun test() {
        magicPotionInvoker.magicPotionRangeMaximum = 1
        magicPotionInvoker.magicPotionRangeMinimum = magicPotionInvoker.magicPotionRangeMaximum
        magicPotionInvoker.magicPotionScale = 0x17
        magicPotionInvoker.magicPotionShift = 0x0F
        magicPotionInvoker.items[dqbb.ItemName.MAGIC_POTION] = 1
        magicReceiver.hitPoints = 1
        magicReceiver.magicPoints = 1
        magicReceiver.magicPointsMaximum = 100
        assertEquals(true, magicPotion.use(magicPotionInvoker, magicReceiver))
        assertEquals(25, magicReceiver.magicPoints)
    }
}