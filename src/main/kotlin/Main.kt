package dqbb


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val actor = Actor(
        actionPointsMaximum = 2,
        allegiance = 0,
        decisions = listOf(
            Decision(
                ability = MagicHeal(
                    magicPoints = 2,
                ),
                apply = Qualifier(
                    checkers = listOf(
                        CheckHitPoints(
                            expression = Expression.PERCENTAGE,
                            operator = Operator.LESS_THAN,
                            priority = Priority.LOWEST,
                            value = 10
                        )
                    ),
                    priority = Priority.HIGHEST,
                    match = Match.OR,
                    target = Target.SELF,
                ),
                condition = Qualifier(
                    checkers = listOf(
                        CheckTurnsStopSpell(
                            expression = Expression.EXACT,
                            operator = Operator.GREATER_THAN,
                            priority = Priority.HIGHEST,
                            value = 0,
                        ),
                        CheckTurnsSleep(
                            expression = Expression.EXACT,
                            operator = Operator.GREATER_THAN,
                            priority = Priority.HIGHEST,
                            value = 0
                        ),
                        CheckMagicPoints(
                            expression = Expression.EXACT,
                            operator = Operator.GREATER_THAN,
                            priority = Priority.HIGHEST,
                            value = 2,
                        ),
                        CheckHitPoints(
                            expression = Expression.PERCENTAGE,
                            operator = Operator.LESS_THAN,
                            priority = Priority.LOWEST,
                            value = 25,
                        ),
                    ),
                    match = Match.ALL,
                    priority = Priority.HIGHEST,
                    target = Target.ALLY,
                ),
                priority = Priority.LOWEST,
            )
        ),
        hitPoints = 1,
        hitPointsMaximum = 22,
        magicPointsMaximum = 20,
        turnsSleepMaximum = 3,
        turnsStopSpellMaximum = 3,
    )

    val checker = CheckerBuilder.build(
        CheckConfig(
            "HIT_POINTS",
            "EXACT",
            "EQUAL",
            "LOWEST",
            1
        )
    )
    println(checker)

    val q = QualifierBuilder.build(
        QualifierConfig(
            checkers = listOf(
                CheckConfig(
                    attribute = "HIT_POINTS",
                    expression = "EXACT",
                    operator = "EQUAL",
                    priority = "HIGHEST",
                    value = 1
                )
            ),
            match = "OR",
            priority = "HIGHEST",
            target = "SELF",
        )
    )

    println(q)

    ConsumableHerb().use(actor, actor)
}
