import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestHurtResister {
    private class HurtResister : dqbb.HurtResister {
        override var hurtResistanceMaximum: Int = 0
        override var hurtResistanceMinimum: Int = 0
    }

    private lateinit var hurtResister: dqbb.HurtResister

    @BeforeTest
    fun before() {
        hurtResister = HurtResister()
    }

    @Test
    fun testHurtResistanceMaximum() {
        val hurtResistanceMaximum = (0..100).random()
        hurtResister.hurtResistanceMaximum = hurtResistanceMaximum
        assertEquals(hurtResistanceMaximum, hurtResister.hurtResistanceMaximum)
    }

    @Test
    fun testHurtResistanceMinimum() {
        val hurtResistanceMinimum = (0..100).random()
        hurtResister.hurtResistanceMinimum = hurtResistanceMinimum
        assertEquals(hurtResistanceMinimum, hurtResister.hurtResistanceMinimum)
    }

    @Test
    fun testHurtResistanceRange() {
        val hurtResistanceMinimum = (1..100).random()
        val hurtResistanceMaximum = ((hurtResistanceMinimum + 1)..(hurtResistanceMinimum * 2)).random()

        hurtResister.hurtResistanceMaximum = hurtResistanceMaximum
        hurtResister.hurtResistanceMinimum = hurtResistanceMinimum

        assertContains(hurtResister.hurtResistanceRange, hurtResistanceMaximum)
        assertContains(hurtResister.hurtResistanceRange, hurtResistanceMinimum)
        assertEquals(hurtResistanceMinimum, hurtResister.hurtResistanceRange.first)
        assertEquals(hurtResistanceMaximum, hurtResister.hurtResistanceRange.last)
    }

    @Test
    fun testHurtResistance() {
        hurtResister.hurtResistanceMaximum = 100
        hurtResister.hurtResistanceMinimum = (0..<hurtResister.hurtResistanceMaximum).random()
        assertContains(hurtResister.hurtResistanceRange, hurtResister.hurtResistance)
    }
}