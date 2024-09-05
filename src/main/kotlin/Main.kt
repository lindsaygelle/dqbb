package dqbb

fun main() {

    val attackEnemy = AttackEnemy<Actor, Actor>()
    val attackHero = AttackHero<Actor, Actor>()
    val heal = Heal<Actor, Actor>(2)
    val healMore = HealMore<Actor, Actor>(10)
    val herb = Herb<Actor, Actor>()
    val hurt = Hurt<Actor, Actor>(2)
    val run = Run<Actor, Actor>()
    val sleep = Sleep<Actor, Actor>(2)
    val stopSpell = StopSpell<Actor, Actor>(2)

    val actionAttackEnemy = Action(
        ability = attackEnemy,
        actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.ENEMY
                )
            )
        ),
        actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ),
            selectionType = SelectionType.ENEMY
        ),
        attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS,
            sortType = SortType.DESCENDING
        ),
        priorityType = PriorityType.LOW
    )

    val actionAttackHero = Action(
        ability = attackHero,
        actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.ENEMY
                )
            )
        ),
        actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ),
            selectionType = SelectionType.ENEMY
        ),
        attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS,
            sortType = SortType.DESCENDING
        ),
        priorityType = PriorityType.LOW
    )

    val actionHeal = Action(
        ability = heal,
        actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = heal.magicCost
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.SELF
                ),
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN_EQUAL,
                                    value = 25
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.ALLY
                )
            )
        ),
        actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ),
            selectionType = SelectionType.ALLY
        ),
        attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS,
            sortType = SortType.ASCENDING
        ),
        priorityType = PriorityType.entries.random()
    )

    val actionHerb = Action(
        ability = herb,
        actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.LESS_THAN,
                                    value = heal.magicCost
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.TURNS_SLEEP,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.HERB_COUNT,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.SELF
                ),
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN_EQUAL,
                                    value = 25
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.ALLY
                )
            )
        ),
        actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ),
            selectionType = SelectionType.ALLY
        ),
        attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS,
            sortType = SortType.ASCENDING
        ),
        priorityType = PriorityType.entries.random()
    )

    val actionHealMore = Action(
        ability = healMore,
        actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = healMore.magicCost
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.SELF
                ),
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN_EQUAL,
                                    value = 25
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.ALLY
                )
            )
        ),
        actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ),
            selectionType = SelectionType.ALLY
        ),
        attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS,
            sortType = SortType.ASCENDING
        ),
        priorityType = PriorityType.entries.random()
    )

    val actionHurt = Action(
        ability = hurt,
        actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 2
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.SELF
                ),
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.ENEMY
                )
            )
        ),
        actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ),
            selectionType = SelectionType.ENEMY
        ),
        attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS,
            sortType = SortType.DESCENDING
        ),
        priorityType = PriorityType.entries.random()
    )

    val actionRun = Action(
        ability = run,
        actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN_EQUAL,
                                    value = 5
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.TURNS_SLEEP,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.SELF
                ),
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.ENEMY
                )
            )
        ),
        actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ),
            selectionType = SelectionType.ENEMY
        ),
        attributeSort = AttributeSort(
            attributeName = AttributeName.AGILITY,
            sortType = SortType.ASCENDING
        ),
        priorityType = PriorityType.entries.random()
    )

    val actionSleep = Action(
        ability = sleep,
        actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = sleep.magicCost
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.SELF
                ),
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.TURNS_SLEEP,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.ENEMY
                )
            )
        ),
        actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ),
            selectionType = SelectionType.ENEMY
        ),
        attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS,
            sortType = SortType.DESCENDING
        ),
        priorityType = PriorityType.entries.random()
    )

    val actionStopSpell = Action(
        ability = stopSpell,
        actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = stopSpell.magicCost
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.SELF
                ),
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                ),
                                AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ),
                    selectionType = SelectionType.ENEMY
                )
            )
        ),
        actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ),
            selectionType = SelectionType.ENEMY
        ),
        attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS,
            sortType = SortType.DESCENDING
        ),
        priorityType = PriorityType.entries.random()
    )

    val actors = mutableListOf<Actor>()

    for (i in (0..4)) {
        val actor = Actor()
        actor.actions = listOf(
            listOf(actionAttackEnemy, actionAttackHero).random(),
            listOf(actionHeal, actionHealMore).random(),
            actionHerb,
            actionHurt,
            actionRun,
            actionSleep,
            actionStopSpell
        )
        actor.allegiance = i % 2
        actor.agility = (0..10).random()
        actor.evasionRequirementMaximum = 32
        actor.healRangeMaximum = (10..100).random()
        actor.healScale = 7
        actor.healShift = 3
        actor.herbRangeMaximum = (10..50).random()
        actor.herbScale = 3
        actor.herbShift = 7
        actor.hitPointsMaximum = (10..100).random()
        actor.hitPoints = actor.hitPointsMaximum
        actor.hurtRangeMaximum = (0..255).random()
        actor.hurtRequirementMaximum = (0..16).random()
        actor.hurtResistanceMaximum = (0..16).random()
        actor.hurtScale = (3..10).random()
        actor.hurtShift = (0..10).random()
        actor.items[ItemName.HERB] = (3..10).random()
        actor.magicPointsMaximum = (6..12).random()
        actor.magicPoints = actor.magicPointsMaximum
        actor.runRangeMaximum = (0..16).random()
        actor.runShift = 2
        actor.sleepRequirementMaximum = (0..16).random()
        actor.sleepResistanceMaximum = (0..16).random()
        actor.sleepResolutionMaximum = 3
        actor.strength = (1..10).random()
        actor.turnsSleepMaximum = (3..6).random()
        actor.turnsSleepMinimum = (0..<actor.turnsSleepMaximum).random()
        actors.add(actor)
    }

    val battleSystem = BattleSystem()
    battleSystem.actors.addAll(actors)
    while (battleSystem.hasNext()) {
        battleSystem.run()
    }
}