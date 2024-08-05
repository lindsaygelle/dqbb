package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

const val AGILITY_MINIMUM: Int = 0x03
const val BREATHE_FIRE_RANGE_MAXIMUM: Int = 255 // TODO: This number seems really high
const val BREATHE_FIRE_RANGE_MINIMUM: Int = 0
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
const val TURNS_SLEEP_MAXIMUM: Int = 6
const val TURNS_SLEEP_MINIMUM: Int = 1
const val TURNS_STOP_SPELL_MAXIMUM: Int = 6
const val TURNS_STOP_SPELL_MINIMUM: Int = 1

class Actor(
    agility: Int? = null,
    var allegiance: Int,
    var armor: Armor? = null,
    breatheFireRangeMaximum: Int? = null,
    breatheFireRangeMinimum: Int? = null,
    breatheFireScale: Int? = null,
    breatheFireShift: Int? = null,
    damageResistance: Int? = null,
    decisions: List<Decision>,
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
    name: String? = null,
    var shield: Shield? = null,
    sleepRequirementMaximum: Int? = null,
    sleepRequirementMinimum: Int? = null,
    statusResistance: Int? = null,
    stopSpellRequirementMaximum: Int? = null,
    stopSpellRequirementMinimum: Int? = null,
    strength: Int? = null,
    turnsSleep: Int? = null,
    turnsSleepMaximum: Int? = null,
    turnsSleepMinimum: Int? = null,
    turnsStopSpell: Int? = null,
    turnsStopSpellMaximum: Int? = null,
    turnsStopSpellMinimum: Int? = null,
    var weapon: Weapon? = null,
) : Identifier {

    var agility: Int = maxOf(0, (agility ?: AGILITY_MINIMUM))

    val arn: String
        get() = "${this.name}:${this.id}:${this.allegiance}"

    val breatheFireRangeMaximum: Int =
        maxOf(BREATHE_FIRE_RANGE_MINIMUM, (breatheFireRangeMaximum ?: BREATHE_FIRE_RANGE_MAXIMUM))

    val breatheFireRangeMinimum: Int =
        minOf(
            this.breatheFireRangeMaximum,
            maxOf(BREATHE_FIRE_RANGE_MINIMUM, (breatheFireRangeMinimum ?: BREATHE_FIRE_RANGE_MINIMUM))
        )

    val breatheFireScale: Int = maxOf(0, (breatheFireScale ?: BREATHE_FIRE_SCALE))

    val breatheFireShift: Int = maxOf(0, (breatheFireShift ?: BREATHE_FIRE_SHIFT))

    var damageResistance: Int = maxOf(0, (damageResistance ?: DAMAGE_RESISTANCE_MINIMUM))

    val decisions: List<Decision> = decisions.sortedByDescending { decision ->
        decision.priorityType.ordinal
    }

    val excellentMoveChanceMaximum: Int = 31

    val excellentMoveChanceMinimum: Int = 0

    val healMoreScale: Int = maxOf(0, (healMoreScale ?: HEAL_MORE_SCALE))

    val healMoreShift: Int = maxOf(0, (healMoreShift ?: HEAL_MORE_SHIFT))

    val healRangeMaximum: Int = maxOf(HEAL_RANGE_MINIMUM, (healRangeMaximum ?: HEAL_RANGE_MAXIMUM))

    val healRangeMinimum: Int =
        minOf(this.healRangeMaximum, maxOf(HEAL_RANGE_MINIMUM, (healRangeMinimum ?: HEAL_RANGE_MINIMUM)))

    val healScale: Int = maxOf(0, (healScale ?: HEAL_SCALE))

    val healShift: Int = maxOf(0, (healShift ?: HEAL_SHIFT))

    private val herbCountMaximum: Int = maxOf(0, (herbCountMaximum ?: HERB_COUNT_MINIMUM))

    private val herbCountPercentage: Int
        get() = this.getPercentage(this.getItemCount(ItemType.HERB), this.herbCountMaximum)

    val herbScale: Int = maxOf(0, (herbScale ?: HERB_SCALE))

    val herbShift: Int = maxOf(0, (herbShift ?: HERB_SHIFT))

    var hitPoints: Int = 0
        set(value) {
            field = this.getClampedValue(value, this.hitPointsMaximum)
            logger.debug(
                "$this: " +
                        "hitPoints=$field"
            )
        }

    val hitPointsMaximum: Int = maxOf(HIT_POINTS_MINIMUM, (hitPointsMaximum ?: HIT_POINTS_MINIMUM))

    private val hitPointsPercentage: Int
        get() = getPercentage(this.hitPoints, this.hitPointsMaximum)

    val hurtMoreScale: Int = maxOf(0, (hurtMoreScale ?: HURT_MORE_SCALE))

    val hurtMoreShift: Int = maxOf(0, (hurtMoreShift ?: HURT_MORE_SHIFT))

    val hurtRangeMaximum: Int = maxOf(0, (hurtRangeMaximum ?: HURT_RANGE_MAXIMUM))

    val hurtRangeMinimum: Int = minOf(this.hurtRangeMaximum, maxOf(0, (hurtRangeMinimum ?: HURT_RANGE_MINIMUM)))

    val hurtRequirementMaximum: Int = maxOf(0, (hurtRequirementMaximum ?: HURT_REQUIREMENT_MAXIMUM))

    val hurtRequirementMinimum: Int =
        minOf(this.hurtRequirementMaximum, maxOf(0, (hurtRequirementMinimum ?: HURT_REQUIREMENT_MINIMUM)))

    val hurtScale: Int = maxOf(0, (hurtScale ?: HURT_SCALE))

    val hurtShift: Int = maxOf(0, (hurtShift ?: HURT_SHIFT))

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    val isAlive: Boolean
        get() = this.hitPoints > 0

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    var magicPoints: Int = 0
        set(value) {
            field = this.getClampedValue(value, this.magicPointsMaximum)
            logger.debug(
                "$this: " +
                        "magicPoints=$field"
            )
        }

    val magicPointsMaximum: Int = maxOf(MAGIC_POINTS_MINIMUM, (magicPointsMaximum ?: MAGIC_POINTS_MINIMUM))

    private val magicPointsPercentage: Int
        get() = this.getPercentage(this.magicPoints, this.magicPointsMaximum)

    private val magicPotionsCountMaximum: Int = maxOf(0, (magicPotionsCountMaximum ?: MAGIC_POTION_COUNT_MINIMUM))

    private val magicPotionsPercentage: Int
        get() = this.getPercentage(this.getItemCount(ItemType.MAGIC_POTION), this.magicPotionsCountMaximum)

    val name: String = (name ?: "ACTOR").replace(" ", "_").uppercase()

    val sleepRequirementMaximum: Int = maxOf(0, (sleepRequirementMaximum ?: SLEEP_REQUIREMENT_MAXIMUM))

    val sleepRequirementMinimum: Int =
        minOf(this.sleepRequirementMaximum, maxOf((sleepRequirementMinimum ?: SLEEP_REQUIREMENT_MINIMUM)))

    val statusResistance: Int = maxOf(STATUS_RESISTANCE_MINIMUM, (statusResistance ?: STATUS_RESISTANCE_MINIMUM))

    val stopSpellRequirementMaximum: Int =
        maxOf(STOP_SPELL_REQUIREMENT_MINIMUM, (stopSpellRequirementMaximum ?: STOP_SPELL_REQUIREMENT_MAXIMUM))

    val stopSpellRequirementMinimum: Int = minOf(
        this.stopSpellRequirementMaximum,
        maxOf(STOP_SPELL_REQUIREMENT_MINIMUM, (stopSpellRequirementMinimum ?: STOP_SPELL_REQUIREMENT_MINIMUM))
    )

    val statusSleep: Boolean
        get() = this.turnsSleep > 0

    val statusStopSpell: Boolean
        get() = this.turnsStopSpell > 0

    val strength: Int = maxOf(STRENGTH_MINIMUM, (strength ?: STRENGTH_MINIMUM))

    val trail: MutableList<Trail> = mutableListOf()

    var turnsAlive: Int = 0

    var turnsSleep: Int = 0
        set(value) {
            field = this.getClampedValue(value, this.turnsSleepMaximum)
            logger.debug(
                "$this: " +
                        "turnsSleep=$field"
            )
        }

    // turnsSleepMaximum should be at least 0.
    val turnsSleepMaximum: Int = maxOf(0, (turnsSleepMaximum ?: TURNS_SLEEP_MAXIMUM))

    // turnsSleepMinimum should be at least 0 and less than turnsSleepMaximum
    val turnsSleepMinimum: Int =
        minOf(this.turnsSleepMaximum, maxOf(0, (turnsSleepMinimum ?: TURNS_SLEEP_MINIMUM)))

    private val turnsSleepPercentage: Int
        get() = this.getPercentage(this.turnsSleep, this.turnsSleepMaximum)

    var turnsStopSpell: Int = 0
        set(value) {
            field = this.getClampedValue(value, this.turnsStopSpellMaximum)
            logger.debug(
                "$this: " +
                        "turnsStopSpell=$field"
            )
        }

    // turnsStopSpellMaximum should be at least 0.
    val turnsStopSpellMaximum: Int = maxOf(0, (turnsStopSpellMaximum ?: TURNS_STOP_SPELL_MAXIMUM))

    // turnsStopSpellMinimum should be at least 0 and less than turnsStopSpellMaximum.
    val turnsStopSpellMinimum: Int =
        minOf(this.turnsStopSpellMaximum, maxOf(0, (turnsStopSpellMinimum ?: TURNS_STOP_SPELL_MINIMUM)))

    private val turnsStopSpellPercentage: Int
        get() = this.getPercentage(this.turnsStopSpell, this.turnsStopSpellMaximum)

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

    private fun getClampedValue(value: Int, valueMaximum: Int): Int {
        return maxOf(0, minOf(valueMaximum, value))
    }

    fun getConditionType(conditionType: ConditionType): Int {
        return when (conditionType) {
            ConditionType.AGILITY -> this.agility
            ConditionType.HERBS -> this.getItemCount(ItemType.HERB)
            ConditionType.HIT_POINTS -> this.hitPoints
            ConditionType.HIT_POINTS_MAXIMUM -> this.hitPointsMaximum
            ConditionType.MAGIC_POINTS -> this.magicPoints
            ConditionType.MAGIC_POINTS_MAXIMUM -> this.magicPointsMaximum
            ConditionType.MAGIC_POTIONS -> this.getItemCount(ItemType.MAGIC_POTION)
            ConditionType.STATUS_RESISTANCE -> this.statusResistance
            ConditionType.TURNS_SLEEP -> this.turnsSleep
            ConditionType.TURNS_STOP_SPELL -> this.turnsStopSpell
        }
    }

    fun getConditionTypePercentage(conditionType: ConditionType): Int {
        return when (conditionType) {
            ConditionType.HERBS -> this.herbCountPercentage
            ConditionType.HIT_POINTS -> this.hitPointsPercentage
            ConditionType.MAGIC_POINTS -> this.magicPointsPercentage
            ConditionType.MAGIC_POTIONS -> this.magicPotionsPercentage
            ConditionType.TURNS_SLEEP -> this.turnsSleepPercentage
            ConditionType.TURNS_STOP_SPELL -> this.turnsStopSpellPercentage
            else -> -1
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

    fun getItemCount(itemType: ItemType): Int {
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
        this.hitPoints = maxOf(HIT_POINTS_MINIMUM, (hitPoints ?: this.hitPointsMaximum))
        this.magicPoints = maxOf(MAGIC_POINTS_MINIMUM, (magicPoints ?: this.magicPointsMaximum))
        this.turnsSleep = maxOf(0, turnsSleep ?: 0)
        this.turnsStopSpell = maxOf(0, turnsStopSpell ?: 0)

        logger.info(
            "$this: " +
                    "agility=${this.agility} " +
                    "allegiance=${this.allegiance} " +
                    "armor.name=${this.armor?.name} " +
                    "breatheFireRangeMaximum=${this.breatheFireRangeMaximum} " +
                    "breatheFireRangeMinimum=${this.breatheFireRangeMinimum} " +
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
                    "name=${this.name} " +
                    "shield.name=${this.shield?.name} " +
                    "statusResistance=${this.statusResistance} " +
                    "strength=${this.strength} " +
                    "turnsSleep=${this.turnsSleep} " +
                    "turnsSleepMaximum=${this.turnsSleepMaximum} " +
                    "turnsSleepMinimum=${this.turnsSleepMinimum} " +
                    "turnsStopSpell=${this.turnsStopSpell} " +
                    "turnsStopSpellMaximum=${this.turnsStopSpellMaximum} " +
                    "turnsStopSpellMinimum=${this.turnsStopSpellMinimum} " +
                    "wakeUpChanceMaximum=${this.wakeUpChanceMaximum} " +
                    "wakeUpChanceMinimum=${this.wakeUpChanceMinimum} " +
                    "weapon.name=${this.weapon?.name}"
        )
    }
}
