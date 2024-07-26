package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class Actor(
    actionPoints: Int = Int.MAX_VALUE,
    actionPointsMaximum: Int = 1,
    agilityMaximum: Int,
    allegiance: Int,
    decisions: List<Decision>,
    excellentMoveChance: Int = 32,
    healMoreScale: Int = 0x55,
    healScale: Int = 0x0A,
    herbScale: Int = 0x17,
    hitPoints: Int = Int.MAX_VALUE,
    hitPointsMaximum: Int,
    statusResistance: Int = 0x0F,
    strengthMaximum: Int = 1,
    magicPoints: Int = Int.MAX_VALUE,
    magicPointsMaximum: Int,
    turnsSleep: Int = 0,
    turnsSleepMaximum: Int,
    turnsStopSpell: Int = 0,
    turnsStopSpellMaximum: Int,
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

    private val decisions: List<Decision> = decisions.sortedByDescending { decision -> decision.priority }

    val excellentMoveChance: Int = maxOf(0, excellentMoveChance)

    private val hasActionPoints: Boolean
        get() = this.actionPoints > 0

    private val healMoreScale: Int = maxOf(0x55, healMoreScale)

    val healMoreValue: Int
        get() = (0..7).random() and 0x0F + this.healMoreScale

    private val healScale: Int = maxOf(0x0A, healScale)

    val healValue: Int
        get() = (0..7).random() and 0x07 + this.healScale

    private val herbScale: Int = maxOf(0x0A, herbScale)

    val herbCount: Int
        get() = 0

    val herbCountPercentage: Int
        get() = 0

    val herbValue: Int
        get() = (0..7).random() and 0x0F + this.healScale

    var hitPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.hitPointsMaximum, value))
            logger.debug(
                "$this: " +
                        "hitPoints=$field"
            )
        }

    val hitPointsMaximum: Int = maxOf(1, hitPointsMaximum)

    val hitPointsPercentage: Int
        get() = getPercentage(this.hitPoints, this.hitPointsMaximum)

    val isAlive: Boolean
        get() = this.hitPoints > 0

    // protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

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

    val strengthMaximum: Int = maxOf(0, strengthMaximum)

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
        logger.debug(
            "$this: " +
                    "decision.id=$decision"
        )
        if (decision != null) {
            return decision.ability.use(this, decision.targetSelection.actors)
        }
        return false
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
