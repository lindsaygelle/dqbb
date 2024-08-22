import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestSleepRequester {
    private class SleepRequester : dqbb.SleepRequester {
        override var sleepRequirementMaximum: Int = 0
        override var sleepRequirementMinimum: Int = 0
    }

    private lateinit var sleepRequester: dqbb.SleepRequester

    @BeforeTest
    fun before() {
        sleepRequester = SleepRequester()
    }

    @Test
    fun testSleepRequirementMaximum() {
        val sleepRequirementMaximum = (0..100).random()
        sleepRequester.sleepRequirementMaximum = sleepRequirementMaximum
        assertEquals(sleepRequirementMaximum, sleepRequester.sleepRequirementMaximum)
    }

    @Test
    fun testSleepRequirementMinimum() {
        val sleepRequirementMinimum = (0..100).random()
        sleepRequester.sleepRequirementMinimum = sleepRequirementMinimum
        assertEquals(sleepRequirementMinimum, sleepRequester.sleepRequirementMinimum)
    }

    @Test
    fun testSleepRequirementRange() {
        val sleepRequirementMinimum = (1..100).random()
        val sleepRequirementMaximum = ((sleepRequirementMinimum + 1)..(sleepRequirementMinimum * 2)).random()

        sleepRequester.sleepRequirementMaximum = sleepRequirementMaximum
        sleepRequester.sleepRequirementMinimum = sleepRequirementMinimum

        assertContains(sleepRequester.sleepRequirementRange, sleepRequirementMaximum)
        assertContains(sleepRequester.sleepRequirementRange, sleepRequirementMinimum)
        assertEquals(sleepRequirementMinimum, sleepRequester.sleepRequirementRange.first)
        assertEquals(sleepRequirementMaximum, sleepRequester.sleepRequirementRange.last)
    }

    @Test
    fun testSleepRequirement() {
        sleepRequester.sleepRequirementMaximum = 100
        sleepRequester.sleepRequirementMinimum = (0..<sleepRequester.sleepRequirementMaximum).random()
        assertContains(sleepRequester.sleepRequirementRange, sleepRequester.sleepRequirement)
    }
}