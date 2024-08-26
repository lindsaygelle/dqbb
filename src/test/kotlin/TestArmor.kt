import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestArmor {
    private lateinit var armor: dqbb.Armor

    @BeforeTest
    fun before() {
        armor = dqbb.Armor()
    }

    @Test
    fun testBlocksSleep() {
        val blocksSleep = listOf(true, false).random()
        armor.blocksSleep = blocksSleep
        assertEquals(blocksSleep, armor.blocksSleep)
    }

    @Test
    fun testBlocksStopSpell() {
        val blocksStopSpell = listOf(true, false).random()
        armor.blocksStopSpell = blocksStopSpell
        assertEquals(blocksStopSpell, armor.blocksStopSpell)
    }

    @Test
    fun testBreatheFireReduction() {
        val breatheFireReduction = (1..100).random()
        armor.breatheFireReduction = breatheFireReduction
        assertEquals(breatheFireReduction, armor.breatheFireReduction)
    }

    @Test
    fun testBreatheFireReductionNegative() {
        val breatheFireReduction = (-100..-1).random()
        armor.breatheFireReduction = breatheFireReduction
        assertEquals(0, armor.breatheFireReduction)
    }

    @Test
    fun testDefense() {
        val defense = (1..100).random()
        armor.defense = defense
        assertEquals(defense, armor.defense)
    }

    @Test
    fun testDefenseNegative() {
        val defense = (-100..-1).random()
        armor.defense = defense
        assertEquals(0, armor.defense)
    }

    @Test
    fun testHurtReduction() {
        val hurtReduction = (1..100).random()
        armor.hurtReduction = hurtReduction
        assertEquals(hurtReduction, armor.hurtReduction)
    }

    @Test
    fun testHurtReductionNegative() {
        val hurtReduction = (-100..-1).random()
        armor.hurtReduction = hurtReduction
        assertEquals(0, armor.hurtReduction)
    }
}