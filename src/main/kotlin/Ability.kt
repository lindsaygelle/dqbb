package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


abstract class Ability(
    protected val conditionType: ConditionType,
    private val orderType: OrderType?,
) : Identifier {

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    abstract val name: String

    protected abstract fun apply(actor: Actor, otherActor: Actor): Boolean

    protected abstract fun check(actor: Actor, otherActor: Actor): Boolean

    private fun getActor(otherActors: Collection<Actor>): Actor? {
        logger.debug(
            "$this: " +
                    "conditionType=${this.conditionType} " +
                    "orderType=${this.orderType} " +
                    "otherActors.size=${otherActors.size}"
        )
        val sortedActors = sortActors(otherActors)
        logger.debug(
            "$this: " +
                    "sortedActors.size=${sortedActors.size}"
        )
        val otherActor = sortedActors.firstOrNull()
        logger.debug(
            "$this: " +
                    "otherActor=${otherActor?.id}"
        )
        return otherActor
    }

    private fun sortActors(otherActors: Collection<Actor>): Collection<Actor> {
        return when (this.orderType) {
            OrderType.MAX -> otherActors.sortedByDescending { actor ->
                actor.getConditionType(this.conditionType)
            }

            OrderType.MIN -> otherActors.sortedBy { actor ->
                actor.getConditionType(this.conditionType)
            }

            else -> otherActors
        }
    }

    fun use(actor: Actor, otherActors: Collection<Actor>): Boolean {
        actor.trail.add(
            Trail(
                "$actor TRIES ability ${this.name}"
            )
        )
        val actorStatusSleep = actor.statusSleep
        logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "actor.statusSleep=$actorStatusSleep"
        )
        if (actorStatusSleep) {
            actor.trail.add(
                Trail(
                    "$actor FAILED to use ${this.name} because they are ASLEEP"
                )
            )
            return false
        }
        val otherActor = getActor(otherActors)
        logger.debug(
            "$this: " +
                    "otherActor.id=${otherActor?.id}"
        )
        if (otherActor == null) {
            actor.trail.add(
                Trail(
                    "$actor FAILED to use ${this.name} because there was no suitable target"
                )
            )
            return false
        }
        actor.trail.add(
            Trail(
                "$actor CHOSE $otherActor as target for ability ${this.name}"
            )
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
        if (!otherActor.isAlive) {
            actor.trail.add(
                Trail(
                    "$actor DEFEATED $otherActor after ${actor.turnsAlive} turns!"
                )
            )
        }
        return applyValue
    }
}
