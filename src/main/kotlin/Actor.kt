package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class Actor(
    agility: Int? = null,
    agilityMaximum: Int? = null,
    agilityMinimum: Int? = null,
    val allegiance: Int,
    var armor: Armor? = null,
    damageResistance: Int? = null,
    damageResistanceMaximum: Int? = null,
    damageResistanceMinimum: Int? = null,
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
    hurtScale: Int? = null,
    hurtShift: Int? = null,
    private val items: MutableMap<ItemType, Int> = mutableMapOf(),
    magicPoints: Int? = null,
    magicPointsMaximum: Int? = null,
    magicPotionsCountMaximum: Int? = null,
    var shield: Shield? = null,
    statusResistance: Int? = null,
    strength: Int? = null,
    strengthMaximum: Int? = null,
    strengthMinimum: Int? = null,
    turnsSleep: Int? = null,
    turnsSleepMaximum: Int? = null,
    turnsStopSpell: Int? = null,
    turnsStopSpellMaximum: Int? = null,
    wakeUpChanceMaximum: Int? = null,
    wakeUpChanceMinimum: Int? = null,
    var weapon: Weapon? = null,
) : Identifier {

    var agility: Int = 0
        set(value) {
            field = this.getClampedValue(value, this.agilityMaximum, this.agilityMinimum)
            logger.debug(
                "$this: " +
                        "agility=$field"
            )
        }

    private val agilityMaximum: Int = maxOf(1, (agilityMaximum ?: 0xFF)) // 255

    private val agilityMinimum: Int = maxOf(0, minOf((this.agilityMaximum - 1), (agilityMinimum ?: 0x03))) // 03

    private val agilityPercentage: Int
        get() = this.getPercentage(this.agility, this.agilityMaximum)

    val attackValue: Int
        get() = 0

    var damageResistance: Int = 0
        set(value) {
            field = this.getClampedValue(value, this.damageResistanceMaximum, this.damageResistanceMinimum)
            logger.debug(
                "$this: " +
                        "damageResistance=$field"
            )
        }

    private val damageResistanceMaximum: Int = maxOf(1, (damageResistanceMaximum ?: 0x34))

    private val damageResistanceMinimum: Int =
        maxOf(0, minOf((this.damageResistanceMaximum - 1), (damageResistanceMinimum ?: 0x0F)))

    private val decisions: List<Decision> = decisions.sortedByDescending { decision ->
        decision.priorityType.ordinal
    }

    val excellentMoveChanceMaximum: Int = maxOf(0, (excellentMoveChanceMaximum ?: 32))

    val excellentMoveChanceMinimum: Int = 0

    val healMoreScale: Int = maxOf(0x55, healMoreScale ?: 0)

    val healMoreShift: Int = maxOf(0x0F, healMoreShift ?: 0)

    val healRangeMaximum: Int = maxOf(1, (healRangeMaximum ?: 7))

    val healRangeMinimum: Int = minOf(this.healRangeMaximum, healRangeMinimum ?: 0)

    val healScale: Int = maxOf(0x0A, healScale ?: 0)

    val healShift: Int = maxOf(0x07, healShift ?: 0)

    private val herbCountMaximum: Int = maxOf(10, herbCountMaximum ?: 0)

    private val herbCountPercentage: Int
        get() = this.getPercentage(this.getItem(ItemType.HERB), this.herbCountMaximum)

    val herbScale: Int = maxOf(0x17, herbScale ?: 0)

    val herbShift: Int = maxOf(0x0F, herbShift ?: 0)

    var hitPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.hitPointsMaximum, value))
            logger.debug(
                "$this: " +
                        "hitPoints=$field"
            )
        }

    val hitPointsMaximum: Int = maxOf(0x03, hitPointsMaximum ?: 0)

    private val hitPointsPercentage: Int
        get() = getPercentage(this.hitPoints, this.hitPointsMaximum)

    val hurtMoreScale: Int = maxOf(0x1E, hurtMoreScale ?: 0)

    val hurtMoreShift: Int = maxOf(0x0F, hurtMoreShift ?: 0)

    val hurtRangeMaximum: Int = 255

    val hurtRangeMinimum: Int = 0

    val hurtRequirementMaximum: Int = 16

    val hurtRequirementMinimum: Int = 0

    val hurtScale: Int = maxOf(0x03, hurtScale ?: 0)

    val hurtShift: Int = maxOf(0x07, hurtShift ?: 0)

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

    val magicPointsMaximum: Int = maxOf(0, magicPointsMaximum ?: 0)

    private val magicPointsPercentage: Int
        get() = this.getPercentage(this.magicPoints, this.magicPointsMaximum)

    private val magicPotionsCountMaximum: Int = maxOf(10, magicPotionsCountMaximum ?: 0)

    private val magicPotionsPercentage: Int
        get() = this.getPercentage(this.getItem(ItemType.MAGIC_POTION), this.magicPotionsCountMaximum)

    val sleepRequirementMaximum: Int = 16

    val sleepRequirementMinimum: Int = 0

    var statusResistance: Int = 0
        set(value) {
            field = this.getClampedValue(value, this.statusResistanceMaximum, this.statusResistanceMinimum)
            logger.debug(
                "$this: " +
                        "statusResistance=$field"
            )
        }

    private val statusResistanceMaximum: Int = 0xFF // 255

    private val statusResistanceMinimum: Int = 0x00 // 0

    private val statusResistancePercentage: Int
        get() = this.getPercentage(this.statusResistance, this.statusResistanceMaximum)

    val stopSpellRequirementMaximum: Int = 16

    val stopSpellRequirementMinimum: Int = 0

    val statusSleep: Boolean
        get() = this.turnsSleep > 0

    val statusStopSpell: Boolean
        get() = this.turnsStopSpell > 0

    var strength: Int = 0
        set(value) {
            field = this.getClampedValue(value, this.strengthMaximum, this.strengthMinimum)
            logger.debug(
                "$this: " +
                        "strength=$field"
            )
        }

    private val strengthMaximum: Int = maxOf(1, strengthMaximum ?: 0x8C) // 140

    private val strengthMinimum: Int = maxOf(0, minOf((this.strengthMaximum - 1), (strengthMinimum ?: 0x05)))

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

    val turnsSleepMaximum: Int = maxOf(0, turnsSleepMaximum ?: 0)

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

    val turnsStopSpellMaximum: Int = maxOf(0, turnsStopSpellMaximum ?: 0)

    private val turnsStopSpellPercentage: Int
        get() = getPercentage(this.turnsStopSpell, this.turnsStopSpellMaximum)

    val wakeUpChanceMaximum: Int = maxOf(1, wakeUpChanceMaximum ?: 3)

    val wakeUpChanceMinimum: Int = maxOf(0, minOf(this.wakeUpChanceMaximum, wakeUpChanceMinimum ?: 0))

    fun addItem(itemType: ItemType, value: Int) {
        this.items[itemType]?.plus(
            when (itemType) {
                ItemType.HERB -> this.getClampedValue(value, this.herbCountMaximum, 0)
                ItemType.MAGIC_POTION -> this.getClampedValue(value, this.magicPotionsCountMaximum, 0)
            }
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
            ConditionType.MAGIC_POTIONS -> this.getItem(ItemType.MAGIC_POTION)
            ConditionType.STATUS_RESISTANCE -> this.statusResistanceMaximum
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
            ConditionType.STATUS_RESISTANCE -> this.statusResistancePercentage
            ConditionType.TURNS_SLEEP -> this.turnsSleepPercentage
            ConditionType.TURNS_STOP_SPELL -> this.turnsStopSpellPercentage
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
        this.agility = agility ?: this.agilityMinimum
        this.damageResistance = damageResistance ?: this.damageResistanceMinimum
        this.hitPoints = hitPoints ?: this.hitPointsMaximum
        this.magicPoints = magicPoints ?: this.hitPointsMaximum
        this.statusResistance = statusResistance ?: this.statusResistanceMinimum
        this.strength = strength ?: this.strengthMinimum
        this.turnsSleep = turnsSleep ?: 0
        this.turnsStopSpell = turnsStopSpell ?: 0

        logger.info(
            "$this: " +
                    "agility=${this.agility} " +
                    "agilityMaximum=${this.agilityMaximum} " +
                    "agilityMinimum=${this.agilityMinimum} " +
                    "allegiance=${this.allegiance} " +
                    "armor.name=${this.armor?.name} " +
                    "damageResistance=${this.damageResistance} " +
                    "damageResistanceMaximum=${this.damageResistanceMaximum} " +
                    "damageResistanceMinimum=${this.damageResistanceMinimum} " +
                    "decisions.size=${this.decisions.size} " +
                    "excellentMoveChanceMaximum=${this.excellentMoveChanceMaximum} " +
                    "excellentMoveChanceMinimum=${this.excellentMoveChanceMaximum} " +
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
                    "statusResistanceMaximum=${this.statusResistanceMaximum} " +
                    "statusResistanceMinimum=${this.statusResistanceMinimum} " +
                    "strength=${this.strength} " +
                    "strengthMaximum=${this.strengthMaximum} " +
                    "strengthMinimum=${this.strengthMinimum} " +
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
