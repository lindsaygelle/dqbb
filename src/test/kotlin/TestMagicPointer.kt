import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestMagicPointer {
    private class MagicPointer : dqbb.MagicPointer {
        override var magicPoints: Int = 0
    }

    private lateinit var magicPointer: dqbb.MagicPointer

    @BeforeTest
    fun before() {
        magicPointer = MagicPointer()
    }

    @Test
    fun test() {
        for (magicPoints in (0..100)) {
            magicPointer.magicPoints = magicPoints
            assertEquals(magicPoints, magicPointer.magicPoints)
        }
    }
}