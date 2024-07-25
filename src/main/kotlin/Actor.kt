package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class Actor(
    actionPoints: Int = Int.MAX_VALUE,
    actionPointsMaximum: Int,
    allegiance: Int,
    decisions: List<Decision>,
    healMoreScale: Int = 0x55,
    healScale: Int = 0x0A,
    herbScale: Int = 0x17,
    hitPoints: Int = Int.MAX_VALUE,
    hitPointsMaximum: Int,
    val items: MutableMap<Item, Int> = mutableMapOf(),
    statusResistance: Int = 0x0F,
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
            logger.debug("actionPoints=$field")
        }

    private var actionPointsMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("actionPointsMaximum=$field")
        }

    val actionPointsPercentage: Int
        get() = this.getPercentage(this.actionPoints, this.actionPointsMaximum)

    private var allegiance: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("allegiance=$field")
        }

    private var decisions: List<Decision> = listOf()
        set(value) {
            field = value.sortedBy { decision: Decision -> decision.priority }
            logger.debug("decisions.size=${field.size}")
        }

    private val hasActionPoints: Boolean
        get() = this.actionPoints > 0

    private var healMoreScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("healMoreScale=$field")
        }

    val healMoreValue: Int
        get() = (0..7).random() and 0x0F + this.healMoreScale

    private var healScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("healScale=$field")
        }

    val healValue: Int
        get() = (0..7).random() and 0x07 + this.healScale

    private var herbScale: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("herbScale=$field")
        }

    val herbValue: Int
        get() = (0..7).random() and 0x0F + this.healScale

    var hitPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.hitPointsMaximum, value))
            logger.debug("hitPoints=$field")
        }

    private var hitPointsMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("hitPointsMaximum=$field")
        }

    val hitPointsPercentage: Int
        get() = getPercentage(this.hitPoints, this.hitPointsMaximum)

    val isAlive: Boolean
        get() = this.hitPoints > 0

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    var magicPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.magicPointsMaximum, value))
            logger.debug("magicPoints=$field")
        }

    private var magicPointsMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("magicPointsMaximum=$field")
        }

    val magicPointsPercentage: Int
        get() = getPercentage(this.magicPoints, this.magicPointsMaximum)

    var statusResistance: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("statusResistance=$field")
        }

    val statusSleep: Boolean
        get() = this.turnsSleep > 0

    val statusStopSpell: Boolean
        get() = this.turnsStopSpell > 0

    var turnsSleep: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.turnsSleepMaximum, value))
            logger.debug("turnsSleep=$field")
        }
    private var turnsSleepMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("turnsSleepMaximum=$field")
        }

    val turnsSleepPercentage: Int
        get() = getPercentage(this.turnsSleep, this.turnsSleepMaximum)

    var turnsStopSpell: Int = 0
        set(value) {
            field = maxOf(0, minOf(this.turnsStopSpellMaximum, value))
            logger.debug("turnsStopSpell=$field")
        }

    private var turnsStopSpellMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("turnsStopSpellMaximum=$field")
        }

    val turnsStopSpellPercentage: Int
        get() = getPercentage(this.turnsStopSpell, this.turnsStopSpellMaximum)

    fun checkAlly(actor: Actor): Boolean {
        return this.allegiance == actor.allegiance
    }

    fun checkEnemy(actor: Actor): Boolean {
        return !this.checkAlly(actor)
    }

    private fun getPercentage(value: Int, valueMaximum: Int): Int {
        return ((value.toDouble() / valueMaximum) * 100).toInt()
    }

    private fun performAbility(ability: Ability, actor: Actor): Boolean {
        logger.info("ability=${ability::class.simpleName}")
        return ability.use(this, actor)
    }

    fun performDecisions(actors: List<Actor>) {
        this.decisions.forEach { decision ->
            if (decision.isValid(this, actors)) {
                performAbility(decision.ability, decision.actors.random())
            }
        }
    }

    init {
        this.actionPointsMaximum = actionPointsMaximum
        this.actionPoints = actionPoints
        this.allegiance = allegiance
        this.decisions = decisions
        this.healMoreScale = healMoreScale
        this.healScale = healScale
        this.herbScale = herbScale
        this.hitPointsMaximum = hitPointsMaximum
        this.hitPoints = hitPoints
        this.magicPointsMaximum = magicPointsMaximum
        this.magicPoints = magicPoints
        this.statusResistance = statusResistance
        this.turnsSleepMaximum = turnsSleepMaximum
        this.turnsSleep = turnsSleep
        this.turnsStopSpellMaximum = turnsStopSpellMaximum
        this.turnsStopSpell = turnsStopSpell

        logger.info(
            "actionPointsMaximum=${this.actionPointsMaximum} " +
                    "actionPoints=${this.actionPoints} " +
                    "allegiance=${this.allegiance} " +
                    "decisions.size=${this.decisions.size} " +
                    "healMoreScale=${this.healMoreScale} " +
                    "healScale=${this.healScale} " +
                    "herbScale=${this.herbScale} " +
                    "hitPointsMaximum=${this.hitPointsMaximum} " +
                    "hitPoints=${this.hitPoints} " +
                    "magicPointsMaximum=${this.magicPointsMaximum} " +
                    "magicPoints=${this.magicPoints} " +
                    "statusResistance=${this.statusResistance} " +
                    "turnsSleepMaximum=${this.turnsSleepMaximum} " +
                    "turnsSleep=${this.turnsSleep} " +
                    "turnsStopSpellMaximum=${this.turnsStopSpellMaximum} " +
                    "turnsStopSpell=${this.turnsStopSpell}"
        )
    }
}
