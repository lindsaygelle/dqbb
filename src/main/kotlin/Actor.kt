package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class Actor(
    actionPoints: Int = -1,
    actionPointsMaximum: Int = -1,
    agilityMaximum: Int = -1,
    allegiance: Int,
    damageResistance: Int = -1,
    decisions: List<Decision>,
    excellentMoveMaximum: Int = -1,
    excellentMoveMinimum: Int = -1,
    healMoreScale: Int = -1,
    healScale: Int = -1,
    healShift: Int = -1,
    herbScale: Int = -1,
    herbShift: Int = -1,
    hitPoints: Int = -1,
    hitPointsMaximum: Int = -1,
    hurtMoreScale: Int = -1,
    hurtMoreShift: Int = -1,
    hurtScale: Int = -1,
    hurtShift: Int = -1,
    val items: MutableMap<ItemType, Int> = mutableMapOf(),
    statusResistance: Int = -1,
    strengthMaximum: Int = -1,
    magicPoints: Int = -1,
    magicPointsMaximum: Int = -1,
    turnsSleep: Int = -1,
    turnsSleepMaximum: Int = -1,
    turnsStopSpell: Int = -1,
    turnsStopSpellMaximum: Int = -1,
) {

    var actionPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.actionPointsMaximum, value))
            logger.debug(
                "$this: " +
                        "actionPoints=$field"
            )
        }

    val actionPointsMaximum: Int = maxOf(0, actionPointsMaximum)

    val actionPointsPercentage: Int
        get() = this.getPercentage(this.actionPoints, this.actionPointsMaximum)

    val agility: Int
        get() = this.agilityMaximum

    private val agilityMaximum: Int = maxOf(0, agilityMaximum)

    var allegiance: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "$this: " +
                        "allegiance=$field"
            )
        }

    val attackValue: Int
        get() = 0

    val damageResistance: Int = maxOf(0x0F, damageResistance)

    private val decisions: List<Decision> = decisions.sortedByDescending { decision -> decision.priority }

    val excellentMoveMaximum: Int = maxOf(0, excellentMoveMaximum)

    val excellentMoveMinimum: Int = maxOf(0, excellentMoveMinimum)

    private val hasActionPoints: Boolean
        get() = this.actionPoints > 0

    val healMoreScale: Int = maxOf(0x55, healMoreScale)

    val healMoreShift: Int = 0x0F

    val healRangeMaximum: Int = 7

    val healRangeMinimum: Int = 0

    val healScale: Int = maxOf(0x0A, healScale)

    val healShift: Int = maxOf(0x07, healShift)

    val herbScale: Int = maxOf(0x17, herbScale)

    val herbShift: Int = maxOf(0x0F, herbShift)

    var hitPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.hitPointsMaximum, value))
            logger.debug(
                "$this: " +
                        "hitPoints=$field"
            )
        }

    val hitPointsMaximum: Int = maxOf(0x03, hitPointsMaximum)

    val hitPointsPercentage: Int
        get() = getPercentage(this.hitPoints, this.hitPointsMaximum)

    val hurtMoreScale: Int = maxOf(0x1E, hurtMoreScale)

    val hurtMoreShift: Int = maxOf(0x0F, hurtMoreShift)

    val hurtRangeMaximum: Int = 255

    val hurtRangeMinimum: Int = 0

    private val hurtRange: IntRange = (this.hurtRangeMinimum..hurtRangeMaximum)

    val hurtRangeRandom: Int
        get() = this.hurtRange.random()

    val hurtRequirementMaximum: Int = 16

    val hurtRequirementMinimum: Int = 0

    private val hurtRequirementRange: IntRange = (this.hurtRequirementMinimum..hurtRequirementMaximum)

    val hurtRequirement: Int
        get() = this.hurtRequirementRange.random()

    val hurtResistance: Int = this.damageResistance shr 4

    val hurtScale: Int = maxOf(0x03, hurtScale)

    val hurtShift: Int = maxOf(0x07, hurtShift)

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

    val magicPointsMaximum: Int = maxOf(0, magicPointsMaximum)

    val magicPointsPercentage: Int
        get() = getPercentage(this.magicPoints, this.magicPointsMaximum)

    val statusResistance: Int
        get() = this.statusResistanceMaximum

    private val statusResistanceMaximum: Int = maxOf(0x0F, statusResistance)

    val statusSleep: Boolean
        get() = this.turnsSleep > 0

    val statusStopSpell: Boolean
        get() = this.turnsStopSpell > 0

    val strength: Int
        get() = this.strengthMaximum

    val strengthMaximum: Int = maxOf(0x05, strengthMaximum)

    var turnsSleep: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.turnsSleepMaximum, value))
            logger.debug(
                "$this: " +
                        "turnsSleep=$field"
            )
        }

    val turnsSleepMaximum: Int = maxOf(0, turnsSleepMaximum)

    val turnsSleepPercentage: Int
        get() = getPercentage(this.turnsSleep, this.turnsSleepMaximum)

    var turnsStopSpell: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.turnsStopSpellMaximum, value))
            logger.debug(
                "$this: " +
                        "turnsStopSpell=$field"
            )
        }

    val turnsStopSpellMaximum: Int = maxOf(0, turnsStopSpellMaximum)

    val turnsStopSpellPercentage: Int
        get() = getPercentage(this.turnsStopSpell, this.turnsStopSpellMaximum)

    fun getAttackPower(actor: Actor): Int {
        return this.strength
    }

    private fun getDecision(otherActors: List<Actor>): Decision? {
        for (decision in decisions) {
            val isValid = decision.isValid(this, otherActors)
            logger.debug(
                "$this: " +
                        "decision.ability=${decision.ability} " +
                        "decision.id=$decision " +
                        "decision.isValid=$isValid"
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

    fun takeTurn(otherActors: List<Actor>): Boolean {
        val decision = getDecision(otherActors)
        var decisionValue = false
        logger.debug(
            "$this: " +
                    "decision.id=$decision " +
                    "otherActors.size=${otherActors.size}"
        )
        if (decision != null) {
            decisionValue = decision.ability.use(this, decision.targetSelection.actors)
            logger.debug(
                "$this: " +
                        "decision.ability.id=${decision.ability} " +
                        "decision.ability.use=$decisionValue " +
                        "decision.id=$decision"
            )
        }
        return decisionValue
    }

    init {
        this.actionPoints = actionPoints
        this.allegiance = allegiance
        this.hitPoints = hitPoints
        this.magicPoints = magicPoints
        this.turnsSleep = turnsSleep
        this.turnsStopSpell = turnsStopSpell
    }
}
