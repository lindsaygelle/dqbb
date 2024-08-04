package dqbb

import kotlinx.serialization.json.Json
import java.io.File
import java.nio.charset.Charset

fun main() {

    /*
    val actor0 = Actor(
        agility = 0,
        allegiance = (0..2).random(),
        armor = ArmorErdrick,
        decisions = listOf(
            Decision(
                ability = MagicHeal(
                    conditionType = ConditionType.HIT_POINTS,
                    orderType = OrderType.MIN,
                ),
                priorityType = PriorityType.HIGHEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.HIT_POINTS,
                                    expressionType = ExpressionType.PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN,
                                    value = 10,
                                )
                            ),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ALLY,
                        ),
                        Qualify(
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.MAGIC_POINTS,
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
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.HIT_POINTS,
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
            Decision(
                ability = ConsumeMagicPotion(
                    conditionType = ConditionType.MAGIC_POINTS,
                    orderType = OrderType.MIN,
                ),
                priorityType = PriorityType.LOWEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.MAGIC_POINTS,
                                    expressionType = ExpressionType.EXACT,
                                    operatorType = OperatorType.LESS_THAN,
                                    value = 3,
                                )
                            ),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ALLY,
                        ),
                        Qualify(
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.MAGIC_POTIONS,
                                    expressionType = ExpressionType.EXACT,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 0
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
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.MAGIC_POINTS,
                                    expressionType = ExpressionType.PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN,
                                    value = 3,
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
        items = mutableMapOf(
            ItemType.HERB to 10,
            ItemType.MAGIC_POTION to 4,
        ),
        magicPointsMaximum = 10,
        turnsSleepMaximum = 3,
        turnsStopSpellMaximum = 3,
    )

    val actor1 = Actor(
        agility = 1,
        allegiance = (0..2).random(),
        decisions = listOf(
            Decision(
                ability = MagicHurt(
                    conditionType = ConditionType.HIT_POINTS,
                    orderType = OrderType.MAX,
                ),
                priorityType = PriorityType.HIGHEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.HIT_POINTS,
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
                            actorCheckers = listOf(),
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
        agility = 10,
        allegiance = (0..2).random(),
        armor = ArmorMagic,
        decisions = listOf(
            Decision(
                ability = Attack(
                    conditionType = ConditionType.HIT_POINTS,
                    orderType = OrderType.MIN,
                ),
                priorityType = PriorityType.LOWEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.HIT_POINTS,
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
                            actorCheckers = listOf(),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ENEMY,
                        )
                    )
                )
            ),
            Decision(
                ability = MagicSleep(
                    conditionType = ConditionType.HIT_POINTS,
                    orderType = OrderType.MAX,
                ),
                priorityType = PriorityType.HIGHEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.TURNS_SLEEP,
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
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.MAGIC_POINTS,
                                    expressionType = ExpressionType.EXACT,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 1,
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
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.TURNS_SLEEP,
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
            Decision(
                ability = MagicSleep(
                    conditionType = ConditionType.HIT_POINTS,
                    orderType = OrderType.MAX,
                ),
                priorityType = PriorityType.HIGHEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(
                        Qualify(
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.TURNS_SLEEP,
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
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.MAGIC_POINTS,
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
                            actorCheckers = listOf(
                                ActorChecker(
                                    conditionType = ConditionType.TURNS_SLEEP,
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

    val actor3 = Actor(
        allegiance = (0..2).random(),
        decisions = listOf(
            Decision(
                ability = Attack(
                    conditionType = ConditionType.HIT_POINTS,
                    orderType = OrderType.MAX,
                ),
                priorityType = PriorityType.HIGHEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(),
                ),
                targetSelection = State(
                    matchType = MatchType.ANY,
                    qualifiers = listOf(
                        Qualify(
                            actorCheckers = listOf(),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ENEMY,
                        )
                    )
                ),
            )
        )
    )

    val actor4 = Actor(
        allegiance = (0..2).random(),
        decisions = listOf(
            Decision(
                ability = ConsumeHerb(
                    conditionType = ConditionType.HIT_POINTS,
                    orderType = OrderType.MIN,
                ),
                priorityType = PriorityType.HIGHEST,
                preCondition = State(
                    matchType = MatchType.ALL,
                    qualifiers = listOf(),
                ),
                targetSelection = State(
                    matchType = MatchType.ANY,
                    qualifiers = listOf(
                        Qualify(
                            actorCheckers = listOf(),
                            matchType = MatchType.ANY,
                            targetType = TargetType.ALLY,
                        )
                    )
                ),
            )
        ),
        items = mutableMapOf(
            ItemType.HERB to (0..20).random(),
        )
    )

    var battleSystem = BattleSystem(
        actors = mutableSetOf(
            actor0, actor1, actor2, actor3, actor4
        )
    )

    while (battleSystem.isActive) {
        battleSystem.run()
    }

    println("Battle log:")
    battleSystem.trail.forEach {
        println(it.message)
    }
    println("Battle finished in ${battleSystem.turns} turns")

    */
    val actorsFolder = File(ClassLoader.getSystemResource("actors").file)

    if (!actorsFolder.exists() || !actorsFolder.isDirectory) {
        throw IllegalArgumentException("Folder not found: actors")
    }

    val actorFiles = actorsFolder.listFiles { _, name -> name.endsWith(".json") }
        ?: throw IllegalArgumentException("No JSON files found in the actors folder")

    actorFiles.forEach { file ->
        val jsonContent = file.readText(Charset.defaultCharset())

        val decoded = Json.decodeFromString<JSONActor>(jsonContent)

        decoded.build()
    }
}
