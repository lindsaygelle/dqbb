package dqbb

abstract class Check<T>(
    private val attribute: Attribute,
    private val expression: Expression,
    private val operator: Operator,
    override val priority: Priority,
    private val value: Comparable<T>,
) : Checker {

    private fun checkValue(value: T): Boolean {
        var result = when (operator) {
            Operator.EQUAL -> this.value == value
            Operator.GREATER_THAN -> this.value < value  // This is annoying. It won't compile without swapping them.
            /* Unresolved reference. None of the following candidates is applicable because of receiver type mismatch:
            public fun String.compareTo(other: String, ignoreCase: Boolean = ...): Int defined in kotlin.text */
            Operator.LESS_THAN -> this.value > value  // This is annoying. It won't compile without swapping them.
        }
        println("${this::class.simpleName}: is ${this.value} $operator $value when $expression ? $result")
        return result
    }

    override fun check(actor: Actor): Boolean {
        println("${this::class.simpleName}: attribute=$attribute expression=$expression operator=$operator value=$value")
        return checkValue(
            when (expression) {
                Expression.EXACT -> getExact(actor)
                Expression.PERCENTAGE -> getPercentage(actor)
            }
        )
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
        println("$actor hitPointsPercentage=$hitPoints")
        return hitPoints
    }

    override fun getPercentage(actor: Actor): HitPoints {
        val hitPointsPercentage = actor.hitPointsPercentage
        println("$actor hitPointsPercentage=$hitPointsPercentage")
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
        return actor.magicPoints
    }

    override fun getPercentage(actor: Actor): MagicPoints {
        return actor.magicPointsPercentage
    }
}

class CheckStatusSleep(
    priority: Priority = Priority.HIGHEST,
    value: Boolean,
) : Check<Boolean>(
    attribute = Attribute.STATUS_SLEEP,
    expression = Expression.EXACT,
    priority = priority,
    operator = Operator.EQUAL,
    value = value,
) {
    override fun getExact(actor: Actor): Boolean {
        return actor.statusSleep
    }

    override fun getPercentage(actor: Actor): Boolean {
        return false
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
        return actor.turnsSleep
    }

    override fun getPercentage(actor: Actor): Turns {
        return actor.turnsSleepPercentage
    }
}
