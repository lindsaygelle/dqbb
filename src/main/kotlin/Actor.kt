package dqbb

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
    turnsSleepMaximum: Turns
) {

    private var actionPoints: ActionPoints = 0
        set(value) {
            field = maxOf(0, minOf(this.actionPointsMaximum, value))
        }

    private var actionPointsMaximum: ActionPoints = 0
        set(value) {
            field = maxOf(0, value)
        }

    var allegiance: Int = maxOf(0, allegiance)
        set(value) {
            field = maxOf(0, value)
        }

    private val decisions: List<Decision> = decisions.sortedBy { decision -> decision.priority }

    private val hasActionPoints: Boolean
        get() = this.actionPoints > 0

    var hitPoints: HitPoints = 0
        set(value) {
            field = maxOf(0, minOf(this.hitPointsMaximum, value))
            println("hitPoints=$field")
        }

    private var hitPointsMaximum: HitPoints = 0
        set(value) {
            field = maxOf(0, value)
        }

    val hitPointsPercentage: HitPoints
        get() = getPercentage(this.hitPoints, this.hitPointsMaximum)

    val isAlive: Boolean
        get() = this.hitPoints > 0

    var magicPoints: MagicPoints = 0
        set(value) {
            field = maxOf(0, minOf(this.magicPointsMaximum, value))
        }

    private var magicPointsMaximum: MagicPoints = 0
        set(value) {
            field = maxOf(0, value)
        }

    val magicPointsPercentage: MagicPoints
        get() = getPercentage(this.magicPoints, this.magicPointsMaximum)

    val statusSleep: Boolean
        get() = this.turnsSleep > 0

    var turnsSleep: Turns = 0
        set(value) {
            field = maxOf(0, minOf(this.turnsSleepMaximum, value))
        }
    private var turnsSleepMaximum: Turns = 0
        set(value) {
            field = maxOf(0, value)
        }

    val turnsSleepPercentage: Turns
        get() = getPercentage(this.turnsSleep, this.turnsSleepMaximum)

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
        println("ability=${ability::class.simpleName}")
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
    }
}
