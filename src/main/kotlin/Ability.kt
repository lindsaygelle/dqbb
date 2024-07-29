package dqbb

// import org.apache.logging.log4j.LogManager
// import org.apache.logging.log4j.Logger


abstract class Ability(
    protected val condition: ConditionType,
) {

    protected abstract fun apply(actor: Actor, otherActor: Actor): Boolean

    protected abstract fun check(actor: Actor, otherActor: Actor): Boolean

    private fun getActor(otherActors: Set<Actor>): Actor? {
        logger.debug(
            "$this: " +
                    "condition=$condition " +
                    "otherActors.size=${otherActors.size}"
        )
        return when (condition) {
            ConditionType.HIT_POINTS -> otherActors.minByOrNull { otherActor ->
                otherActor.hitPoints
            }

            ConditionType.MAGIC_POINTS -> otherActors.minByOrNull {
                otherActor -> otherActor.magicPoints
            }
            else -> otherActors.random()
        }
    }

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
