import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestHurtRequester {
    private class HurtRequester : dqbb.HurtRequester {
        override var hurtRequirementMaximum: Int = 0
        override var hurtRequirementMinimum: Int = 0
    }

    private lateinit var hurtRequester: dqbb.HurtRequester

    @BeforeTest
    fun before() {
        hurtRequester = HurtRequester()
    }

    @Test
    fun testHurtRequirementMaximum() {
        val hurtRequirementMaximum = (0..100).random()
        hurtRequester.hurtRequirementMaximum = hurtRequirementMaximum
        assertEquals(hurtRequirementMaximum, hurtRequester.hurtRequirementMaximum)
    }

    @Test
    fun testHurtRequirementMinimum() {
        val hurtRequirementMinimum = (0..100).random()
        hurtRequester.hurtRequirementMinimum = hurtRequirementMinimum
        assertEquals(hurtRequirementMinimum, hurtRequester.hurtRequirementMinimum)
    }

    @Test
    fun testHurtRequirementRange() {
        val hurtRequirementMinimum = (1..100).random()
        val hurtRequirementMaximum = ((hurtRequirementMinimum + 1)..(hurtRequirementMinimum * 2)).random()

        hurtRequester.hurtRequirementMaximum = hurtRequirementMaximum
        hurtRequester.hurtRequirementMinimum = hurtRequirementMinimum

        assertContains(hurtRequester.hurtRequirementRange, hurtRequirementMaximum)
        assertContains(hurtRequester.hurtRequirementRange, hurtRequirementMinimum)
        assertEquals(hurtRequirementMinimum, hurtRequester.hurtRequirementRange.first)
        assertEquals(hurtRequirementMaximum, hurtRequester.hurtRequirementRange.last)
    }

    @Test
    fun testHurtRequirement() {
        hurtRequester.hurtRequirementMaximum = 100
        hurtRequester.hurtRequirementMinimum = (0..<hurtRequester.hurtRequirementMaximum).random()
        assertContains(hurtRequester.hurtRequirementRange, hurtRequester.hurtRequirement)
    }
}