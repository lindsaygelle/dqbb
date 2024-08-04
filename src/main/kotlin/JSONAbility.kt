package dqbb

import kotlinx.serialization.Serializable

@Serializable
data class JSONAbility(
    val actionType: ActionType,
    val conditionType: ConditionType,
    val orderType: OrderType,
) {
    fun build(): Ability {
        return when (this.actionType) {
            ActionType.ATTACK -> Attack(
                this.conditionType, this.orderType
            )

            ActionType.BREATHE_FIRE -> MagicBreatheFire(
                this.conditionType, this.orderType
            )

            ActionType.HEAL -> MagicHeal(
                this.conditionType, this.orderType
            )

            ActionType.HEAL_MORE -> MagicHealMore(
                this.conditionType, this.orderType
            )

            ActionType.HERB -> ConsumeHerb(
                this.conditionType, this.orderType
            )

            ActionType.HURT -> MagicHurt(
                this.conditionType, this.orderType
            )

            ActionType.HURT_MORE -> MagicHurtMore(
                this.conditionType, this.orderType
            )

            ActionType.MAGIC_POTION -> ConsumeMagicPotion(
                this.conditionType, this.orderType
            )

            ActionType.SLEEP -> MagicSleep(
                this.conditionType, this.orderType
            )

            ActionType.STOP_SPELL -> MagicStopSpell(
                this.conditionType, this.orderType
            )
        }
    }
}
