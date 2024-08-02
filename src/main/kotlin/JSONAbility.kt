package dqbb

import kotlinx.serialization.*

@Serializable
data class JSONAbility(
    val actionType: ActionType,
    val conditionType: ConditionType,
    val orderType: OrderType?,
) {
    fun build(): Ability {
        return when (this.actionType) {
            ActionType.ATTACK -> Attack(conditionType, orderType)
            ActionType.HEAL -> MagicHeal(conditionType, orderType)
            ActionType.HEAL_MORE -> MagicHealMore(conditionType, orderType)
            ActionType.HERB -> ConsumeHerb(conditionType, orderType)
            ActionType.HURT -> MagicHurt(conditionType, orderType)
            ActionType.HURT_MORE -> MagicHurtMore(conditionType, orderType)
            ActionType.MAGIC_POTION -> ConsumeMagicPotion(conditionType, orderType)
            ActionType.SLEEP -> MagicSleep(conditionType, orderType)
            ActionType.STOP_SPELL -> MagicStopSpell(conditionType, orderType)
        }
    }
}
