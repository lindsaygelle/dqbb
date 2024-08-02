package dqbb

abstract class Consume(
    conditionType: ConditionType,
    orderType: OrderType? = null,
) : Ability(
    conditionType = conditionType,
    orderType = orderType,
) {

    protected abstract val itemType: ItemType

    override fun apply(actor: Actor, otherActor: Actor): Boolean {
        val checkResistanceValue = checkResistance(actor, otherActor)
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "checkResistance=$checkResistanceValue " +
                    "otherActor.id=${otherActor.id}"
        )
        if (checkResistanceValue) {
            return false
        }
        val applyEffectValue = applyEffect(actor, otherActor)
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "applyEffect=$applyEffectValue " +
                    "otherActor.id=${otherActor.id}"
        )
        actor.subtractItem(this.itemType)
        return applyEffectValue
    }

    protected abstract fun applyEffect(actor: Actor, otherActor: Actor): Boolean

    override fun check(actor: Actor, otherActor: Actor): Boolean {
        val itemCount = actor.getItem(this.itemType)
        logger.debug(
            "$this: " +
                    "actor.items.$itemType=$itemCount " +
                    "actor.id=${actor.id} " +
                    "itemType=$itemType " +
                    "otherActor.id=${otherActor.id}"
        )
        return itemCount > 0
    }

    protected abstract fun checkResistance(actor: Actor, otherActor: Actor): Boolean
}
