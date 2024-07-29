package dqbb

abstract class Consume(
    condition: ConditionType,
    private val item: ItemType
) : Ability(
    condition = condition
) {

    override fun apply(actor: Actor, otherActor: Actor): Boolean {
        val checkResistanceValue = checkResistance(actor, otherActor)
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "checkResistance=$checkResistanceValue " +
                    "otherActor.id=$otherActor"
        )
        if (checkResistanceValue) {
            return false
        }
        val applyEffectValue = applyEffect(actor, otherActor)
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "applyEffect=$applyEffectValue " +
                    "otherActor.id=$otherActor"
        )
        actor.items[item]?.minus(1)
        return applyEffectValue
    }

    protected abstract fun applyEffect(actor: Actor, otherActor: Actor): Boolean

    override fun check(actor: Actor, otherActor: Actor): Boolean {
        val itemCount = actor.items.getOrDefault(this.item, 0)
        println(//logger.debug
            "$this: " +
                    "actor.items.$item=$itemCount " +
                    "actor.id=$actor " +
                    "item=$item " +
                    "otherActor.id=$otherActor"
        )
        return itemCount > 0
    }

    protected abstract fun checkResistance(actor: Actor, otherActor: Actor): Boolean
}

