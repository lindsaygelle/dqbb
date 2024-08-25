import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestExcellentAttacker {
    private class ExcellentAttacker : dqbb.ExcellentAttacker {
        override var excellentAttackRequirementMaximum: Int = 0
        override var excellentAttackRequirementMinimum: Int = 0
    }

    private lateinit var excellentAttacker: dqbb.ExcellentAttacker

    @BeforeTest
    fun before() {
        excellentAttacker = ExcellentAttacker()
    }

    @Test
    fun testExcellentAttackRequirementMaximum() {
        val excellentAttackRequirementMaximum = (0..100).random()
        excellentAttacker.excellentAttackRequirementMaximum = excellentAttackRequirementMaximum
        assertEquals(excellentAttackRequirementMaximum, excellentAttacker.excellentAttackRequirementMaximum)
    }

    @Test
    fun testExcellentAttackRequirementMinimum() {
        val excellentAttackRequirementMinimum = (0..100).random()
        excellentAttacker.excellentAttackRequirementMinimum = excellentAttackRequirementMinimum
        assertEquals(excellentAttackRequirementMinimum, excellentAttacker.excellentAttackRequirementMinimum)
    }

    @Test
    fun testExcellentAttackRequirementRange() {
        val excellentAttackRequirementMinimum = (1..100).random()
        val excellentAttackRequirementMaximum =
            ((excellentAttackRequirementMinimum + 1)..(excellentAttackRequirementMinimum * 2)).random()

        excellentAttacker.excellentAttackRequirementMaximum = excellentAttackRequirementMaximum
        excellentAttacker.excellentAttackRequirementMinimum = excellentAttackRequirementMinimum

        assertContains(excellentAttacker.excellentAttackRequirementRange, excellentAttackRequirementMaximum)
        assertContains(excellentAttacker.excellentAttackRequirementRange, excellentAttackRequirementMinimum)
        assertEquals(excellentAttackRequirementMinimum, excellentAttacker.excellentAttackRequirementRange.first)
        assertEquals(excellentAttackRequirementMaximum, excellentAttacker.excellentAttackRequirementRange.last)
    }

    @Test
    fun testExcellentAttackRequirement() {
        excellentAttacker.excellentAttackRequirementMaximum = 100
        excellentAttacker.excellentAttackRequirementMinimum =
            (0..<excellentAttacker.excellentAttackRequirementMaximum).random()
        assertContains(excellentAttacker.excellentAttackRequirementRange, excellentAttacker.excellentAttackRequirement)
    }
}