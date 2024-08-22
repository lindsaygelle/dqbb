import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestStrengthPointer {
    private class StrengthPointer : dqbb.StrengthPointer {
        override var strength: Int = 0
    }

    private lateinit var strengthAccumulator: dqbb.StrengthPointer

    @BeforeTest
    fun before() {
        strengthAccumulator = StrengthPointer()
    }

    @Test
    fun test() {
        val strength = (0..100).random()
        strengthAccumulator.strength = strength
        assertEquals(strength, strengthAccumulator.strength)
    }
}