package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.awt.image.BufferedImage

class Actor : ActionInvoker,
    BattleReceiver,
    Nameable {
    var actions: Collection<Action<Actor, Actor>> = emptyList()
        set(value) {
            field = value.filter { action: Action<Actor, Actor> ->
                action.actionCondition != null && action.actionTarget != null
            }.sortedByDescending { action: Action<Actor, Actor> ->
                action.priorityType.ordinal
            }
            logger.debug(
                "actions.size={} id={} simpleName={}", actions.size, id, simpleName
            )
        }

    override var agility: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "agility={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var allegiance: Int = 0
        set(value) {
            field = value
            logger.debug(
                "allegiance={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var armor: Armor? = null
        set(value) {
            field = value
            logger.debug(
                "armor.blocksSleep={} armor.blocksStopSpell={} armor.breatheFireReduction={} armor.defense={} armor.hurtReduction={} armor.id={} armor.name={} id={} simpleName={}",
                field?.blocksSleep,
                field?.blocksStopSpell,
                field?.breatheFireReduction,
                field?.defense,
                field?.hurtReduction,
                field?.id,
                field?.name,
                id,
                simpleName
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
                "breatheFireRangeMaximum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var breatheFireRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(breatheFireRangeMaximum, value))
            logger.debug(
                "breatheFireRangeMinimum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var breatheFireScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "breatheFireScale={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var breatheFireShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "breatheFireShift={} id={} simpleName={}", field, id, simpleName
            )
        }

    var bufferedImage: BufferedImage? = null
        set(value) {
            field = value
            logger.debug(
                "bufferedImage={} id={} simpleName={}", bufferedImage, id, simpleName
            )
        }

    val bufferedImageHeight: Int
        get() = bufferedImage?.height ?: 0
    
    val bufferedImageWidth: Int
        get() = bufferedImage?.width ?: 0

    override var canReceiveExcellentAttack: Boolean = true
        set(value) {
            field = value
            logger.debug(
                "canReceiveExcellentAttack={} id={} simpleName={}", field, id, simpleName
            )
        }

    private val defenseCount: Int
        get() = armorCount + shieldCount

    override var evasionRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "evasionRequirementMaximum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var evasionRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(evasionRequirementMaximum, value))
            logger.debug(
                "evasionRequirementMinimum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var excellentAttackRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "excellentAttackRequirementMaximum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var excellentAttackRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(excellentAttackRequirementMaximum, value))
            logger.debug(
                "excellentAttackRequirementMinimum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var healMoreScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "healMoreScale={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var healMoreShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "healMoreShift={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var healRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "healRangeMaximum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var healRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(healRangeMaximum, value))
            logger.debug(
                "healRangeMinimum={} id={} simpleName={}", field, id, simpleName
            )
        }

    private val herbCount: Int
        get() = items.getOrDefault(ItemName.HERB, 0)

    override var healScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "healScale={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var healShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "healShift={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var herbRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "herbRangeMaximum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var herbRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(herbRangeMaximum, value))
            logger.debug(
                "herbRangeMinimum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var herbScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "herbScale={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var herbShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "herbShift={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hitPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(hitPointsMaximum, value))
            logger.debug(
                "hitPoints={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hitPointsMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "hitPointsMaximum={} id={} simpleName={}", field, id, simpleName
            )
        }

    private val hitPointsPercentage: Int
        get() = getPercentage(hitPoints, hitPointsMaximum)

    override var hurtMoreScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "hurtMoreScale={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hurtMoreShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "hurtMoreShift={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hurtRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "hurtRangeMaximum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hurtRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(hurtRangeMaximum, value))
            logger.debug(
                "hurtRangeMinimum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hurtRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "hurtRequirementMaximum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hurtRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(hurtRequirementMaximum, value))
            logger.debug(
                "hurtRequirementMinimum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hurtResistanceMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "hurtResistanceMaximum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hurtResistanceMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(hurtResistanceMaximum, value))
            logger.debug(
                "hurtResistanceMinimum={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hurtScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "hurtScale={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hurtShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "hurtShift={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var isRunning: Boolean = false
        set(value) {
            field = value
            logger.debug(
                "id={} isRunning={} simpleName={}", id, field, simpleName
            )
        }

    override val items: MutableMap<ItemName, Int> = mutableMapOf()

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override var magicPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(magicPointsMaximum, value))
            logger.debug(
                "id={} magicPoints={} simpleName={}", id, field, simpleName
            )
        }

    override var magicPointsMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} magicPointsMaximum={} simpleName={}", id, field, simpleName
            )
        }

    private val magicPointsPercentage: Int
        get() = getPercentage(magicPoints, magicPointsMaximum)

    private val magicPotionCount: Int
        get() = items.getOrDefault(ItemName.MAGIC_POTION, 0)

    override var magicPotionRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} magicPotionRangeMaximum={} simpleName={}", id, field, simpleName
            )
        }
    override var magicPotionRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(magicPotionRangeMaximum, value))
            logger.debug(
                "id={} magicPotionRangeMinimum={} simpleName={}", id, field, simpleName
            )
        }

    override var magicPotionScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} magicPotionScale={} simpleName={}", id, field, simpleName
            )
        }

    override var magicPotionShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} magicPotionShift={} simpleName={}", id, field, simpleName
            )
        }

    override var name: String? = null
        set(value) {
            field = value
            logger.debug(
                "id={} name={} simpleName={}", id, field, simpleName
            )
        }

    override var runRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} runRangeMaximum={} simpleName={}", id, field, simpleName
            )
        }

    override var runRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(runRangeMaximum, value))
            logger.debug(
                "id={} runRangeMinimum={} simpleName={}", id, field, simpleName
            )
        }

    override var runShift: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} runShift={} simpleName={}", id, field, simpleName
            )
        }

    override var shield: Shield? = null
        set(value) {
            field = value
            logger.debug(
                "id={} shield.defense={} shield.id={} shield.name={} simpleName={}",
                id,
                field?.defense,
                field?.id,
                field?.name,
                simpleName
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
                "id={} simpleName={} sleepRequirementMaximum={}", id, simpleName, field
            )
        }

    override var sleepRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(sleepRequirementMaximum, value))
            logger.debug(
                "id={} simpleName={} sleepRequirementMinimum={}", id, simpleName, field
            )
        }

    override var sleepResistanceMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} sleepResistanceMaximum={}", id, simpleName, field
            )
        }

    override var sleepResistanceMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(sleepResistanceMaximum, value))
            logger.debug(
                "id={} simpleName={} sleepResistanceMinimum={}", id, simpleName, field
            )
        }

    override var sleepResolutionMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} sleepResolutionMaximum={}", id, simpleName, field
            )
        }

    override var sleepResolutionMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(sleepResolutionMaximum, value))
            logger.debug(
                "id={} simpleName={} sleepResolutionMinimum={}", id, simpleName, field
            )
        }

    override var stopSpellRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} stopSpellRequirementMaximum={}", id, simpleName, field
            )
        }

    override var stopSpellRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(stopSpellRequirementMaximum, value))
            logger.debug(
                "id={} simpleName={} stopSpellRequirementMinimum={}", id, simpleName, field
            )
        }

    override var stopSpellResistanceMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} stopSpellResistanceMaximum={}", id, simpleName, field
            )
        }

    override var stopSpellResistanceMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(stopSpellResistanceMaximum, value))
            logger.debug(
                "id={} simpleName={} stopSpellResistanceMinimum={}", id, simpleName, field
            )
        }

    override var stopSpellResolutionMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} stopSpellResolutionMaximum={}", id, simpleName, field
            )
        }

    override var stopSpellResolutionMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(stopSpellResolutionMaximum, value))
            logger.debug(
                "id={} simpleName={} stopSpellResolutionMinimum={}", id, simpleName, field
            )
        }

    override var strength: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} strength={}", id, simpleName, field
            )
        }

    override var turn: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} turn={}", id, simpleName, field
            )
        }

    override var turnsSleep: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsSleepMaximum, value))
            logger.debug(
                "id={} simpleName={} turnsSleep={}", id, simpleName, field
            )
        }

    override var turnsSleepMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} turnsSleepMaximum={}", id, simpleName, field
            )
        }

    override var turnsSleepMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsSleepMaximum, value))
            logger.debug(
                "id={} simpleName={} turnsSleepMinimum={}", id, simpleName, field
            )
        }

    override var turnsStopSpell: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} turnsStopSpell={}", id, simpleName, field
            )
        }

    override var turnsStopSpellMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} turnsStopSpellMaximum={}", id, simpleName, field
            )
        }

    override var turnsStopSpellMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsStopSpellMaximum, value))
            logger.debug(
                "id={} simpleName={} turnsStopSpellMinimum={}", id, simpleName, field
            )
        }

    override var weapon: Weapon? = null
        set(value) {
            field = value
            logger.debug(
                "id={} simpleName={} weapon.attack={} weapon.id={} weapon.name={}",
                id,
                simpleName,
                field?.attack,
                field?.id,
                field?.name
            )
        }

    private val weaponAttack: Int
        get() = weapon?.attack ?: 0

    private val weaponCount: Int
        get() = if (weapon != null) 1 else 0

    override fun getAttribute(attributeName: AttributeName): Int {
        val attributeValue = when (attributeName) {
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
            AttributeName.HASH_CODE -> id
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
        logger.info(
            "attributeName={} attributeValue={} id={} simpleName={}", attributeName, attributeValue, id, simpleName
        )
        return attributeValue
    }

    private fun getPercentage(value: Int, valueMaximum: Int): Int {
        logger.trace(
            "id={} simpleName={} value={} valueMaximum={}", id, simpleName, value, valueMaximum
        )
        return ((value.toDouble() / valueMaximum) * 100).toInt()
    }
}