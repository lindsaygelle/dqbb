import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestBreatheFireReducer {
    private class BreatheFireReducer : dqbb.BreatheFireReducer {
        override var breatheFireReduction: Int = 0
    }

    private lateinit var breatheFireReducer: dqbb.BreatheFireReducer

    @BeforeTest
    fun before() {
        breatheFireReducer = BreatheFireReducer()
    }

    @Test
    fun test() {
        for (breatheFireReduction in (0..100)) {
            breatheFireReducer.breatheFireReduction = breatheFireReduction
            assertEquals(breatheFireReduction, breatheFireReducer.breatheFireReduction)
        }
    }
}