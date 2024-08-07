package dqbb

class ConsumeMagicPotion(
    conditionType: ConditionType,
    orderType: OrderType? = null,
) : Consume(
    conditionType = conditionType,
    orderType = orderType,
) {

    override val actionType: ActionType = ActionType.MAGIC_POTION

    override val itemType: ItemType = ItemType.MAGIC_POTION

    val magicPoints: Int = 2

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Other Actor */
        val magicPoints = otherActor.magicPoints
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "magicPoints=${this.magicPoints} " +
                    "otherActor.magicPoints=$magicPoints " +
                    "otherActor.id=${otherActor.id}"
        )
        otherActor.magicPoints += this.magicPoints
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "otherActor.id=${otherActor.id} " +
                    "otherActor.magicPoints=${otherActor.magicPoints}"
        )
        actor.trail.add(
            Trail(
                "${actor.arn} RESTORES ${otherActor.arn} for ${this.magicPoints} MAGIC POINTS"
            )
        )
        return true
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        return !otherActor.isAlive
    }
}
