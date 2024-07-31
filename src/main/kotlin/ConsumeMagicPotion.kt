package dqbb

class ConsumeMagicPotion(
    conditionType: ConditionType,
) : Consume(
    conditionType = conditionType,
    item = ItemType.MAGIC_POTION
) {
    val magicPoints: Int = 2

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        /* Other Actor */
        val magicPoints = otherActor.magicPoints
        logger.debug(
            "$this: " +
                    "actor.items.${item}=${actor.items.getOrDefault(item, 0)} " +
                    "actor.id=$actor " +
                    "magicPoints=${this.magicPoints} " +
                    "otherActor.magicPoints=$magicPoints " +
                    "otherActor.id=$otherActor"
        )
        otherActor.magicPoints += this.magicPoints
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "otherActor.id=$otherActor " +
                    "otherActor.magicPoints=${otherActor.magicPoints}"
        )
        return true
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        return !otherActor.isAlive
    }
}