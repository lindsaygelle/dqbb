package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


open class Actor(
    actionPoints: Int? = null,
    actionPointsMaximum: Int? = null,
    agility: Int? = null,
    val allegiance: Int,
    var armor: Armor? = null,
    damageResistanceMaximum: Int? = null,
    decisions: List<Decision>,
    excellentMoveChanceMaximum: Int? = null,
    excellentMoveChanceMinimum: Int? = null,
    healMoreScale: Int? = null,
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
    val items: MutableMap<ItemType, Int> = mutableMapOf(),
    magicPoints: Int? = null,
    magicPointsMaximum: Int? = null,
    magicPotionsMaximum: Int? = null,
    var shield: Shield? = null,
    statusResistance: Int? = null,
    strength: Int? = null,
    turnsSleep: Int? = null,
    turnsSleepMaximum: Int? = null,
    turnsStopSpell: Int? = null,
    turnsStopSpellMaximum: Int? = null,
    wakeUpChanceMaximum: Int? = null,
    wakeUpChanceMinimum: Int? = null,
    var weapon: Weapon? = null,
) : Identifier {

    var actionPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.actionPointsMaximum, value))
            logger.debug(
                "$this: " +
                        "actionPoints=$field"
            )
        }

    val actionPointsMaximum: Int = maxOf(0, actionPointsMaximum ?: 1)

    val actionPointsPercentage: Int
        get() = this.getPercentage(this.actionPoints, this.actionPointsMaximum)

    var agility: Int = 0
        set(value) {
            field = maxOf(this.agilityMinimum, minOf(this.agilityMaximum))
        }

    private val agilityMaximum: Int = 0xFF

    private val agilityMinimum: Int = 0x03

    private val agilityPercentage: Int
        get() = this.getPercentage(this.agility, this.agilityMaximum)

    val attackValue: Int
        get() = 0

    val damageResistanceMaximum: Int = maxOf(0x0F, damageResistanceMaximum ?: 0)

    private val decisions: List<Decision> = decisions.sortedByDescending { decision ->
        decision.priorityType.ordinal
    }

    val excellentMoveChanceMaximum: Int = maxOf(0, excellentMoveChanceMaximum ?: 0)

    val excellentMoveChanceMinimum: Int = maxOf(0, excellentMoveChanceMinimum ?: 0)

    val healMoreScale: Int = maxOf(0x55, healMoreScale ?: 0)

    val healMoreShift: Int = 0x0F

    val healRangeMaximum: Int = 7

    val healRangeMinimum: Int = 0

    val healScale: Int = maxOf(0x0A, healScale ?: 0)

    val healShift: Int = maxOf(0x07, healShift ?: 0)

    private val herbCountMaximum: Int = maxOf(1, herbCountMaximum ?: 0)

    val herbCountPercentage: Int
        get() = this.getPercentage(this.items.getOrDefault(ItemType.HERB, 0), this.herbCountMaximum)

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

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

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

    private val magicPotionsMaximum: Int = maxOf(1, magicPotionsMaximum ?: 0)

    private val magicPotionsPercentage: Int
        get() = this.getPercentage(this.items.getOrDefault(ItemType.MAGIC_POTION, 0), this.magicPotionsMaximum)

    val sleepRequirementMaximum: Int = 16

    val sleepRequirementMinimum: Int = 0

    var statusResistance: Int = 0
        set(value) {
            field = maxOf(value, this.statusResistanceMaximum, this.statusResistanceMinimum)
        }

    private val statusResistanceMaximum: Int = 0xFF

    private val statusResistanceMinimum: Int = 0x00

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
        }

    private val strengthMaximum: Int = 0x8C

    private val strengthMinimum: Int = 0x05

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

    val turnsSleepPercentage: Int
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

    fun getAttackPower(actor: Actor): Int {
        return this.strength
    }


    private fun getClampedValue(value: Int, valueMaximum: Int, valueMinimum: Int): Int {
        return maxOf(valueMinimum, minOf(valueMaximum, value))
    }

    fun getConditionType(conditionType: ConditionType): Int {
        return when (conditionType) {
            ConditionType.AGILITY -> this.agility
            ConditionType.HERBS -> this.items.getOrDefault(ItemType.HERB, 0)
            ConditionType.HIT_POINTS -> this.hitPoints
            ConditionType.MAGIC_POINTS -> this.magicPoints
            ConditionType.MAGIC_POTIONS -> this.items.getOrDefault(ItemType.MAGIC_POTION, 0)
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

    fun getDefensePower(actor: Actor): Int {
        return this.agility / 2
    }

    private fun getPercentage(value: Int, valueMaximum: Int): Int {
        return ((value.toDouble() / valueMaximum) * 100).toInt()
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
        this.actionPoints = actionPoints ?: 1
        this.hitPoints = hitPoints ?: this.hitPointsMaximum
        this.magicPoints = magicPoints ?: this.hitPointsMaximum
        this.statusResistance = statusResistance ?: this.statusResistanceMinimum
        this.strength = strength ?: this.strengthMinimum
        this.turnsSleep = turnsSleep ?: 0
        this.turnsStopSpell = turnsStopSpell ?: 0
    }
}
