package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

typealias ActionPoints = Int
typealias HitPoints = Int
typealias MagicPoints = Int
typealias Turns = Int

class Actor(
    actionPoints: Int = ActionPoints.MAX_VALUE,
    actionPointsMaximum: Int,
    allegiance: Int,
    decisions: List<Decision>,
    hitPoints: HitPoints = HitPoints.MAX_VALUE,
    hitPointsMaximum: HitPoints,
    magicPoints: MagicPoints = MagicPoints.MAX_VALUE,
    magicPointsMaximum: MagicPoints,
    turnsSleep: Turns = 0,
    turnsSleepMaximum: Turns,
    turnsStopSpell: Turns = 0,
    turnsStopSpellMaximum: Turns,
) {

    private var actionPoints: ActionPoints = 0
        set(value) {
            field = maxOf(0, minOf(this.actionPointsMaximum, value))
            logger.debug("actionPoints=$field")
        }

    private var actionPointsMaximum: ActionPoints = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("actionPointsMaximum=$field")
        }

    private var allegiance: Int = maxOf(0, allegiance)
        set(value) {
            field = maxOf(0, value)
            logger.debug("allegiance=$field")
        }

    private val decisions: List<Decision> = decisions.sortedBy { decision -> decision.priority }

    private val hasActionPoints: Boolean
        get() = this.actionPoints > 0

    val healValue: HitPoints
        get() = (0..7).random() and 0x07 + 0x0A

    var hitPoints: HitPoints = 0
        set(value) {
            field = maxOf(0, minOf(this.hitPointsMaximum, value))
            logger.debug("hitPoints=$field")
        }

    private var hitPointsMaximum: HitPoints = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("hitPointsMaximum=$field")
        }

    val hitPointsPercentage: HitPoints
        get() = getPercentage(this.hitPoints, this.hitPointsMaximum)

    val isAlive: Boolean
        get() = this.hitPoints > 0

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)
    var magicPoints: MagicPoints = 0
        set(value) {
            field = maxOf(0, minOf(this.magicPointsMaximum, value))
            logger.debug("magicPoints=$field")
        }

    private var magicPointsMaximum: MagicPoints = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("magicPointsMaximum=$field")
        }

    val magicPointsPercentage: MagicPoints
        get() = getPercentage(this.magicPoints, this.magicPointsMaximum)

    val statusSleep: Boolean
        get() = this.turnsSleep > 0

    val statusStopSpell: Boolean
        get() = this.turnsStopSpell > 0

    var turnsSleep: Turns = 0
        set(value) {
            field = maxOf(0, minOf(this.turnsSleepMaximum, value))
            logger.debug("turnsSleep=$field")
        }
    private var turnsSleepMaximum: Turns = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug("turnsSleepMaximum=$field")
        }

    val turnsSleepPercentage: Turns
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

    val turnsStopSpellPercentage: Turns
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
        this.hitPointsMaximum = hitPointsMaximum
        this.hitPoints = hitPoints
        this.magicPointsMaximum = magicPointsMaximum
        this.magicPoints = magicPoints
        this.turnsSleepMaximum = turnsSleepMaximum
        this.turnsSleep = turnsSleep
        this.turnsStopSpellMaximum = turnsStopSpellMaximum
        this.turnsStopSpell = turnsStopSpell
    }
}
