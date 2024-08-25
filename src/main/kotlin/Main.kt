package dqbb

fun main() {

    val attackHero = AttackHero<Actor, Actor>()
    val breatheFire = BreatheFire<Actor, Actor>(magicCost = 20)
    val heal = Heal<Actor, Actor>(magicCost = 2)
    val healMore = HealMore<Actor, Actor>(magicCost = 10)
    val herb = Herb<Actor, Actor>()
    val hurt = Hurt<Actor, Actor>(magicCost = 2)
    val hurtMore = HurtMore<Actor, Actor>(magicCost = 4)
    val magicPotion = MagicPotion<Actor, Actor>()
    val run = Run<Actor, Actor>()
    val sleep = Sleep<Actor, Actor>(magicCost = 2)
    val stopSpell = StopSpell<Actor, Actor>(magicCost = 2)

    val actors = mutableSetOf<Actor>()
    for (i in (0..2)) {
        val actor = Actor()
        actor.agility = (0..10).random()
        actor.allegiance = i % 2
        actor.breatheFireScale = (1..10).random()
        actor.breatheFireShift = (1..10).random()
        actor.evasionRequirementMaximum = 32
        actor.healMoreScale = (1..10).random()
        actor.healMoreShift = (1..10).random()
        actor.healRangeMaximum = (1..10).random()
        actor.healScale = (1..10).random()
        actor.healShift = (1..10).random()
        actor.herbScale = (1..10).random()
        actor.herbShift = (1..10).random()
        actor.hitPointsMaximum = (10..100).random()
        actor.hitPoints = actor.hitPointsMaximum
        actor.hurtMoreScale = (1..10).random()
        actor.hurtMoreShift = (1..10).random()
        actor.hurtRangeMaximum = 16
        actor.hurtRequirementMaximum = 16
        actor.hurtResistanceMaximum = 16
        actor.hurtScale = (1..10).random()
        actor.hurtShift = (1..10).random()
        actor.magicPointsMaximum = (0..100).random()
        actor.magicPoints = actor.magicPointsMaximum
        actor.runRangeMaximum = 63
        actor.sleepRequirementMaximum = 16
        actor.sleepResistanceMaximum = 16
        actor.sleepResolutionMaximum = 6
        actor.stopSpellRequirementMaximum = 16
        actor.stopSpellResistanceMaximum = 16
        actor.stopSpellResolutionMaximum = 6
        actor.strength = (10..20).random()
        actor.turnsSleepMaximum = (3..10).random()
        actor.turnsSleepMinimum = (1..<actor.turnsSleepMaximum).random()
        actor.turnsStopSpellMaximum = (3..10).random()
        actor.turnsStopSpellMinimum = (1..<actor.turnsStopSpellMaximum).random()
        
        actor.actions =  listOf(
            Action(
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
                                            value = heal.magicCost
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGH
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
                    sortType = SortType.ASCENDING,
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
                                            priorityType = PriorityType.HIGH,
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
            Action(
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
                                            value = hurt.magicCost
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGH
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
                    sortType = SortType.DESCENDING,
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
                                            priorityType = PriorityType.HIGH,
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
            Action(
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
                                            value = sleep.magicCost
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGH
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
                    sortType = SortType.DESCENDING,
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
            Action(
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
                                            value = stopSpell.magicCost
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGH
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
                ),
                actorPriority = ActorPriority(
                    attributeName = AttributeName.AGILITY,
                    sortType = SortType.DESCENDING,
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
            Action(
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
                                    priorityType = PriorityType.HIGH
                                )
                            ),
                            selectionType = SelectionType.SELF,
                        )
                    )
                ),
                actorPriority = ActorPriority(
                    attributeName = AttributeName.AGILITY,
                    sortType = SortType.DESCENDING,
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
            Action(
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
                                    priorityType = PriorityType.HIGH
                                )
                            ),
                            selectionType = SelectionType.ENEMY,
                        )
                    )
                ),
                actorPriority = ActorPriority(
                    attributeName = AttributeName.HIT_POINTS,
                    sortType = SortType.DESCENDING,
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
                ability = attackHero,
                priorityType = PriorityType.entries.random()
            ),
            Action(
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
                                            priorityType = PriorityType.HIGH,
                                            value = 1
                                        )
                                    ),
                                    matchType = MatchType.ALL,
                                    priorityType = PriorityType.HIGH
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
                    sortType = SortType.ASCENDING,
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
                                            priorityType = PriorityType.HIGH,
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


        actors.add(actor)
    }

    val battleSystem = BattleSystem()
    battleSystem.attributeName = AttributeName.entries.random()
    battleSystem.actors.addAll(actors)
    println(battleSystem.actors.size)
    while (battleSystem.hasNext()) {
        battleSystem.run()
    }
}