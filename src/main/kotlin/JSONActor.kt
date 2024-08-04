package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class JSONActor(
    val agility: Int? = null,
    var allegiance: Int = 0,
    val armor: JSONArmor? = null,
    val damageResistance: Int? = null,
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
                        actorCheckers = listOf(
                            JSONActorChecker(
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
                        actorCheckers = listOf(),
                        matchType = MatchType.ANY,
                        priorityType = PriorityType.HIGHEST,
                        targetType = TargetType.ENEMY,
                    )
                )
            )
        )
    ),
    val experiencePoints: Int? = null,
    val goldPoints: Int? = null,
    val hitPoints: Int? = null,
    val hitPointsMaximum: Int? = null,
    val id: String? = null,
    val items: MutableMap<ItemType, Int> = mutableMapOf(),
    val magicPoints: Int? = null,
    val magicPointsMaximum: Int? = null,
    val name: String? = null,
    val shield: JSONShield? = null,
    val statusResistance: Int? = null,
    val strength: Int? = null,
    val weapon: JSONWeapon? = null,
) {
    fun build(): Actor {
        return Actor(
            agility = this.agility,
            allegiance = this.allegiance,
            armor = this.armor?.build(),
            damageResistance = this.damageResistance,
            decisions = this.decisions.map { it.build() },
            items = this.items,
            hitPoints = this.hitPoints,
            hitPointsMaximum = this.hitPointsMaximum,
            magicPoints = this.magicPoints,
            magicPointsMaximum = this.magicPointsMaximum,
            name = this.name,
            shield = this.shield?.build(),
            statusResistance = this.statusResistance,
            strength = this.strength,
            weapon = this.weapon?.build()
        )
    }
}
