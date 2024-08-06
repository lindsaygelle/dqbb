import dqbb.HurtRequester
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


internal class TestHurtRequester {
    private val hurtRequester: HurtRequester = MockHurtRequester()

    @Test
    fun hurtRequirementMaximum() {
        val hurtRequirementMaximum = (0..1).random()
        hurtRequester.hurtRequirementMaximum = hurtRequirementMaximum
        assertEquals(
            hurtRequirementMaximum, hurtRequester.hurtRequirementMaximum
        )
    }

    @Test
    fun hurtRequirementMinimum() {
        val hurtRequirementMinimum = (0..1).random()
        hurtRequester.hurtRequirementMinimum = hurtRequirementMinimum
        assertEquals(
            hurtRequirementMinimum, hurtRequester.hurtRequirementMinimum
        )
    }

    @Test
    fun hurtRequirementRangeFirst() {
        assertEquals(
            hurtRequester.hurtRequirementMinimum, hurtRequester.hurtRequirementRange.first
        )
    }

    @Test
    fun hurtRequirementRangeLast() {
        assertEquals(
            hurtRequester.hurtRequirementMaximum, hurtRequester.hurtRequirementRange.last
        )
    }

    @Test
    fun hurtRequirement() {
        assertContains(
            hurtRequester.hurtRequirementRange, hurtRequester.hurtRequirement
        )
    }
}