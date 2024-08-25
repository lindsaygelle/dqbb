import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestStopSpellRequester {
    private class StopSpellRequester : dqbb.StopSpellRequester {
        override var stopSpellRequirementMaximum: Int = 0
        override var stopSpellRequirementMinimum: Int = 0
    }

    private lateinit var stopSpellRequester: dqbb.StopSpellRequester

    @BeforeTest
    fun before() {
        stopSpellRequester = StopSpellRequester()
    }

    @Test
    fun testStopSpellRequirementMaximum() {
        val stopSpellRequirementMaximum = (0..100).random()
        stopSpellRequester.stopSpellRequirementMaximum = stopSpellRequirementMaximum
        assertEquals(stopSpellRequirementMaximum, stopSpellRequester.stopSpellRequirementMaximum)
    }

    @Test
    fun testStopSpellRequirementMinimum() {
        val stopSpellRequirementMinimum = (0..100).random()
        stopSpellRequester.stopSpellRequirementMinimum = stopSpellRequirementMinimum
        assertEquals(stopSpellRequirementMinimum, stopSpellRequester.stopSpellRequirementMinimum)
    }

    @Test
    fun testStopSpellRequirementRange() {
        val stopSpellRequirementMinimum = (1..100).random()
        val stopSpellRequirementMaximum = ((stopSpellRequirementMinimum + 1)..(stopSpellRequirementMinimum * 2)).random()

        stopSpellRequester.stopSpellRequirementMaximum = stopSpellRequirementMaximum
        stopSpellRequester.stopSpellRequirementMinimum = stopSpellRequirementMinimum

        assertContains(stopSpellRequester.stopSpellRequirementRange, stopSpellRequirementMaximum)
        assertContains(stopSpellRequester.stopSpellRequirementRange, stopSpellRequirementMinimum)
        assertEquals(stopSpellRequirementMinimum, stopSpellRequester.stopSpellRequirementRange.first)
        assertEquals(stopSpellRequirementMaximum, stopSpellRequester.stopSpellRequirementRange.last)
    }

    @Test
    fun testStopSpellRequirement() {
        stopSpellRequester.stopSpellRequirementMaximum = 100
        stopSpellRequester.stopSpellRequirementMinimum = (0..<stopSpellRequester.stopSpellRequirementMaximum).random()
        assertContains(stopSpellRequester.stopSpellRequirementRange, stopSpellRequester.stopSpellRequirement)
    }
}