package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

const val AGILITY_MINIMUM: Int = 0x03
const val BREATHE_FIRE_SCALE: Int = 0x10
const val BREATHE_FIRE_SHIFT: Int = 0x07
const val DAMAGE_RESISTANCE_MINIMUM: Int = 0x01
const val HEAL_MORE_SCALE: Int = 0x55
const val HEAL_MORE_SHIFT: Int = 0x0F
const val HEAL_RANGE_MAXIMUM: Int = 7
const val HEAL_RANGE_MINIMUM: Int = 0
const val HEAL_SCALE: Int = 0x14
const val HEAL_SHIFT: Int = 0x07
const val HERB_COUNT_MINIMUM: Int = 0x01
const val HERB_SCALE: Int = 0x17
const val HERB_SHIFT: Int = 0x0F
const val HIT_POINTS_MINIMUM: Int = 0x03
const val HURT_MORE_SCALE: Int = 0x1E
const val HURT_MORE_SHIFT: Int = 0x0F
const val HURT_RANGE_MAXIMUM: Int = 16
const val HURT_RANGE_MINIMUM: Int = 0
const val HURT_REQUIREMENT_MAXIMUM: Int = 16
const val HURT_REQUIREMENT_MINIMUM: Int = 0
const val HURT_SCALE: Int = 0x03
const val HURT_SHIFT: Int = 0x07
const val MAGIC_POINTS_MINIMUM: Int = 0x00
const val MAGIC_POTION_COUNT_MINIMUM: Int = 0x01
const val SLEEP_REQUIREMENT_MAXIMUM: Int = 16
const val SLEEP_REQUIREMENT_MINIMUM: Int = 0
const val STATUS_RESISTANCE_MINIMUM: Int = 0x00
const val STOP_SPELL_REQUIREMENT_MAXIMUM: Int = 16
const val STOP_SPELL_REQUIREMENT_MINIMUM: Int = 0
const val STRENGTH_MINIMUM: Int = 0x05
const val TURNS_SLEEP_MINIMUM: Int = 6
const val TURNS_STOP_SPELL_MINIMUM: Int = 6

