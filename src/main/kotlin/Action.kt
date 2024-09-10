package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Action<A : ActionInvoker, B : ActionReceiver>() : Identifier,
    Prioritized {
    var ability: Ability<A, B>? = null
        set(value) {
            field = value
            logger.debug(
                "ability.id={} ability.simpleName={} id={} simpleName={}", field?.id, field?.simpleName, id, simpleName
            )
        }

    var actionCondition: ActionCondition<A, B>? = null
        set(value) {
            field = value
            logger.debug(
                "actionCondition.actionChecks.size={} actionCondition.id={} id={} simpleName={}",
                field?.actionChecks?.size,
                field?.id,
                id,
                simpleName
            )
        }

    var actionTarget: ActionTarget<A, B>? = null
        set(value) {
            field = value
            logger.debug(
                "actionTarget.attributeCriteria={} actionTarget.id={} id={} simpleName={}",
                field?.attributeCriteria,
                field?.id,
                id,
                simpleName
            )
        }

    var attributeSort: AttributeSort<B>? = null
        set(value) {
            field = value
            logger.debug(
                "attributeSort.id={} attributeSort.sortType={} id={} simpleName={}",
                field?.id,
                field?.sortType,
                id,
                simpleName
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
        ability: Ability<A, B>,
        actionCondition: ActionCondition<A, B>,
        actionTarget: ActionTarget<A, B>,
        attributeSort: AttributeSort<B>? = null,
        priorityType: PriorityType = PriorityType.EQUAL,
    ) : this() {
        this.ability = ability
        this.actionCondition = actionCondition
        this.actionTarget = actionTarget
        this.attributeSort = attributeSort
        this.priorityType = priorityType
    }
}