package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class Decision(
    val ability: Ability,
    val preCondition: State,
    override val priorityType: PriorityType,
    val targetSelection: State,
    variability: Int? = null,
) : Identifier, Prioritized {

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private val variability: Int = maxOf(0, maxOf(variability ?: 100))

    fun isValid(actor: Actor, otherActors: Collection<Actor>): Boolean {
        logger.debug(
            "$this: " +
                    "ability.id=${this.ability.id} " +
                    "ability.name=${this.ability.name} " +
                    "actor.id=${actor.id} " +
                    "priorityType=${this.priorityType} " +
                    "variability=${this.variability}"
        )
        val preConditionCheck = this.preCondition.check(actor, otherActors)
        logger.debug(
            "$this: " +
                    "ability.id=${this.ability.id} " +
                    "ability.name=${this.ability.name} " +
                    "actor.id=${actor.id} " +
                    "preCondition.actors.size=${preCondition.actors.size} " +
                    "preCondition.id=${preCondition.id} " +
                    "preCondition.check=$preConditionCheck"
        )
        if (!preConditionCheck) {
            return false
        }
        val targetSelectionCheck = this.targetSelection.check(actor, otherActors)
        logger.debug(
            "$this: " +
                    "ability.id=${this.ability.id} " +
                    "ability.name=${this.ability.name} " +
                    "actor.id=${actor.id} " +
                    "targetSelection.actors.size=${targetSelection.actors.size} " +
                    "targetSelection.id=${targetSelection.id} " +
                    "targetSelection.check=$targetSelectionCheck"
        )
        return targetSelectionCheck
    }
}
