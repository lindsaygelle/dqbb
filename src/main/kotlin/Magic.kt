package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


abstract class Magic(
    condition: ConditionType,
    magicPoints: Int,
) : Ability(
    condition = condition,
) {
    private val magicPoints: Int = maxOf(0, magicPoints)

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
        return applyEffectValue
    }

    protected abstract fun applyEffect(actor: Actor, otherActor: Actor): Boolean

    override fun check(actor: Actor, otherActor: Actor): Boolean {
        val statusStopSpell = actor.statusStopSpell
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "actor.statusStopSpell=$statusStopSpell"
        )
        if (statusStopSpell) {
            return false
        }
        val magicPointsValue = actor.magicPoints - magicPoints
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "magicPointsValue=$magicPointsValue"
        )
        if (magicPointsValue < 0) {
            return false
        }
        actor.magicPoints = magicPointsValue
        return actor.magicPoints > 0
    }

    protected abstract fun checkResistance(actor: Actor, otherActor: Actor): Boolean
}
