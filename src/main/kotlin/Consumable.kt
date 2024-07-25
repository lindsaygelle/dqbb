package dqbb

abstract class Consumable(
    private val item: Item
) : Ability() {
    protected abstract fun applyEffect(actor: Actor, otherActor: Actor): Boolean
    override fun check(actor: Actor, otherActor: Actor): Boolean {
        val actorItem = actor.items[item]
        logger.debug("item=$item actor.items[$item]=$actorItem")
        return (actorItem != null) && (actorItem > 0)
    }

    override fun apply(actor: Actor, otherActor: Actor): Boolean {
        actor.items[item]?.minus(1)
        return applyEffect(actor, otherActor)
    }
}

class ConsumableHerb() : Consumable(
    item = Item.HERB,
) {
    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val hitPointsPrevious = otherActor.hitPoints
        val herbValue = actor.herbValue
        val hitPoints = otherActor.hitPoints + herbValue
        logger.debug("herbValue=$herbValue hitPointsPrevious=$hitPointsPrevious hitPoints=$hitPoints")
        otherActor.hitPoints = hitPoints
        return otherActor.hitPoints > hitPointsPrevious
    }
}
