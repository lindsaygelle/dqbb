package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class JSONActor(
    val allegiance: Int,
    val armor: JSONArmor? = null,
    val decisions: List<JSONDecision> = listOf(
        JSONDecision(
            ability = JSONAbility(
                actionType = ActionType.ATTACK,
                conditionType = ConditionType.HIT_POINTS,
                orderType = OrderType.MAX,
            ),
            preCondition = JSONState(
                matchType = MatchType.ANY,
                qualifiers = listOf(
                    JSONQualify(
                        checkers = listOf(
                            JSONCheckActor(
                                conditionType = ConditionType.HIT_POINTS,
                                expressionType = ExpressionType.EXACT,
                                priorityType = PriorityType.HIGHEST,
                                operatorType = OperatorType.GREATER_THAN,
                                value = 0
                            )
                        ),
                        matchType = MatchType.ANY,
                        priorityType = PriorityType.HIGHEST,
                        targetType = TargetType.ENEMY,
                    )
                )
            ),
            priorityType = PriorityType.HIGHEST,
            targetSelection = JSONState(
                matchType = MatchType.ANY,
                qualifiers = listOf(
                    JSONQualify(
                        checkers = listOf(),
                        matchType = MatchType.ANY,
                        priorityType = PriorityType.HIGHEST,
                        targetType = TargetType.ENEMY,
                    )
                )
            )
        )
    ),
    val items: MutableMap<ItemType, Int> = mutableMapOf(),
    val shield: JSONShield? = null,
    val weapon: JSONWeapon? = null,
) {
    fun build(): Actor {
        return Actor(
            allegiance = this.allegiance,
            armor = this.armor?.build(),
            decisions = this.decisions.map { it.build() },
            items = this.items,
            shield = this.shield?.build(),
            weapon = this.weapon?.build()
        )
    }
}
