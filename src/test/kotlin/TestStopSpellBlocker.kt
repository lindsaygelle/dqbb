import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestStopSpellBlocker {
    private class StopSpellBlocker : dqbb.StopSpellBlocker {
        override var blocksStopSpell: Boolean = false
    }

    private lateinit var stopSpellBlocker: dqbb.StopSpellBlocker

    @BeforeTest
    fun before() {
        stopSpellBlocker = StopSpellBlocker()
    }

    @Test
    fun test() {
        for (blocksStopSpell in listOf(true, false)) {
            stopSpellBlocker.blocksStopSpell = blocksStopSpell
            assertEquals(blocksStopSpell, stopSpellBlocker.blocksStopSpell)
        }
    }
}