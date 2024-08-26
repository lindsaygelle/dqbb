import dqbb.ItemName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestAbilityItem {
    private class AbilityItem(itemName: dqbb.ItemName) : dqbb.AbilityItem<dqbb.ItemInvoker, dqbb.Receiver>(itemName) {
        override fun apply(invoker: dqbb.ItemInvoker, receiver: dqbb.Receiver): Boolean {
            return true
        }

        override fun checkReceiver(receiver: dqbb.Receiver): Boolean {
            return true
        }
    }

    private class ItemInvoker : dqbb.ItemInvoker {
        override val items: MutableMap<ItemName, Int> = mutableMapOf()
        override var turnsSleep: Int = 0
    }

    private class Receiver : dqbb.Receiver

    private lateinit var abilityItem: dqbb.AbilityItem<dqbb.ItemInvoker, dqbb.Receiver>
    private lateinit var itemInvoker: dqbb.ItemInvoker
    private lateinit var receiver: dqbb.Receiver

    @BeforeTest
    fun before() {
        abilityItem = AbilityItem(dqbb.ItemName.entries.random())
        itemInvoker = ItemInvoker()
        receiver = Receiver()
    }

    @Test
    fun testTurnsSleep() {
        itemInvoker.turnsSleep = 1
        assertEquals(false, abilityItem.use(itemInvoker, receiver))
    }

    @Test
    fun testItemExists() {
        assertEquals(false, abilityItem.use(itemInvoker, receiver))
    }
    
    @Test
    fun testItemCount() {
        itemInvoker.items[abilityItem.itemName] = 0
        assertEquals(false, abilityItem.use(itemInvoker, receiver))
    }

    @Test
    fun testReduceItemCount() {
        itemInvoker.items[abilityItem.itemName] = 1
        abilityItem.use(itemInvoker, receiver)
        assertEquals(0, itemInvoker.items[abilityItem.itemName])
    }
}