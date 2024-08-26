import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestShield {
    private lateinit var shield: dqbb.Shield

    @BeforeTest
    fun before() {
        shield = dqbb.Shield()
    }

    @Test
    fun testDefense() {
        val defense = (1..100).random()
        shield.defense = defense
        assertEquals(defense, shield.defense)
    }

    @Test
    fun testDefenseNegative() {
        for (defense in (-100..-1)) {
            shield.defense = defense
            assertEquals(0, shield.defense)
        }
    }
}