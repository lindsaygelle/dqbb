import dqbb.SleepRequester
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


internal class TestSleepRequester {
    private val sleepRequester: SleepRequester = MockSleepRequester()

    @Test
    fun sleepRequirementMaximum() {
        val sleepRequirementMaximum = (0..1).random()
        sleepRequester.sleepRequirementMaximum = sleepRequirementMaximum
        assertEquals(
            sleepRequirementMaximum, sleepRequester.sleepRequirementMaximum
        )
    }

    @Test
    fun sleepRequirementMinimum() {
        val sleepRequirementMinimum = (0..1).random()
        sleepRequester.sleepRequirementMinimum = sleepRequirementMinimum
        assertEquals(
            sleepRequirementMinimum, sleepRequester.sleepRequirementMinimum
        )
    }

    @Test
    fun sleepRequirementRangeFirst() {
        assertEquals(
            sleepRequester.sleepRequirementMinimum, sleepRequester.sleepRequirementRange.first
        )
    }

    @Test
    fun sleepRequirementRangeLast() {
        assertEquals(
            sleepRequester.sleepRequirementMaximum, sleepRequester.sleepRequirementRange.last
        )
    }

    @Test
    fun sleepRequirement() {
        assertContains(
            sleepRequester.sleepRequirementRange, sleepRequester.sleepRequirement
        )
    }
}