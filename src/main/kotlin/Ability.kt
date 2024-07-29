package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


abstract class Ability(
    protected val condition: ConditionType,
) {

    protected abstract fun apply(actor: Actor, otherActor: Actor): Boolean

    protected abstract fun check(actor: Actor, otherActor: Actor): Boolean

    protected abstract fun getActor(otherActors: Set<Actor>): Actor

    fun use(actor: Actor, otherActors: Set<Actor>): Boolean {
        val actorStatusSleep = actor.statusSleep
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "$actor.statusSleep=$actorStatusSleep"
        )
        if (actorStatusSleep) {
            return false
        }
        val otherActor = getActor(otherActors)
        logger.debug(
            "$this: " +
                    "otherActor.id=$otherActor"
        )
        val checkValue = check(actor, otherActor)
        logger.debug(
            "$this: " +
                    "check=$checkValue"
        )
        if (!checkValue) {
            return false
        }
        val applyValue = apply(actor, otherActor)
        logger.debug(
            "$this: " +
                    "apply=$applyValue"
        )
        return applyValue
    }
}
