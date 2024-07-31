package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class Decision(
    val ability: Ability,
    private val preCondition: State,
    override val priorityType: PriorityType,
    val targetSelection: State,
) : Prioritized {

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun isValid(actor: Actor, otherActors: List<Actor>): Boolean {
        logger.debug(
            "$this: " +
                    "ability.id=$ability " +
                    "actor.id=$actor " +
                    "priorityType=$priorityType"
        )
        val preConditionCheck = this.preCondition.check(actor, otherActors)
        logger.debug(
            "$this: " +
                    "ability.id=$ability " +
                    "actor.id=$actor " +
                    "preCondition.actors.size=${preCondition.actors.size} " +
                    "preCondition.check=$preConditionCheck"
        )
        if (!preConditionCheck) {
            return false
        }
        val targetSelectionCheck = this.targetSelection.check(actor, otherActors)
        logger.debug(
            "$this: " +
                    "ability.id=$ability " +
                    "actor.id=$actor " +
                    "targetSelection.actors.size=${targetSelection.actors.size} " +
                    "targetSelection.check=$targetSelectionCheck"
        )
        return targetSelectionCheck
    }
}
