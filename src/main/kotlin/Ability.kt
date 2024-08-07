package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


abstract class Ability(
    protected val conditionType: ConditionType,
    private val orderType: OrderType?,
) : Identifier {

    abstract val actionType: ActionType

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    val name: String
        get() = this.actionType.toString()

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
                "${actor.arn} TRIES ${this.name}"
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
                    "${actor.arn} is ASLEEP"
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
                    "${actor.arn} cannot find a TARGET"
                )
            )
            return false
        }
        actor.trail.add(
            Trail(
                "${actor.arn} CHOSE ${otherActor.arn}"
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
                    "${actor.arn} DEFEATED ${otherActor.arn}"
                )
            )
        }
        return applyValue
    }
}
