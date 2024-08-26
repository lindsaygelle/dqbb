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
        for (blocksSleep in listOf(true, false)) {
            armor.blocksSleep = blocksSleep
            assertEquals(blocksSleep, armor.blocksSleep)
        }
    }

    @Test
    fun testBlocksStopSpell() {
        for (blocksStopSpell in listOf(true, false)) {
            armor.blocksStopSpell = blocksStopSpell
            assertEquals(blocksStopSpell, armor.blocksStopSpell)
        }
    }

    @Test
    fun testBreatheFireReduction() {
        for (breatheFireReduction in (0..100)) {
            armor.breatheFireReduction = breatheFireReduction
            assertEquals(breatheFireReduction, armor.breatheFireReduction)
        }
    }

    @Test
    fun testBreatheFireReductionNegative() {
        for (breatheFireReduction in (-100..0)) {
            armor.breatheFireReduction = breatheFireReduction
            assertEquals(0, armor.breatheFireReduction)
        }
    }

    @Test
    fun testDefense() {
        for (defense in (0..100)) {
            armor.defense = defense
            assertEquals(defense, armor.defense)
        }
    }

    @Test
    fun testDefenseNegative() {
        for (defense in (-100..0)) {
            armor.defense = defense
            assertEquals(0, armor.defense)
        }
    }

    @Test
    fun testHurtReduction() {
        for (hurtReduction in (0..100)) {
            armor.hurtReduction = hurtReduction
            assertEquals(hurtReduction, armor.hurtReduction)
        }
    }

    @Test
    fun testHurtReductionNegative() {
        for (hurtReduction in (-100..0)) {
            armor.hurtReduction = hurtReduction
            assertEquals(0, armor.hurtReduction)
        }
    }
}