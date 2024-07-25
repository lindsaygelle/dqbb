package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class Check<T>(
    expression: Expression,
    operator: Operator,
    priority: Priority,
    value: Comparable<T>,
) : Checker {

    private var expression: Expression = Expression.EXACT
        set(value) {
            field = value
            logger.debug("expression=$field")
        }

    private var operator: Operator = Operator.EQUAL
        set(value) {
            field = value
            logger.debug("operator=$field")
        }

    final override var priority: Priority = Priority.LOWEST
        set(value) {
            field = value
            logger.debug("priority=$field")
        }

    private var value: Comparable<T> = value
        set(value) {
            field = value
            logger.debug("value=$value")
        }

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
        logger.info("expression=$expression operator=$operator value=$value")
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

    init {
        this.expression = expression
        this.operator = operator
        this.priority = priority
        this.value = value
        logger.info(
            "expression=$expression " +
                    "operator=$operator " +
                    "priority=$priority " +
                    "value=$value"
        )
    }
}

class CheckActionPoints(
    expression: Expression,
    operator: Operator,
    priority: Priority,
    value: Int,
) : Check<Int>(
    expression = expression,
    operator = operator,
    priority = priority,
    value = value,
) {
    override fun getExact(actor: Actor): Int {
        val actionPoints = actor.actionPoints
        logger.debug("$actor.actionPoints=$actionPoints")
        return actionPoints
    }

    override fun getPercentage(actor: Actor): Int {
        val actionPointsPercentage = actor.actionPointsPercentage
        logger.debug("$actor.actionPointsPercentage=$actionPointsPercentage")
        return actionPointsPercentage
    }
}

class CheckHitPoints(
    expression: Expression,
    operator: Operator,
    priority: Priority,
    value: Int,
) : Check<Int>(
    expression = expression,
    operator = operator,
    priority = priority,
    value = value,
) {
    override fun getExact(actor: Actor): Int {
        val hitPoints = actor.hitPoints
        logger.debug("$actor.hitPoints=$hitPoints")
        return hitPoints
    }

    override fun getPercentage(actor: Actor): Int {
        val hitPointsPercentage = actor.hitPointsPercentage
        logger.debug("$actor.hitPointsPercentage=$hitPointsPercentage")
        return hitPointsPercentage
    }
}

class CheckMagicPoints(
    expression: Expression,
    operator: Operator,
    priority: Priority,
    value: Int,
) : Check<Int>(
    expression = expression,
    priority = priority,
    operator = operator,
    value = value,
) {
    override fun getExact(actor: Actor): Int {
        val magicPoints = actor.magicPoints
        logger.debug("$actor.magicPoints=$magicPoints")
        return magicPoints
    }

    override fun getPercentage(actor: Actor): Int {
        val magicPointsPercentage = actor.magicPointsPercentage
        logger.debug("$actor.magicPointsPercentage=$magicPointsPercentage")
        return magicPointsPercentage
    }
}

class CheckTurnsSleep(
    expression: Expression,
    operator: Operator,
    priority: Priority,
    value: Int,
) : Check<Int>(
    expression = expression,
    operator = operator,
    priority = priority,
    value = value,
) {
    override fun getExact(actor: Actor): Int {
        val turnsSleep = actor.turnsSleep
        logger.debug("$actor.turnsSleep=$turnsSleep")
        return turnsSleep
    }

    override fun getPercentage(actor: Actor): Int {
        val turnsSleepPercentage = actor.turnsSleepPercentage
        logger.debug("$actor.turnsSleepPercentage=$turnsSleepPercentage")
        return turnsSleepPercentage
    }
}

class CheckTurnsStopSpell(
    expression: Expression,
    operator: Operator,
    priority: Priority,
    value: Int,
) : Check<Int>(
    expression = expression,
    operator = operator,
    priority = priority,
    value = value,
) {
    override fun getExact(actor: Actor): Int {
        val turnsStopSpell = actor.turnsStopSpell
        logger.debug("$actor.turnsStopSpell=$turnsStopSpell")
        return turnsStopSpell
    }

    override fun getPercentage(actor: Actor): Int {
        val turnsStopSpellPercentage = actor.turnsStopSpellPercentage
        logger.debug("$actor.turnsStopSpellPercentage=$turnsStopSpellPercentage")
        return turnsStopSpellPercentage
    }
}
