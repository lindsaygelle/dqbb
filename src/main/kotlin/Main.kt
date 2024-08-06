package dqbb

fun main() {

    val healMagicCost = 2
    val hurtMagicCost = 0
    val sleepMagicCost = 2
    val stopSpellMagicCost = 2
    //val enemyAttack = EnemyAttack<Actor, Actor>()
    val heal = Heal<Actor, Actor>(healMagicCost)

    val herb = Herb<Actor, Actor>()
    val hurt = Hurt<Actor, Actor>(hurtMagicCost)
    val run = Run<Actor, Actor>()
    val sleep = Sleep<Actor, Actor>(sleepMagicCost)
    val stopSpell = StopSpell<Actor, Actor>(stopSpellMagicCost)

    val actors = mutableListOf<Actor>()

    for (i in (0..10)) {
        val actor = Actor()
        actor.actorActions = listOf(
            ActorAction(
                actorCondition = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                            operatorType = OperatorType.GREATER_THAN,
                                            priorityType = PriorityType.EQUAL,
                                            value = 25
                                        ),
                                        ActorCheck(
                                            attributeName = AttributeName.MAGIC_POINTS,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = healMagicCost
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGHEST
                                )
                            ),
                            selectionType = SelectionType.SELF,
                        ),
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                            operatorType = OperatorType.LESS_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 25
                                        )
                                    ),
                                    matchType = MatchType.ANY,
                                    priorityType = PriorityType.EQUAL
                                )
                            ),
                            selectionType = SelectionType.ALLY
                        )
                    )
                ),
                actorPriority = ActorPriority(
                    attributeName = AttributeName.HIT_POINTS,
                    sortedBy = SortType.ASCENDING,
                ),
                actorSelection = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.HIGHEST,
                                            value = 25
                                        )
                                    ),
                                    matchType = MatchType.ANY,
                                    priorityType = PriorityType.EQUAL,
                                ),
                            ),
                            selectionType = SelectionType.ALLY,
                        )
                    )
                ),
                ability = heal,
                priorityType = PriorityType.entries.random()
            ),
            ActorAction(
                actorCondition = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.MAGIC_POINTS,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = hurtMagicCost
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGHEST
                                )
                            ),
                            selectionType = SelectionType.SELF,
                        ),
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 25
                                        )
                                    ),
                                    matchType = MatchType.ANY,
                                    priorityType = PriorityType.EQUAL
                                )
                            ),
                            selectionType = SelectionType.ALLY
                        ),
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 1
                                        )
                                    ),
                                    matchType = MatchType.ANY,
                                    priorityType = PriorityType.EQUAL
                                )
                            ),
                            selectionType = SelectionType.ENEMY
                        )
                    )
                ),
                actorPriority = ActorPriority(
                    attributeName = AttributeName.HIT_POINTS,
                    sortedBy = SortType.DESCENDING,
                ),
                actorSelection = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.HIGHEST,
                                            value = 1
                                        )
                                    ),
                                    matchType = MatchType.ANY,
                                    priorityType = PriorityType.EQUAL,
                                ),
                            ),
                            selectionType = SelectionType.ENEMY,
                        )
                    )
                ),
                ability = hurt,
                priorityType = PriorityType.entries.random()
            ),
            ActorAction(
                actorCondition = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.MAGIC_POINTS,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = sleepMagicCost
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGHEST
                                )
                            ),
                            selectionType = SelectionType.SELF,
                        ),
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 25
                                        )
                                    ),
                                    matchType = MatchType.ANY,
                                    priorityType = PriorityType.EQUAL
                                )
                            ),
                            selectionType = SelectionType.ALLY
                        ),
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.TURNS_SLEEP,
                                            operatorType = OperatorType.EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 0
                                        )
                                    ),
                                    matchType = MatchType.ANY,
                                    priorityType = PriorityType.EQUAL
                                )
                            ),
                            selectionType = SelectionType.ENEMY
                        )
                    )
                ),
                actorPriority = ActorPriority(
                    attributeName = AttributeName.AGILITY,
                    sortedBy = SortType.DESCENDING,
                ),
                actorSelection = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 1
                                        ),
                                        ActorCheck(
                                            attributeName = AttributeName.TURNS_SLEEP,
                                            operatorType = OperatorType.EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 0
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.EQUAL,
                                ),
                            ),
                            selectionType = SelectionType.ENEMY,
                        )
                    )
                ),
                ability = sleep,
                priorityType = PriorityType.entries.random()
            ),
            ActorAction(
                actorCondition = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.MAGIC_POINTS,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = stopSpellMagicCost
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGHEST
                                )
                            ),
                            selectionType = SelectionType.SELF,
                        ), ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 25
                                        )
                                    ),
                                    matchType = MatchType.ANY,
                                    priorityType = PriorityType.EQUAL
                                )
                            ),
                            selectionType = SelectionType.ALLY
                        ), ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.TURNS_STOP_SPELL,
                                            operatorType = OperatorType.EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 0
                                        )
                                    ),
                                    matchType = MatchType.ANY,
                                    priorityType = PriorityType.EQUAL
                                )
                            ),
                            selectionType = SelectionType.ENEMY
                        )
                    )
                ), actorPriority = ActorPriority(
                    attributeName = AttributeName.AGILITY,
                    sortedBy = SortType.DESCENDING,
                ),
                actorSelection = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 1
                                        ),
                                        ActorCheck(
                                            attributeName = AttributeName.TURNS_STOP_SPELL,
                                            operatorType = OperatorType.EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 0
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.EQUAL,
                                ),
                            ),
                            selectionType = SelectionType.ENEMY,
                        )
                    )
                ),
                ability = stopSpell,
                priorityType = PriorityType.entries.random()
            ),
            ActorAction(
                actorCondition = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                            operatorType = OperatorType.LESS_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 15
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGHEST
                                )
                            ),
                            selectionType = SelectionType.SELF,
                        )
                    )
                ),
                actorPriority = ActorPriority(
                    attributeName = AttributeName.AGILITY,
                    sortedBy = SortType.DESCENDING,
                ),
                actorSelection = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 1
                                        ),
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.EQUAL,
                                ),
                            ),
                            selectionType = SelectionType.ENEMY,
                        )
                    )
                ),
                ability = run,
                priorityType = PriorityType.entries.random()
            ),
            /*
            ActorAction(
                actorCondition = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 1
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGHEST
                                )
                            ),
                            selectionType = SelectionType.ENEMY,
                        )
                    )
                ),
                actorPriority = ActorPriority(
                    attributeName = AttributeName.HIT_POINTS,
                    sortedBy = SortType.DESCENDING,
                ),
                actorSelection = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 1
                                        ),
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.EQUAL,
                                ),
                            ),
                            selectionType = SelectionType.ENEMY,
                        )
                    )
                ),
                invokable = enemyAttack,
                priorityType = PriorityType.LOWEST
            ),

             */
            ActorAction(
                actorCondition = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                            operatorType = OperatorType.GREATER_THAN,
                                            priorityType = PriorityType.EQUAL,
                                            value = 25
                                        ),
                                        ActorCheck(
                                            attributeName = AttributeName.MAGIC_POINTS,
                                            operatorType = OperatorType.LESS_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 1
                                        ),
                                        ActorCheck(
                                            attributeName = AttributeName.HERB_COUNT,
                                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                                            priorityType = PriorityType.HIGHEST,
                                            value = 1
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGHEST
                                )
                            ),
                            selectionType = SelectionType.SELF,
                        ),
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                            operatorType = OperatorType.LESS_THAN_EQUAL,
                                            priorityType = PriorityType.EQUAL,
                                            value = 25
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.EQUAL
                                )
                            ),
                            selectionType = SelectionType.ALLY
                        )
                    )
                ),
                actorPriority = ActorPriority(
                    attributeName = AttributeName.HIT_POINTS,
                    sortedBy = SortType.ASCENDING,
                ),
                actorSelection = ActorAggregate(
                    actorFilters = listOf(
                        ActorFilter(
                            actorCriteria = listOf(
                                ActorCriterion(
                                    actorChecks = listOf(
                                        ActorCheck(
                                            attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                            operatorType = OperatorType.LESS_THAN_EQUAL,
                                            priorityType = PriorityType.HIGHEST,
                                            value = 25
                                        )
                                    ),
                                    matchType = MatchType.ANY,
                                    priorityType = PriorityType.EQUAL,
                                ),
                            ),
                            selectionType = SelectionType.ALLY,
                        )
                    )
                ),
                ability = herb,
                priorityType = PriorityType.entries.random()
            )
        )
        actor.allegiance = i % 2
        actor.agility = (0..10).random()
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
    val battleSystem = BattleSystem(actors)
    while (battleSystem.hasNext()) {
        battleSystem.run()
    }

    actors.forEach {
        println(
            "actor.uuid=${it.uuid} " +
                    "actor.allegiance=${it.allegiance} " +
                    "actor.hitPoints=${it.hitPoints} " +
                    "actor.hitPointsMaximum=${it.hitPointsMaximum}" +
                    "actor.index=${it.index} " +
                    "actor.items=${it.items} " +
                    "actor.magicPoints=${it.magicPoints} " +
                    "actor.magicPointsMaximum=${it.magicPointsMaximum} " +
                    "actor.turnsBattle=${it.turnsBattle} " +
                    "battleSystem.turnsBattle=${battleSystem.turnsBattle}"
        )
    }


    /*
    val actor = Actor()
    actor.hitPointsMaximum = 10
    actor.hitPoints = actor.hitPointsMaximum
    actor.hurtRequirementMaximum = 16
    actor.hurtShift = 3
    actor.hurtScale = 7
    actor.magicPointsMaximum = 10
    actor.magicPoints = actor.magicPointsMaximum
    actor.sleepRequirementMaximum = 16
    actor.stopSpellRequirementMaximum = 16
    actor.stopSpellRequirementMinimum = 16
    actor.turnsSleepMaximum = 3
    actor.turnsStopSpellMaximum = 3

    Hurt<Actor, Actor>(magicCost = 0).use(actor, actor)
    actor.hitPoints = actor.hitPointsMaximum - 5
    Heal<Actor, Actor>(magicCost = 0).use(actor, actor)
    StopSpell<Actor, Actor>(0).use(actor, actor)

    actor.hitPoints = actor.hitPointsMaximum - 6
    actor.items[ItemName.HERB] = 1
    Herb<Actor, Actor>().use(actor, actor)
    Run<Actor, Actor>().use(actor, actor)
    Sleep<Actor, Actor>(0).use(actor, actor)

     */
}