import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestAttackPointer {
    private class AttackPointer : dqbb.AttackPointer {
        override var attack: Int = 0
    }

    private lateinit var attackAccumulator: dqbb.AttackPointer

    @BeforeTest
    fun before() {
        attackAccumulator = AttackPointer()
    }

    @Test
    fun test() {
        val attack = (0..100).random()
        attackAccumulator.attack = attack
        assertEquals(attack, attackAccumulator.attack)
    }
}