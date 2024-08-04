package dqbb


abstract class Magic(
    conditionType: ConditionType,
    orderType: OrderType,
) : Ability(
    conditionType = conditionType,
    orderType = orderType,
) {

    protected abstract val magicPoints: Int

    override fun apply(actor: Actor, otherActor: Actor): Boolean {
        val checkResistanceValue = checkResistance(actor, otherActor)
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "checkResistance=$checkResistanceValue " +
                    "otherActor.id=${otherActor.id}"
        )
        if (checkResistanceValue) {
            actor.trail.add(
                Trail(
                    "$actor's SPELL ${this.name} was RESISTED!"
                )
            )
            return false
        }
        val applyEffectValue = applyEffect(actor, otherActor)
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "applyEffect=$applyEffectValue " +
                    "otherActor.id=${otherActor.id}"
        )
        return applyEffectValue
    }

    protected abstract fun applyEffect(actor: Actor, otherActor: Actor): Boolean

    override fun check(actor: Actor, otherActor: Actor): Boolean {
        val statusStopSpell = actor.statusStopSpell
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "actor.statusStopSpell=$statusStopSpell"
        )
        if (statusStopSpell) {
            actor.trail.add(
                Trail(
                    "$actor's MAGIC is blocked!"
                )
            )
            return false
        }
        val magicPointsValue = actor.magicPoints - magicPoints
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "actor.magicPoints=${actor.magicPoints} " +
                    "magicPoints=${this.magicPoints} " +
                    "magicPointsValue=$magicPointsValue"
        )
        if (magicPointsValue < 0) {
            actor.trail.add(
                Trail(
                    "$actor has INSUFFICIENT magic points!"
                )
            )
            return false
        }
        actor.magicPoints = magicPointsValue
        return true
    }

    protected abstract fun checkResistance(actor: Actor, otherActor: Actor): Boolean
}
