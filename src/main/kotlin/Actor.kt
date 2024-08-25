package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Actor : AbilityInvoker,
    BattleReceiver,
    Identifier,
    Nameable {
    var actions: List<Action> = listOf()
        set(value) {
            field = value.sortedByDescending { action -> action.priorityType.ordinal }
            logger.debug(
                "id={} actions.size={}", id, field.size
            )
        }
    override var agility: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} agility={}", id, field
            )
        }
    var allegiance: Int = 0
        set(value) {
            field = value
            logger.debug(
                "id={} allegiance={}", id, field
            )
        }
    override var armor: Armor? = null
        set(value) {
            field = value
            logger.debug(
                "id={} armor.id={}", id, field?.id
            )
        }
    private val armorCount: Int
        get() = if (armor != null) 1 else 0
    private val armorDefense: Int
        get() = (armor?.defense ?: 0)
    private val blocksSleep: Boolean
        get() = armor?.blocksSleep == true
    private val blocksStopSpell: Boolean
        get() = armor?.blocksStopSpell == true
    override var breatheFireRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} breatheFireRangeMaximum={}", id, field
            )
        }
    override var breatheFireRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(breatheFireRangeMaximum, value))
            logger.debug(
                "id={} breatheFireRangeMinimum={}", id, field
            )
        }
    override var breatheFireScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} breatheFireScale={}", id, field
            )
        }
    override var breatheFireShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} breatheFireShift={}", id, field
            )
        }
    override var canReceiveExcellentAttack: Boolean = true
        set(value) {
            field = value
            logger.debug(
                "id={} canReceiveExcellentAttack={}", id, field
            )
        }
    private val defenseCount: Int
        get() = armorCount + shieldCount
    override var evasionRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} evasionRequirementMaximum={}", id, field
            )
        }
    override var evasionRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(evasionRequirementMaximum, value))
            logger.debug(
                "id={} evasionRequirementMinimum={}", id, field
            )
        }
    override var excellentAttackRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} excellentAttackRequirementMaximum={}", id, field
            )
        }
    override var excellentAttackRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(excellentAttackRequirementMaximum, value))
            logger.debug(
                "id={} excellentAttackRequirementMinimum={}", id, field
            )
        }
    override var healMoreScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} healMoreScale={}", id, field
            )
        }
    override var healMoreShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} healMoreShift={}", id, field
            )
        }
    override var healRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} healRangeMaximum={}", id, field
            )
        }
    override var healRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(healRangeMaximum, value))
            logger.debug(
                "id={} healRangeMinimum={}", id, field
            )
        }
    private val herbCount: Int
        get() = items.getOrDefault(ItemName.HERB, 0)
    override var healScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} healScale={}", id, field
            )
        }
    override var healShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} healShift={}", id, field
            )
        }
    override var herbRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} herbRangeMaximum={}", id, field
            )
        }
    override var herbRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(herbRangeMaximum, value))
            logger.debug(
                "id={} herbRangeMinimum={}", id, field
            )
        }
    override var herbScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} herbScale={}", id, field
            )
        }
    override var herbShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} herbShift={}", id, field
            )
        }
    override var hitPoints: Int = 0
        set(value) {
            if (value > field) {
                hitPointsReceived += value
            } else {
                hitPointsReduced += value
            }
            field = maxOf(0, minOf(hitPointsMaximum, value))
            logger.debug(
                "id={} hitPoints={}", id, field
            )
        }
    override var hitPointsMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} hitPointsMaximum={}", id, field
            )
        }
    private val hitPointsPercentage: Int
        get() = getPercentage(magicPoints, magicPointsMaximum)
    var hitPointsReceived: Int = 0
        set(value) {
            field = value
            logger.debug(
                "id={} hitPointsReceived={}", id, field
            )
        }
    var hitPointsReduced: Int = 0
        set(value) {
            field = value
            logger.debug(
                "id={} hitPointsReduced={}", id, field
            )
        }
    override var hurtMoreScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} hurtMoreScale={}", id, field
            )
        }
    override var hurtMoreShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} hurtMoreShift={}", id, field
            )
        }
    override var hurtRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} hurtRangeMaximum={}", id, field
            )
        }
    override var hurtRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(hurtRangeMaximum, value))
            logger.debug(
                "id={} hurtRangeMinimum={}", id, field
            )
        }
    override var hurtRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} hurtRequirementMaximum={}", id, field
            )
        }
    override var hurtRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(hurtRequirementMaximum, value))
            logger.debug(
                "id={} hurtRequirementMinimum={}", id, field
            )
        }
    override var hurtResistanceMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} hurtResistanceMaximum={}", id, field
            )
        }
    override var hurtResistanceMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(hurtResistanceMaximum, value))
            logger.debug(
                "id={} hurtResistanceMinimum={}", id, field
            )
        }
    override var hurtScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} hurtScale={}", id, field
            )
        }
    override var hurtShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} hurtShift={}", id, field
            )
        }
    override var isRunning: Boolean = false
        set(value) {
            field = value
            logger.debug(
                "id={} isRunning={}", id, field
            )
        }
    override val items: MutableMap<ItemName, Int> = mutableMapOf()
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)
    override var magicPoints: Int = 0
        set(value) {
            if (value > field) {
                magicPointsReceived += value
            } else {
                magicPointsReduced += value
            }
            field = maxOf(0, minOf(magicPointsMaximum, value))
            logger.debug(
                "id={} magicPoints={}", id, field
            )
        }
    override var magicPointsMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} magicPointsMaximum={}", id, field
            )
        }
    var magicPointsReceived: Int = 0
        set(value) {
            field = value
            logger.debug(
                "id={} magicPointsReceived={}", id, field
            )
        }
    var magicPointsReduced: Int = 0
        set(value) {
            field = value
            logger.debug(
                "id={} magicPointsReduced={}", id, field
            )
        }
    private val magicPotionCount: Int
        get() = items.getOrDefault(ItemName.MAGIC_POTION, 0)
    override var magicPotionRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} magicPotionRangeMaximum={}", id, field
            )
        }
    private val magicPointsPercentage: Int
        get() = getPercentage(magicPoints, magicPointsMaximum)
    override var magicPotionRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(magicPotionRangeMaximum, value))
            logger.debug(
                "id={} magicPotionRangeMinimum={}", id, field
            )
        }
    override var magicPotionScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} magicPotionScale={}", id, field
            )
        }
    override var magicPotionShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} magicPotionShift={}", id, field
            )
        }
    override var name: String? = null
        set(value) {
            field = value
            logger.debug(
                "id={} name={}", id, field
            )
        }
    override var runRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} runRangeMaximum={}", id, field
            )
        }
    override var runRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(runRangeMaximum, value))
            logger.debug(
                "id={} runRangeMinimum={}", id, field
            )
        }
    override var runShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} runShift={}", id, field
            )
        }
    override var shield: Shield? = null
        set(value) {
            field = value
            logger.debug(
                "id={} shield.id={}", id, field?.id
            )
        }
    private val shieldCount: Int
        get() = if (shield != null) 1 else 0
    private val shieldDefense: Int
        get() = shield?.defense ?: 0
    override var sleepRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} sleepRequirementMaximum={}", id, field
            )
        }
    override var sleepRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(sleepRequirementMaximum, value))
            logger.debug(
                "id={} sleepRequirementMinimum={}", id, field
            )
        }
    override var sleepResistanceMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} sleepResistanceMaximum={}", id, field
            )
        }
    override var sleepResistanceMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(sleepResistanceMaximum, value))
            logger.debug(
                "id={} sleepResistanceMinimum={}", id, field
            )
        }
    override var sleepResolutionMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} sleepResolutionMaximum={}", id, field
            )
        }
    override var sleepResolutionMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(sleepResolutionMaximum, value))
            logger.debug(
                "id={} sleepResolutionMinimum={}", id, field
            )
        }
    override var stopSpellRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} stopSpellRequirementMaximum={}", id, field
            )
        }
    override var stopSpellRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(stopSpellRequirementMaximum, value))
            logger.debug(
                "id={} stopSpellRequirementMinimum={}", id, field
            )
        }
    override var stopSpellResistanceMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} stopSpellResistanceMaximum={}", id, field
            )
        }
    override var stopSpellResistanceMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(stopSpellResistanceMaximum, value))
            logger.debug(
                "id={} stopSpellResistanceMinimum={}", id, field
            )
        }
    override var stopSpellResolutionMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} stopSpellResolutionMaximum={}", id, field
            )
        }
    override var stopSpellResolutionMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(stopSpellResolutionMaximum, value))
            logger.debug(
                "id={} stopSpellResolutionMinimum={}", id, field
            )
        }
    override var strength: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} strength={}", id, field
            )
        }
    override var turn: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} turn={}", id, field
            )
        }
    override var turnsSleep: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsSleepMaximum, value))
            logger.debug(
                "id={} turnsSleep={}", id, field
            )
        }
    override var turnsSleepMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} turnsSleepMaximum={}", id, field
            )
        }
    override var turnsSleepMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsSleepMaximum, value))
            logger.debug(
                "id={} turnsSleepMinimum={}", id, field
            )
        }
    override var turnsStopSpell: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} turnsStopSpell={}", id, field
            )
        }
    override var turnsStopSpellMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} turnsStopSpellMaximum={}", id, field
            )
        }
    override var turnsStopSpellMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsStopSpellMaximum, value))
            logger.debug(
                "id={} turnsStopSpellMinimum={}", id, field
            )
        }
    override var weapon: Weapon? = null
        set(value) {
            field = value
            logger.debug(
                "id={} weapon.id={}", id, field?.id
            )
        }
    private val weaponAttack: Int
        get() = weapon?.attack ?: 0
    private val weaponCount: Int
        get() = if (weapon != null) 1 else 0

    fun getAttribute(attributeName: AttributeName): Int {
        return when (attributeName) {
            AttributeName.ACTION_COUNT -> actions.size
            AttributeName.AGILITY -> agility
            AttributeName.ALLEGIANCE -> allegiance
            AttributeName.ARMOR_COUNT -> armorCount
            AttributeName.ARMOR_DEFENSE -> armorDefense
            AttributeName.BLOCKS_SLEEP -> (if (blocksSleep) 1 else 0)
            AttributeName.BLOCKS_STOP_SPELL -> (if (blocksStopSpell) 1 else 1)
            AttributeName.BREATHE_FIRE_RANGE_MAXIMUM -> breatheFireRangeMaximum
            AttributeName.BREATHE_FIRE_RANGE_MINIMUM -> breatheFireRangeMinimum
            AttributeName.BREATHE_FIRE_SCALE -> breatheFireScale
            AttributeName.BREATHE_FIRE_SHIFT -> breatheFireShift
            AttributeName.DEFENSE_COUNT -> defenseCount
            AttributeName.EVASION_REQUIREMENT_MAXIMUM -> evasionRequirementMaximum
            AttributeName.EVASION_REQUIREMENT_MINIMUM -> evasionRequirementMinimum
            AttributeName.EXCELLENT_ATTACK_REQUIREMENT_MAXIMUM -> excellentAttackRequirementMaximum
            AttributeName.EXCELLENT_ATTACK_REQUIREMENT_MINIMUM -> excellentAttackRequirementMinimum
            AttributeName.HEAL_MORE_SCALE -> healMoreScale
            AttributeName.HEAL_MORE_SHIFT -> healMoreShift
            AttributeName.HEAL_RANGE_MAXIMUM -> healRangeMaximum
            AttributeName.HEAL_RANGE_MINIMUM -> healRangeMinimum
            AttributeName.HEAL_SCALE -> healScale
            AttributeName.HEAL_SHIFT -> healShift
            AttributeName.HERB_COUNT -> herbCount
            AttributeName.HERB_RANGE_MAXIMUM -> herbRangeMaximum
            AttributeName.HERB_RANGE_MINIMUM -> herbRangeMinimum
            AttributeName.HERB_SCALE -> herbScale
            AttributeName.HERB_SHIFT -> herbShift
            AttributeName.HIT_POINTS -> hitPoints
            AttributeName.HIT_POINTS_MAXIMUM -> hitPointsMaximum
            AttributeName.HIT_POINTS_PERCENTAGE -> hitPointsPercentage
            AttributeName.HURT_MORE_SCALE -> hurtMoreScale
            AttributeName.HURT_MORE_SHIFT -> hurtMoreShift
            AttributeName.HURT_RANGE_MAXIMUM -> hurtRangeMaximum
            AttributeName.HURT_RANGE_MINIMUM -> hurtRangeMinimum
            AttributeName.HURT_REQUIREMENT_MAXIMUM -> hurtRequirementMaximum
            AttributeName.HURT_REQUIREMENT_MINIMUM -> hurtRequirementMinimum
            AttributeName.HURT_RESISTANCE_MAXIMUM -> hurtResistanceMaximum
            AttributeName.HURT_RESISTANCE_MINIMUM -> hurtResistanceMinimum
            AttributeName.HURT_SCALE -> hurtScale
            AttributeName.HURT_SHIFT -> hurtShift
            AttributeName.IS_RUNNING -> (if (isRunning) 1 else 0)
            AttributeName.ITEM_COUNT -> items.size
            AttributeName.MAGIC_POINTS -> magicPoints
            AttributeName.MAGIC_POINTS_MAXIMUM -> magicPointsMaximum
            AttributeName.MAGIC_POINTS_PERCENTAGE -> magicPointsPercentage
            AttributeName.MAGIC_POTION_COUNT -> magicPotionCount
            AttributeName.MAGIC_POTION_RANGE_MAXIMUM -> magicPotionRangeMaximum
            AttributeName.MAGIC_POTION_RANGE_MINIMUM -> magicPotionRangeMinimum
            AttributeName.MAGIC_POTION_SCALE -> magicPotionScale
            AttributeName.MAGIC_POTION_SHIFT -> magicPotionShift
            AttributeName.RUN_RANGE_MAXIMUM -> runRangeMaximum
            AttributeName.RUN_RANGE_MINIMUM -> runRangeMinimum
            AttributeName.RUN_SHIFT -> runShift
            AttributeName.SHIELD_COUNT -> shieldCount
            AttributeName.SHIELD_DEFENSE -> shieldDefense
            AttributeName.SLEEP_REQUIREMENT_MAXIMUM -> sleepRequirementMaximum
            AttributeName.SLEEP_REQUIREMENT_MINIMUM -> sleepRequirementMinimum
            AttributeName.SLEEP_RESISTANCE_MAXIMUM -> sleepResistanceMaximum
            AttributeName.SLEEP_RESISTANCE_MINIMUM -> sleepResistanceMinimum
            AttributeName.SLEEP_RESOLUTION_MAXIMUM -> sleepResolutionMaximum
            AttributeName.SLEEP_RESOLUTION_MINIMUM -> sleepResolutionMinimum
            AttributeName.STOP_SPELL_REQUIREMENT_MAXIMUM -> stopSpellRequirementMaximum
            AttributeName.STOP_SPELL_REQUIREMENT_MINIMUM -> stopSpellRequirementMaximum
            AttributeName.STOP_SPELL_RESISTANCE_MAXIMUM -> stopSpellResistanceMaximum
            AttributeName.STOP_SPELL_RESISTANCE_MINIMUM -> stopSpellResistanceMinimum
            AttributeName.STOP_SPELL_RESOLUTION_MAXIMUM -> stopSpellResolutionMaximum
            AttributeName.STOP_SPELL_RESOLUTION_MINIMUM -> stopSpellResolutionMinimum
            AttributeName.STRENGTH -> strength
            AttributeName.TURNS_SLEEP -> turnsSleep
            AttributeName.TURNS_SLEEP_MAXIMUM -> turnsSleepMaximum
            AttributeName.TURNS_SLEEP_MINIMUM -> turnsSleepMinimum
            AttributeName.TURNS_STOP_SPELL -> turnsStopSpell
            AttributeName.TURNS_STOP_SPELL_MAXIMUM -> turnsStopSpellMaximum
            AttributeName.TURNS_STOP_SPELL_MINIMUM -> turnsStopSpellMinimum
            AttributeName.WEAPON_ATTACK -> weaponAttack
            AttributeName.WEAPON_COUNT -> weaponCount
        }
    }

    private fun getPercentage(value: Int, valueMaximum: Int): Int {
        return ((value.toDouble() / valueMaximum) * 100).toInt()
    }
}