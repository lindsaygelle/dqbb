import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestHurtReducer {
    private class HurtReducer : dqbb.HurtReducer {
        override var hurtReduction: Int = 0
    }

    private lateinit var hurtReducer: dqbb.HurtReducer

    @BeforeTest
    fun before() {
        hurtReducer = HurtReducer()
    }

    @Test
    fun test() {
        val hurtReduction = (0..100).random()
        hurtReducer.hurtReduction = hurtReduction
        assertEquals(hurtReduction, hurtReducer.hurtReduction)
    }
}