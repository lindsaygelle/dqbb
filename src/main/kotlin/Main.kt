package dqbb

fun main() {
    val actor = Actor(
        actionPointsMaximum = 2,
        agilityMaximum = 0,
        allegiance = 0,
        decisions = listOf(
            Decision(
                ability = MagicHeal(
                    conditionType = ConditionType.HIT_POINTS,
                    // orderBy = OrderBy.MIN,
                ),
                priorityType = PriorityType.HIGHEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(
                                CheckHitPoints(
                                    expressionType = ExpressionType.PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN,
                                    value = 10,
                                )
                            ),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ALLY,
                        ),
                        Qualify(
                            checkers = listOf(
                                CheckMagicPoints(
                                    expressionType = ExpressionType.EXACT,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 1
                                )
                            ),
                            matchType = MatchType.ALL,
                            targetType = TargetType.SELF,
                        )
                    ),
                ),
                targetSelection = State(
                    matchType = MatchType.ANY,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(
                                CheckHitPoints(
                                    expressionType = ExpressionType.PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN,
                                    value = 10,
                                )
                            ),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ALLY,
                        )
                    )
                )
            ),
        ),
        hitPoints = 20,
        hitPointsMaximum = 20,
        magicPointsMaximum = 10,
        turnsSleepMaximum = 3,
        turnsStopSpellMaximum = 3,
    )

    val actor1 = Actor(
        actionPointsMaximum = 2,
        agilityMaximum = 1,
        allegiance = 0,
        decisions = listOf(
            Decision(
                ability = MagicHurt(
                    conditionType = ConditionType.HIT_POINTS,
                    // orderBy = OrderBy.MIN,
                ),
                priorityType = PriorityType.HIGHEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(
                                CheckHitPoints(
                                    expressionType = ExpressionType.EXACT,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 0,
                                )
                            ),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ENEMY,
                        )
                    ),
                ),
                targetSelection = State(
                    matchType = MatchType.ANY,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ENEMY,
                        )
                    )
                )
            ),
        ),
        hitPoints = 20,
        hitPointsMaximum = 20,
        magicPoints = 20,
        magicPointsMaximum = 20,
        turnsSleepMaximum = 3,
        turnsStopSpellMaximum = 3,
    )

    val actor2 = Actor(
        actionPointsMaximum = 4,
        agilityMaximum = 10,
        allegiance = 1,
        decisions = listOf(
            Decision(
                ability = Attack(
                    conditionType = ConditionType.HIT_POINTS,
                    // orderBy = OrderBy.MIN,
                ),
                priorityType = PriorityType.LOWEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(
                                CheckHitPoints(
                                    expressionType = ExpressionType.EXACT,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 0,
                                )
                            ),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ENEMY,
                        )
                    ),
                ),
                targetSelection = State(
                    matchType = MatchType.ANY,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ENEMY,
                        )
                    )
                )
            ),
            Decision(
                ability = MagicSleep(
                    conditionType = ConditionType.HIT_POINTS,
                    // orderBy = OrderBy.MIN,
                ),
                priorityType = PriorityType.HIGHEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(
                                CheckTurnsSleep(
                                    expressionType = ExpressionType.EXACT,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0,
                                )
                            ),
                            matchType = MatchType.ANY,
                            priorityType = PriorityType.HIGH,
                            targetType = TargetType.ENEMY,
                        ),
                        Qualify(
                            checkers = listOf(
                                CheckMagicPoints(
                                    expressionType = ExpressionType.EXACT,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 9,
                                )
                            ),
                            matchType = MatchType.ANY,
                            priorityType = PriorityType.HIGHEST,
                            targetType = TargetType.SELF,
                        )
                    ),
                ),
                targetSelection = State(
                    matchType = MatchType.ANY,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(
                                CheckTurnsSleep(
                                    expressionType = ExpressionType.EXACT,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0,
                                ),
                            ),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ENEMY,
                        )
                    )
                )
            ),
        ),
        hitPoints = 100,
        hitPointsMaximum = 100,
        magicPoints = 20,
        magicPointsMaximum = 20,
        turnsSleepMaximum = 3,
        turnsStopSpellMaximum = 3,
    )

    val battleSystem = BattleSystem(mutableSetOf(actor, actor1, actor2))

    battleSystem.run()

    println(battleSystem.actors)
}
