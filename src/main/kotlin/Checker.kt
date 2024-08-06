package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Checker(
    private val checkType: CheckType,
    private val operatorType: OperatorType,
    private val value: Int,
) {
    private var iterations: Int = 0
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun check(character: Character): Boolean {
        val valueOther = getValue(character)
        logger.debug(
            "character.hashCode=${character.hashCode()} " +
            "checkType=$checkType " +
            "iterations=$iterations " +
            "operatorType=$operatorType " +
            "value=$value " +
            "valueOther=$valueOther"
        )
        iterations += 1
        val checkValue = checkValue(valueOther)
        logger.info("checkValue=$checkValue iterations=$iterations")
        return checkValue
    }

    private fun checkValue(valueOther: Int): Boolean {
        return when (operatorType) {
            OperatorType.EQUAL -> valueOther == value
            OperatorType.GREATER_THAN -> valueOther > value
            OperatorType.GREATER_THAN_EQUAL -> valueOther >= value
            OperatorType.LESS_THAN -> valueOther < value
            OperatorType.LESS_THAN_EQUAL -> valueOther <= value
            OperatorType.NOT -> valueOther != value
        }
    }

    private fun getValue(character: Character): Int {
        return when (checkType) {
            CheckType.AGILITY -> character.agility
            CheckType.ARMOR_DEFENSE -> (character.armor?.defense ?: 0)
            CheckType.BREATHE_FIRE_RANGE_MAXIMUM -> character.breatheFireRangeMaximum
            CheckType.BREATHE_FIRE_RANGE_MINIMUM -> character.breatheFireRangeMinimum
            CheckType.BREATHE_FIRE_SCALE -> character.breatheFireScale
            CheckType.BREATHE_FIRE_SHIFT -> character.breatheFireShift
            CheckType.DAMAGE_RESISTANCE -> character.damageResistance
            CheckType.DEFENSE -> character.defense
            CheckType.HEAL_MORE_SCALE -> character.healMoreScale
            CheckType.HEAL_MORE_SHIFT -> character.healMoreShift
            CheckType.HEAL_RANGE_MAXIMUM -> character.healRangeMaximum
            CheckType.HEAL_RANGE_MINIMUM -> character.hurtRangeMinimum
            CheckType.HEAL_SCALE -> character.healScale
            CheckType.HEAL_SHIFT -> character.healShift
            CheckType.HERB_SCALE -> character.herbScale
            CheckType.HERB_SHIFT -> character.healShift
            CheckType.HIT_POINTS -> character.hitPoints
            CheckType.HIT_POINTS_MAXIMUM -> character.hitPointsMaximum
            CheckType.HIT_POINTS_PERCENTAGE -> character.hitPointsPercentage
            CheckType.HURT_MORE_SCALE -> character.hurtMoreScale
            CheckType.HURT_MORE_SHIFT -> character.hurtMoreShift
            CheckType.HURT_RANGE_MAXIMUM -> character.hurtRangeMaximum
            CheckType.HURT_RANGE_MINIMUM -> character.hurtRangeMinimum
            CheckType.HURT_REQUIREMENT_MAXIMUM -> character.hurtRequirementMaximum
            CheckType.HURT_REQUIREMENT_MINIMUM -> character.hurtRequirementMinimum
            CheckType.HURT_SCALE -> character.hurtScale
            CheckType.HURT_SHIFT -> character.hurtShift
            CheckType.MAGIC_POINTS -> character.magicPoints
            CheckType.MAGIC_POINTS_MAXIMUM -> character.magicPointsMaximum
            CheckType.MAGIC_POINTS_PERCENTAGE -> character.magicPointsPercentage
            CheckType.SHIELD_DEFENSE -> (character.shield?.defense ?: 0)
            CheckType.SLEEP_REQUIREMENT_MAXIMUM -> character.sleepRequirementMaximum
            CheckType.SLEEP_REQUIREMENT_MINIMUM -> character.sleepRequirementMinimum
            CheckType.STATUS_RESISTANCE -> character.statusResistance
            CheckType.STOP_SPELL_REQUIREMENT_MAXIMUM -> character.stopSpellRequirementMaximum
            CheckType.STOP_SPELL_REQUIREMENT_MINIMUM -> character.stopSpellRequirementMinimum
            CheckType.STRENGTH -> character.strength
            CheckType.TURNS_SLEEP -> character.turnsSleep
            CheckType.TURNS_SLEEP_MAXIMUM -> character.turnsSleepMaximum
            CheckType.TURNS_SLEEP_MINIMUM -> character.turnsSleepMinimum
            CheckType.TURNS_SLEEP_PERCENTAGE -> character.turnsSleepPercentage
            CheckType.TURNS_STOP_SPELL -> character.turnsStopSpell
            CheckType.TURNS_STOP_SPELL_MAXIMUM -> character.turnsStopSpellMaximum
            CheckType.TURNS_STOP_SPELL_MINIMUM -> character.turnsStopSpellMinimum
            CheckType.TURNS_STOP_SPELL_PERCENTAGE -> character.turnsStopSpellPercentage
            CheckType.WEAPON_ATTACK -> (character.weapon?.attack ?: 0)
        }
    }

    override fun toString(): String {
        return "checkType=$checkType " +
               "class=${super.toString()} " +
               "hashCode=${hashCode()} " +
               "iterations=$iterations " +
               "operatorType=$operatorType " +
               "value=$value"
    }
}