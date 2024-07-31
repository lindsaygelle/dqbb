package dqbb

import com.sun.org.apache.xpath.internal.operations.Or
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


abstract class Ability(
    protected val conditionType: ConditionType,
    private val orderType: OrderType?,
) {

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    protected abstract fun apply(actor: Actor, otherActor: Actor): Boolean

    protected abstract fun check(actor: Actor, otherActor: Actor): Boolean

    private fun getActor(otherActors: Set<Actor>): Actor? {
        logger.debug(
            "$this: " +
                    "conditionType=$conditionType " +
                    "orderType=$orderType " +
                    "otherActors.size=${otherActors.size}"
        )
        val orderedActors = orderActors(otherActors)
        logger.debug(
            "$this: " +
                    "orderedActors.size=${orderedActors.size}"
        )
        val otherActor = orderedActors.firstOrNull()
        return otherActor
    }

    private fun orderActors(otherActors: Set<Actor>): Collection<Actor> {
        return when (orderType) {
            OrderType.MAX -> otherActors.sortedByDescending { actor ->
                actor.getConditionType(conditionType)
            }

            OrderType.MIN -> otherActors.sortedBy { actor ->
                actor.getConditionType(conditionType)
            }

            else -> otherActors
        }
    }

    fun use(actor: Actor, otherActors: Set<Actor>): Boolean {
        val actorStatusSleep = actor.statusSleep
        logger.debug(
            "$this: " +
                    "actor.id=$actor " +
                    "actor.statusSleep=$actorStatusSleep"
        )
        if (actorStatusSleep) {
            return false
        }
        val otherActor = getActor(otherActors)
        val isNotNull = otherActor != null
        logger.debug(
            "$this: " +
                    "otherActor.isNotNull=$isNotNull"
        )
        if (!isNotNull) {
            return false
        }
        logger.debug(
            "$this: " +
                    "otherActor.id=$otherActor"
        )
        val checkValue = check(actor, otherActor!!)
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
