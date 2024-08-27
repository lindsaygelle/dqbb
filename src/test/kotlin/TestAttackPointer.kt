import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestAttackPointer {
    private class AttackPointer : dqbb.AttackPointer {
        override var attack: Int = 0
    }

    private lateinit var attackPointer: dqbb.AttackPointer

    @BeforeTest
    fun before() {
        attackPointer = AttackPointer()
    }

    @Test
    fun test() {
        for (attack in (0..100)) {
            attackPointer.attack = attack
            assertEquals(attack, attackPointer.attack)
        }
    }
}