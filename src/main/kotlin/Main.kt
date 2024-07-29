package dqbb

fun main() {
    val actor = Actor(
        actionPointsMaximum = 2,
        agilityMaximum = 0,
        allegiance = 0,
        decisions = listOf(
            Decision(
                ability = MagicHeal(
                    condition = ConditionType.HIT_POINTS,
                    // orderBy = OrderBy.MIN,
                ),
                priority = PriorityType.HIGHEST,
                preCondition = State(
                    match = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(
                                CheckHitPoints(
                                    expression = ExpressionType.PERCENTAGE,
                                    operator = OperatorType.LESS_THAN,
                                    value = 10,
                                )
                            ),
                            match = MatchType.ANY,
                            target = TargetType.ALLY,
                        ),
                        Qualify(
                            checkers = listOf(
                                CheckMagicPoints(
                                    expression = ExpressionType.EXACT,
                                    operator = OperatorType.GREATER_THAN,
                                    value = 1
                                )
                            ),
                            match = MatchType.ALL,
                            target = TargetType.SELF,
                        )
                    ),
                ),
                targetSelection = State(
                    match = MatchType.ANY,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(
                                CheckHitPoints(
                                    expression = ExpressionType.PERCENTAGE,
                                    operator = OperatorType.LESS_THAN,
                                    value = 10,
                                )
                            ),
                            match = MatchType.ANY,
                            target = TargetType.ALLY,
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
                    condition = ConditionType.HIT_POINTS,
                    // orderBy = OrderBy.MIN,
                ),
                priority = PriorityType.HIGHEST,
                preCondition = State(
                    match = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(
                                CheckHitPoints(
                                    expression = ExpressionType.EXACT,
                                    operator = OperatorType.GREATER_THAN,
                                    value = 0,
                                )
                            ),
                            match = MatchType.ANY,
                            target = TargetType.ENEMY,
                        )
                    ),
                ),
                targetSelection = State(
                    match = MatchType.ANY,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(),
                            match = MatchType.ANY,
                            target = TargetType.ENEMY,
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
                    condition = ConditionType.HIT_POINTS,
                    // orderBy = OrderBy.MIN,
                ),
                priority = PriorityType.HIGHEST,
                preCondition = State(
                    match = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(
                                CheckHitPoints(
                                    expression = ExpressionType.EXACT,
                                    operator = OperatorType.GREATER_THAN,
                                    value = 0,
                                )
                            ),
                            match = MatchType.ANY,
                            target = TargetType.ENEMY,
                        )
                    ),
                ),
                targetSelection = State(
                    match = MatchType.ANY,
                    qualifiers = listOf(
                        Qualify(
                            checkers = listOf(),
                            match = MatchType.ANY,
                            target = TargetType.ENEMY,
                        )
                    )
                )
            ),
        ),
        hitPoints = 40,
        hitPointsMaximum = 40,
        magicPointsMaximum = 20,
        turnsSleepMaximum = 3,
        turnsStopSpellMaximum = 3,
    )

    val battleSystem = BattleSystem(mutableSetOf(actor, actor1, actor2))

    battleSystem.run()

    println(battleSystem.actors)
}
