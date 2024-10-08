import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestTurnAccumulator {
    private class TurnAccumulator : dqbb.TurnAccumulator {
        override var turn: Int = 0
    }

    private lateinit var turnAccumulator: dqbb.TurnAccumulator

    @BeforeTest
    fun before() {
        turnAccumulator = TurnAccumulator()
    }

    @Test
    fun test() {
        for (turn in (0..100)) {
            turnAccumulator.turn = turn
            assertEquals(turn, turnAccumulator.turn)
        }
    }
}