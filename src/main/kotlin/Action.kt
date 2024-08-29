package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Action() : Identifier,
    Prioritized {
    var ability: Ability<Actor, Actor>? = null
        set(value) {
            field = value
            logger.debug(
                "ability.id={} ability.simpleName={} id={} simpleName={}", field?.id, field?.simpleName, id, simpleName
            )
        }

    var actionCondition: ActionCondition<Actor, Actor>? = null
        set(value) {
            field = value
            logger.debug(
                "actionCondition.id={} id={} simpleName={}", field?.id, id, simpleName
            )
        }

    var actionTarget: ActionTarget<Actor, Actor>? = null
        set(value) {
            field = value
            logger.debug(
                "actionTarget.id={} id={} simpleName={}", field?.id, id, simpleName
            )
        }

    var attributeSort: AttributeSort<Actor>? = null
        set(value) {
            field = value
            logger.debug(
                "attributeSort.id={} id={} simpleName={}", field?.id, id, simpleName
            )
        }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override var priorityType: PriorityType = PriorityType.EQUAL
        set(value) {
            field = value
            logger.debug(
                "id={} priorityType={} simpleName={}", id, field, simpleName
            )
        }

    constructor(
        ability: Ability<Actor, Actor>,
        actionCondition: ActionCondition<Actor, Actor>,
        actionTarget: ActionTarget<Actor, Actor>,
        attributeSort: AttributeSort<Actor>? = null,
        priorityType: PriorityType = PriorityType.EQUAL,
    ) : this() {
        this.ability = ability
        this.actionCondition = actionCondition
        this.actionTarget = actionTarget
        this.attributeSort = attributeSort
        this.priorityType = priorityType
    }

    private fun checkAbility(actor: Actor, actors: Collection<Actor>): Boolean {
        logger.info(
            "actor.id={} actors.size={} id={} attributeSort.id={} simpleName={}",
            actor.id,
            actors.size,
            id,
            attributeSort?.id,
            simpleName
        )
        val sortedActors = (attributeSort?.sort(actors) ?: actors)
        if (sortedActors.isEmpty()) {
            return false
        }
        return ability?.use(actor, sortedActors.first()) ?: false
    }

    private fun checkActionCondition(actor: Actor, actors: Collection<Actor>): Boolean {
        logger.info(
            "actionCondition.id={} actor.id={} actors.size={} id={} simpleName={}",
            actionCondition?.id,
            actor.id,
            actors.size,
            id,
            simpleName
        )
        return actionCondition?.check(actor, actors) ?: false
    }

    private fun checkActionTarget(actor: Actor, actors: Collection<Actor>): Boolean {
        logger.info(
            "actionTarget.id={} actor.id={} actors.size={} id={} simpleName={}",
            actionTarget?.id,
            actor.id,
            actors.size,
            id,
            simpleName
        )
        val targetedActors = actionTarget?.target(actor, actors)
        if (targetedActors.isNullOrEmpty()) {
            return false
        }
        return checkAbility(actor, targetedActors)
    }

    fun use(actor: Actor, actors: Collection<Actor>): Boolean {
        logger.info(
            "actor.id={} actors.size={} id={} priorityType={} simpleName={}",
            actor.id,
            actors.size,
            id,
            priorityType,
            simpleName
        )
        return checkActionCondition(actor, actors) && checkActionTarget(actor, actors)
    }
}