import dqbb.StopSpellRequester
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


internal class TestStopSpellRequester {
    private lateinit var stopSpellRequester: StopSpellRequester

    @BeforeTest
    fun createStopSpellRequester() {
        stopSpellRequester = MockStopSpellRequester()
    }

    @Test
    fun stopSpellRequirementMaximum() {
        val stopSpellRequirementMaximum = (0..1).random()
        stopSpellRequester.stopSpellRequirementMaximum = stopSpellRequirementMaximum
        assertEquals(
            stopSpellRequirementMaximum, stopSpellRequester.stopSpellRequirementMaximum
        )
    }

    @Test
    fun stopSpellRequirementMinimum() {
        val stopSpellRequirementMinimum = (0..1).random()
        stopSpellRequester.stopSpellRequirementMinimum = stopSpellRequirementMinimum
        assertEquals(
            stopSpellRequirementMinimum, stopSpellRequester.stopSpellRequirementMinimum
        )
    }

    @Test
    fun stopSpellRequirementRangeFirst() {
        stopSpellRequester.stopSpellRequirementMinimum = (0..10).random()
        assertEquals(
            stopSpellRequester.stopSpellRequirementMinimum, stopSpellRequester.stopSpellRequirementRange.first
        )
    }

    @Test
    fun stopSpellRequirementRangeLast() {
        stopSpellRequester.stopSpellRequirementMaximum = (0..10).random()
        assertEquals(
            stopSpellRequester.stopSpellRequirementMaximum, stopSpellRequester.stopSpellRequirementRange.last
        )
    }

    @Test
    fun stopSpellRequirement() {
        stopSpellRequester.stopSpellRequirementMaximum = (10..20).random()
        stopSpellRequester.stopSpellRequirementMinimum = (0..stopSpellRequester.stopSpellRequirementMaximum).random()
        assertContains(
            stopSpellRequester.stopSpellRequirementRange, stopSpellRequester.stopSpellRequirement
        )
    }
}