import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestDefensePointer {
    private class DefensePointer : dqbb.DefensePointer {
        override var defense: Int = 0
    }

    private lateinit var defensePointer: dqbb.DefensePointer

    @BeforeTest
    fun before() {
        defensePointer = DefensePointer()
    }

    @Test
    fun test() {
        val defense = (0..100).random()
        defensePointer.defense = defense
        assertEquals(defense, defensePointer.defense)
    }
}