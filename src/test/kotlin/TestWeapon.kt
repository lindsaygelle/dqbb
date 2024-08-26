import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestWeapon {
    private lateinit var weapon: dqbb.Weapon

    @BeforeTest
    fun before() {
        weapon = dqbb.Weapon()
    }

    @Test
    fun testAttack() {
        val attack = (1..100).random()
        weapon.attack = attack
        assertEquals(attack, weapon.attack)
    }

    @Test
    fun testAttackNegative() {
        for (attack in (-100..-1)) {
            weapon.attack = attack
            assertEquals(0, weapon.attack)
        }
    }
}