class Actor(
    agility: Int? = null,
    val allegiance: Int,
    var armor: Armor? = null,
    damageResistance: Int? = null,
    decisions: List<Decision>,
    excellentMoveChanceMaximum: Int? = null,
    healMoreScale: Int? = null,
    healMoreShift: Int? = null,
    healRangeMaximum: Int? = null,
    healRangeMinimum: Int? = null,
    healScale: Int? = null,
    healShift: Int? = null,
    herbCountMaximum: Int? = null,
    herbScale: Int? = null,
    herbShift: Int? = null,
    hitPoints: Int? = null,
    hitPointsMaximum: Int? = null,
    hurtMoreScale: Int? = null,
    hurtMoreShift: Int? = null,
    hurtRangeMaximum: Int? = null,
    hurtRangeMinimum: Int? = null,
    hurtRequirementMaximum: Int? = null,
    hurtRequirementMinimum: Int? = null,
    hurtScale: Int? = null,
    hurtShift: Int? = null,
    private val items: MutableMap<ItemType, Int> = mutableMapOf(),
    magicPoints: Int? = null,
    magicPointsMaximum: Int? = null,
    magicPotionsCountMaximum: Int? = null,
    var shield: Shield? = null,
    sleepRequirementMaximum: Int? = null,
    sleepRequirementMinimum: Int? = null,
    statusResistance: Int? = null,
    stopSpellRequirementMaximum: Int? = null,
    stopSpellRequirementMinimum: Int? = null,
    strength: Int? = null,
    turnsSleep: Int? = null,
    turnsSleepMaximum: Int? = null,
    turnsStopSpell: Int? = null,
    turnsStopSpellMaximum: Int? = null,
    var weapon: Weapon? = null,
) : Identifier {

    var agility: Int = maxOf(0, agility ?: AGILITY_MINIMUM)

    private val agilityPercentage: Int = 100 // TODO

    val breatheFireScale: Int = BREATHE_FIRE_SCALE // TODO

    val breatheFireShift: Int = BREATHE_FIRE_SHIFT // TODO

    var damageResistance: Int = (damageResistance ?: DAMAGE_RESISTANCE_MINIMUM)

    private val decisions: List<Decision> = decisions.sortedByDescending { decision ->
        decision.priorityType.ordinal
    }

    val excellentMoveChanceMaximum: Int = maxOf(0, (excellentMoveChanceMaximum ?: 32))

    val excellentMoveChanceMinimum: Int = 0

    val healMoreScale: Int = (healMoreScale ?: HEAL_MORE_SCALE)

    val healMoreShift: Int = (healMoreShift ?: HEAL_MORE_SHIFT)

    val healRangeMaximum: Int = (healRangeMaximum ?: HEAL_RANGE_MAXIMUM)

    val healRangeMinimum: Int = (healRangeMinimum ?: HEAL_RANGE_MINIMUM)

    val healScale: Int = (healScale ?: HEAL_SCALE)

    val healShift: Int = (healShift ?: HEAL_SHIFT)

    private val herbCountMaximum: Int = (herbCountMaximum ?: HERB_COUNT_MINIMUM)

    private val herbCountPercentage: Int
        get() = this.getPercentage(this.getItem(ItemType.HERB), this.herbCountMaximum)

    val herbScale: Int = (herbScale ?: HERB_SCALE)

    val herbShift: Int = (herbShift ?: HERB_SHIFT)

    var hitPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.hitPointsMaximum, value))
            logger.debug(
                "$this: " +
                        "hitPoints=$field"
            )
        }

    val hitPointsMaximum: Int = maxOf(1, (hitPointsMaximum ?: HIT_POINTS_MINIMUM))

    private val hitPointsPercentage: Int
        get() = getPercentage(this.hitPoints, this.hitPointsMaximum)

    val hurtMoreScale: Int = (hurtMoreScale ?: HURT_MORE_SCALE)

    val hurtMoreShift: Int = (hurtMoreShift ?: HURT_MORE_SHIFT)

    val hurtRangeMaximum: Int = (hurtRangeMaximum ?: HURT_RANGE_MAXIMUM)

    val hurtRangeMinimum: Int = (hurtRangeMinimum ?: HURT_RANGE_MINIMUM)

    val hurtRequirementMaximum: Int = (hurtRequirementMaximum ?: HURT_REQUIREMENT_MAXIMUM)

    val hurtRequirementMinimum: Int = (hurtRequirementMinimum ?: HURT_REQUIREMENT_MINIMUM)

    val hurtScale: Int = (hurtScale ?: HURT_SCALE)

    val hurtShift: Int = (hurtShift ?: HURT_SHIFT)

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    val isAlive: Boolean
        get() = this.hitPoints > 0

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    var magicPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.magicPointsMaximum, value))
            logger.debug(
                "$this: " +
                        "magicPoints=$field"
            )
        }

    val magicPointsMaximum: Int = (magicPointsMaximum ?: MAGIC_POINTS_MINIMUM)

    private val magicPointsPercentage: Int
        get() = this.getPercentage(this.magicPoints, this.magicPointsMaximum)

    private val magicPotionsCountMaximum: Int = (magicPotionsCountMaximum ?: MAGIC_POTION_COUNT_MINIMUM)

    private val magicPotionsPercentage: Int
        get() = this.getPercentage(this.getItem(ItemType.MAGIC_POTION), this.magicPotionsCountMaximum)

    val sleepRequirementMaximum: Int = (sleepRequirementMaximum ?: SLEEP_REQUIREMENT_MAXIMUM)

    val sleepRequirementMinimum: Int = (sleepRequirementMinimum ?: SLEEP_REQUIREMENT_MINIMUM)

    val statusResistance: Int = (statusResistance ?: STATUS_RESISTANCE_MINIMUM)

    val stopSpellRequirementMaximum: Int = (stopSpellRequirementMaximum ?: STOP_SPELL_REQUIREMENT_MAXIMUM)

    val stopSpellRequirementMinimum: Int = (stopSpellRequirementMinimum ?: STOP_SPELL_REQUIREMENT_MINIMUM)

    val statusSleep: Boolean
        get() = this.turnsSleep > 0

    val statusStopSpell: Boolean
        get() = this.turnsStopSpell > 0

    val strength: Int = (strength ?: STRENGTH_MINIMUM)

    val trail: MutableList<Trail> = mutableListOf()

    var turnsAlive: Int = 0

    var turnsSleep: Int = 0
        set(value) {
            field = this.getClampedValue(value, this.turnsSleepMaximum, 0)
            logger.debug(
                "$this: " +
                        "turnsSleep=$field"
            )
        }

    val turnsSleepMaximum: Int = (turnsSleepMaximum ?: TURNS_SLEEP_MINIMUM)

    private val turnsSleepPercentage: Int
        get() = getPercentage(this.turnsSleep, this.turnsSleepMaximum)

    var turnsStopSpell: Int = 0
        set(value) {
            field = this.getClampedValue(value, turnsStopSpellMaximum, 0)
            logger.debug(
                "$this: " +
                        "turnsStopSpell=$field"
            )
        }

    val turnsStopSpellMaximum: Int = (turnsStopSpellMaximum ?: TURNS_STOP_SPELL_MINIMUM)

    private val turnsStopSpellPercentage: Int
        get() = getPercentage(this.turnsStopSpell, this.turnsStopSpellMaximum)

    val wakeUpChanceMaximum: Int = 3

    val wakeUpChanceMinimum: Int = 0

    fun addItem(itemType: ItemType, value: Int) {
        val itemCountPrevious = this.items.getOrDefault(itemType, 0)
        val itemCountMaximum = when (itemType) {
            ItemType.HERB -> this.herbCountMaximum
            ItemType.MAGIC_POTION -> this.magicPotionsCountMaximum
        }
        val itemCount = minOf((itemCountPrevious + value), itemCountMaximum)
        this.items[itemType] = itemCount
        logger.debug(
            "$this: " +
                    "itemCount=$itemCount " +
                    "itemCountMaximum=$itemCountMaximum " +
                    "itemCountPrevious=$itemCountPrevious " +
                    "itemType=$itemType"
        )
    }

    fun getAttackValue(actor: Actor): Int {
        return this.strength
    }

    private fun getClampedValue(value: Int, valueMaximum: Int, valueMinimum: Int): Int {
        return maxOf(valueMinimum, minOf(valueMaximum, value))
    }

    fun getConditionType(conditionType: ConditionType): Int {
        return when (conditionType) {
            ConditionType.AGILITY -> this.agility
            ConditionType.HERBS -> this.getItem(ItemType.HERB)
            ConditionType.HIT_POINTS -> this.hitPoints
            ConditionType.MAGIC_POINTS -> this.magicPoints
            ConditionType.MAGIC_POINTS_MAXIMUM -> this.magicPointsMaximum
            ConditionType.MAGIC_POTIONS -> this.getItem(ItemType.MAGIC_POTION)
            ConditionType.STATUS_RESISTANCE -> this.statusResistance
            ConditionType.TURNS_SLEEP -> this.turnsSleep
            ConditionType.TURNS_STOP_SPELL -> this.turnsStopSpell
        }
    }

    fun getConditionTypePercentage(conditionType: ConditionType): Int {
        return when (conditionType) {
            ConditionType.AGILITY -> this.agilityPercentage
            ConditionType.HERBS -> this.herbCountPercentage
            ConditionType.HIT_POINTS -> this.hitPointsPercentage
            ConditionType.MAGIC_POINTS -> this.magicPointsPercentage
            ConditionType.MAGIC_POTIONS -> this.magicPotionsPercentage
            ConditionType.TURNS_SLEEP -> this.turnsSleepPercentage
            ConditionType.TURNS_STOP_SPELL -> this.turnsStopSpellPercentage
            else -> 0
        }
    }

    private fun getDecision(otherActors: Collection<Actor>): Decision? {
        this.decisions.forEachIndexed { index, decision ->
            val isValid = decision.isValid(this, otherActors)
            logger.debug(
                "$this: " +
                        "decision.ability.id=${decision.ability.id} " +
                        "decision.ability.name=${decision.ability.name} " +
                        "decision.id=${decision.id} " +
                        "decision.priorityType=${decision.priorityType} " +
                        "decision.isValid=$isValid " +
                        "index=$index"
            )
            if (isValid) {
                return decision
            }
        }
        return null
    }

    fun getDefenseValue(actor: Actor): Int {
        return this.agility / 2
    }

    fun getItem(itemType: ItemType): Int {
        return this.items.getOrDefault(itemType, 0)
    }

    private fun getPercentage(value: Int, valueMaximum: Int): Int {
        return ((value.toDouble() / valueMaximum) * 100).toInt()
    }

    fun subtractItem(itemType: ItemType) {
        this.items[itemType]?.let { itemCount ->
            if (itemCount > 0) {
                this.items[itemType] = itemCount - 1
            }
        }
    }

    fun takeTurn(otherActors: Collection<Actor>): Boolean {
        val decision = getDecision(otherActors)
        var decisionValue = false
        logger.debug(
            "$this: " +
                    "decision.id=${decision?.id} " +
                    "otherActors.size=${otherActors.size}"
        )
        if (decision != null) {
            decisionValue = decision.ability.use(this, decision.targetSelection.actors)
            logger.debug(
                "$this: " +
                        "decision.ability.id=${decision.ability.id} " +
                        "decision.ability.name=${decision.ability.name} " +
                        "decision.ability.use=$decisionValue " +
                        "decision.id=${decision.id}"
            )
        }
        return decisionValue
    }

    init {
        this.hitPoints = hitPoints ?: this.hitPointsMaximum
        this.magicPoints = magicPoints ?: this.magicPointsMaximum
        this.turnsSleep = turnsSleep ?: 0
        this.turnsStopSpell = turnsStopSpell ?: 0

        logger.info(
            "$this: " +
                    "agility=${this.agility} " +
                    "allegiance=${this.allegiance} " +
                    "armor.name=${this.armor?.name} " +
                    "breatheFireScale=${this.breatheFireScale} " +
                    "breatheFireShift=${this.breatheFireShift} " +
                    "damageResistance=${this.damageResistance} " +
                    "decisions.size=${this.decisions.size} " +
                    "excellentMoveChanceMaximum=${this.excellentMoveChanceMaximum} " +
                    "excellentMoveChanceMinimum=${this.excellentMoveChanceMinimum} " +
                    "healMoreScale=${this.healMoreScale} " +
                    "healMoreShift=${this.healMoreShift} " +
                    "healRangeMaximum=${this.healRangeMaximum} " +
                    "healRangeMinimum=${this.healRangeMinimum} " +
                    "healScale=${this.healScale} " +
                    "healShift=${this.healShift} " +
                    "herbCountMaximum=${this.herbCountMaximum} " +
                    "herbScale=${this.herbScale} " +
                    "herbShift=${this.herbShift} " +
                    "hitPoints=${this.hitPoints} " +
                    "hitPointsMaximum=${this.hitPointsMaximum} " +
                    "hurtMoreScale=${this.hurtMoreScale} " +
                    "hurtMoreShift=${this.hurtMoreShift} " +
                    "hurtScale=${this.hurtScale} " +
                    "hurtShift=${this.hurtShift} " +
                    "items.size=${this.items.size} " +
                    "magicPoints=${this.magicPoints} " +
                    "magicPointsMaximum=${this.magicPointsMaximum} " +
                    "magicPotionsCountMaximum=${this.magicPotionsCountMaximum} " +
                    "shield.name=${this.shield?.name} " +
                    "statusResistance=${this.statusResistance} " +
                    "strength=${this.strength} " +
                    "turnsSleep=${this.turnsSleep} " +
                    "turnsSleepMaximum=${this.turnsSleepMaximum} " +
                    "turnsStopSpell=${this.turnsStopSpell} " +
                    "turnsStopSpellMaximum=${this.turnsStopSpellMaximum} " +
                    "wakeUpChanceMaximum=${this.wakeUpChanceMaximum} " +
                    "wakeUpChanceMinimum=${this.wakeUpChanceMinimum} " +
                    "weapon.name=${this.weapon?.name}"
        )
    }
}
