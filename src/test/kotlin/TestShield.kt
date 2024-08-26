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
        for (defense in (0..100)) {
            shield.defense = defense
            assertEquals(defense, shield.defense)
        }
    }

    @Test
    fun testDefenseNegative() {
        for (defense in (-100..0)) {
            shield.defense = defense
            assertEquals(0, shield.defense)
        }
    }
}