package dqbb

class ConsumeHerb(
    conditionType: ConditionType,
    orderType: OrderType? = null,
) : Consume(
    conditionType = conditionType,
    orderType = orderType,
) {

    override val itemType: ItemType = ItemType.HERB

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Actor */
        val healRangeMaximum = actor.healRangeMaximum
        val healRangeMinimum = actor.healRangeMinimum
        val herbRangeRandom = (healRangeMinimum..healRangeMinimum).random()
        val herbScale = actor.herbScale
        val herbShift = actor.herbShift
        val herbValue = (herbRangeRandom and herbShift) + herbScale
        /* Other Actor */
        val hitPoints = otherActor.hitPoints
        logger.debug(
            "$this: " +
                    "actor.healRangeMaximum=$healRangeMaximum " +
                    "actor.healRangeMinimum=$healRangeMinimum " +
                    "actor.herbScale=$herbScale " +
                    "actor.herbShift=$herbShift " +
                    "actor.herbValue=$herbValue " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=$hitPoints " +
                    "otherActor.id=$otherActor"
        )
        otherActor.hitPoints += herbValue
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.id=$otherActor"
        )
        return true
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        return !otherActor.isAlive
    }
}
