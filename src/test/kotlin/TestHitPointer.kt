import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestHitPointer {
    private class HitPointer : dqbb.HitPointer {
        override var hitPoints: Int = 0
    }

    private lateinit var hitPointer: dqbb.HitPointer

    @BeforeTest
    fun before() {
        hitPointer = HitPointer()
    }

    @Test
    fun test() {
        val hitPoints = (0..100).random()
        hitPointer.hitPoints = hitPoints
        assertEquals(hitPoints, hitPointer.hitPoints)
    }
}