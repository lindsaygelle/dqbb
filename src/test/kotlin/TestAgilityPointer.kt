import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestAgilityPointer {
    private class AgilityPointer : dqbb.AgilityPointer {
        override var agility: Int = 0
    }

    private lateinit var agilityAccumulator: dqbb.AgilityPointer

    @BeforeTest
    fun before() {
        agilityAccumulator = AgilityPointer()
    }

    @Test
    fun test() {
        for (agility in (0..100)) {
            agilityAccumulator.agility = agility
            assertEquals(agility, agilityAccumulator.agility)
        }
    }
}