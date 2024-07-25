package dqbb

object CheckerBuilder {
    fun build(checkConfig: CheckConfig): Checker? {
        val attribute = AttributeMatcher.match(checkConfig.attribute)
        if (attribute == null) {
            return null
        }
        val expression = ExpressionMatcher.match(checkConfig.expression)
        if (expression == null) {
            return null
        }
        val operator = OperatorMatcher.match(checkConfig.operator)
        if (operator == null) {
            return null
        }
        val priority = PriorityMatcher.match(checkConfig.priority)
        if (priority == null) {
            return null
        }
        return when (attribute) {
            Attribute.ACTION_POINTS -> CheckActionPoints(
                expression = expression,
                operator = operator,
                priority = priority,
                value = checkConfig.value as Int
            )

            Attribute.HIT_POINTS -> CheckHitPoints(
                expression = expression,
                operator = operator,
                priority = priority,
                value = checkConfig.value as Int
            )

            Attribute.MAGIC_POINTS -> CheckMagicPoints(
                expression = expression,
                operator = operator,
                priority = priority,
                value = checkConfig.value as Int
            )

            Attribute.TURNS_SLEEP -> CheckTurnsSleep(
                expression = expression,
                operator = operator,
                priority = priority,
                value = checkConfig.value as Int
            )

            Attribute.TURNS_STOP_SPELL -> CheckTurnsStopSpell(
                expression = expression,
                operator = operator,
                priority = priority,
                value = checkConfig.value as Int
            )
        }
    }
}