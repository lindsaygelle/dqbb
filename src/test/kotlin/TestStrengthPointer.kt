import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestStrengthPointer {
    private class StrengthPointer : dqbb.StrengthPointer {
        override var strength: Int = 0
    }

    private lateinit var strengthPointer: dqbb.StrengthPointer

    @BeforeTest
    fun before() {
        strengthPointer = StrengthPointer()
    }

    @Test
    fun test() {
        for (strength in (0..100)) {
            strengthPointer.strength = strength
            assertEquals(strength, strengthPointer.strength)
        }
    }
}