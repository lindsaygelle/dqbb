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
                        CheckTurnsSleep(
                            expression = Expression.EXACT,
                            operator = Operator.GREATER_THAN,
                            priority = Priority.HIGHEST,
                            value = 0
                        ),
                        CheckHitPoints(
                            expression = Expression.PERCENTAGE,
                            operator = Operator.LESS_THAN,
                            priority = Priority.LOWEST,
                            value = 25,
                        ),
                    ),
                    match = Match.OR,
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

    actor.performDecisions(listOf(actor, actor))
    /*
    val a = Qualifier(
        checkers = listOf(
            CheckHitPoints(
                expression = Expression.EXACT,
                operator = Operator.LESS_THAN,
                value = 4,
            )
        )
    )
    val b = Qualifier(
        checkers = listOf(
            CheckHitPoints(
                expression = Expression.EXACT,
                operator = Operator.LESS_THAN,
                value = 2,
            )
        ),
        priority = Priority.HIGHEST,
        target = Target.SELF,
    )

    val qA = a.check(actor, listOf(actor))
    println(qA)
    val qB = b.check(actor, qA)
    println(qB)
     */
}
