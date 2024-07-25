package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class Check<T>(
    private val attribute: Attribute,
    private val expression: Expression,
    private val operator: Operator,
    override val priority: Priority,
    private val value: Comparable<T>,
) : Checker {

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private fun checkValue(value: T): Boolean {
        return when (operator) {
            Operator.EQUAL -> this.value == value
            Operator.GREATER_THAN -> this.value < value  // This is annoying. It won't compile without swapping them.
            /* Unresolved reference. None of the following candidates is applicable because of receiver type mismatch:
            public fun String.compareTo(other: String, ignoreCase: Boolean = ...): Int defined in kotlin.text */
            Operator.LESS_THAN -> this.value > value  // This is annoying. It won't compile without swapping them.
        }
    }

    override fun check(actor: Actor): Boolean {
        logger.info("attribute=$attribute expression=$expression operator=$operator value=$value")
        val result = checkValue(
            when (expression) {
                Expression.EXACT -> getExact(actor)
                Expression.PERCENTAGE -> getPercentage(actor)
            }
        )
        logger.info("result=$result")
        return result
    }

    protected abstract fun getExact(actor: Actor): T
    protected abstract fun getPercentage(actor: Actor): T
}

class CheckHitPoints(
    expression: Expression,
    operator: Operator,
    priority: Priority,
    value: HitPoints,
) : Check<HitPoints>(
    attribute = Attribute.HIT_POINTS,
    expression = expression,
    operator = operator,
    priority = priority,
    value = value,
) {
    override fun getExact(actor: Actor): HitPoints {
        val hitPoints = actor.hitPoints
        logger.debug("$actor.hitPoints=$hitPoints")
        return hitPoints
    }

    override fun getPercentage(actor: Actor): HitPoints {
        val hitPointsPercentage = actor.hitPointsPercentage
        logger.debug("$actor.hitPointsPercentage=$hitPointsPercentage")
        return hitPointsPercentage
    }
}

class CheckMagicPoints(
    expression: Expression,
    operator: Operator,
    priority: Priority,
    value: MagicPoints,
) : Check<MagicPoints>(
    attribute = Attribute.MAGIC_POINTS,
    expression = expression,
    priority = priority,
    operator = operator,
    value = value,
) {
    override fun getExact(actor: Actor): MagicPoints {
        val magicPoints = actor.magicPoints
        logger.debug("$actor.magicPoints=$magicPoints")
        return magicPoints
    }

    override fun getPercentage(actor: Actor): MagicPoints {
        val magicPointsPercentage = actor.magicPointsPercentage
        logger.debug("$actor.magicPointsPercentage=$magicPointsPercentage")
        return magicPointsPercentage
    }
}

class CheckTurnsSleep(
    expression: Expression,
    operator: Operator,
    priority: Priority,
    value: Turns,
) : Check<Turns>(
    attribute = Attribute.TURNS_SLEEP,
    expression = expression,
    operator = operator,
    priority = priority,
    value = value,
) {
    override fun getExact(actor: Actor): Turns {
        val turnsSleep = actor.turnsSleep
        logger.debug("$actor.turnsSleep=$turnsSleep")
        return turnsSleep
    }

    override fun getPercentage(actor: Actor): Turns {
        val turnsSleepPercentage = actor.turnsSleepPercentage
        logger.debug("$actor.turnsSleepPercentage=$turnsSleepPercentage")
        return turnsSleepPercentage
    }
}

class CheckTurnsStopSpell(
    expression: Expression,
    operator: Operator,
    priority: Priority,
    value: Turns,
) : Check<Turns>(
    attribute = Attribute.TURNS_SLEEP,
    expression = expression,
    operator = operator,
    priority = priority,
    value = value,
) {
    override fun getExact(actor: Actor): Turns {
        val turnsStopSpell = actor.turnsStopSpell
        logger.debug("$actor.turnsStopSpell=$turnsStopSpell")
        return turnsStopSpell
    }

    override fun getPercentage(actor: Actor): Turns {
        val turnsStopSpellPercentage = actor.turnsStopSpellPercentage
        logger.debug("$actor.turnsStopSpellPercentage=$turnsStopSpellPercentage")
        return turnsStopSpellPercentage
    }
}
