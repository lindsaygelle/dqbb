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
                "actionCondition.id={} id={} simpleName={}", field?.id, id, simpleName
            )
        }

    var actionTarget: ActionTarget<A, B>? = null
        set(value) {
            field = value
            logger.debug(
                "actionTarget.id={} id={} simpleName={}", field?.id, id, simpleName
            )
        }

    var attributeSort: AttributeSort<B>? = null
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

    private fun checkAbility(actionInvoker: A, actionReceivers: Collection<B>): Boolean {
        logger.info(
            "actionInvoker.id={} actionReceivers.size={} id={} attributeSort.id={} simpleName={}",
            actionInvoker.id,
            actionReceivers.size,
            id,
            attributeSort?.id,
            simpleName
        )
        val sortedActionInvokers = (attributeSort?.sort(actionReceivers) ?: actionReceivers)
        if (sortedActionInvokers.isEmpty()) {
            return false
        }
        return ability?.use(actionInvoker, sortedActionInvokers.first()) ?: false
    }

    private fun checkActionCondition(actionInvoker: A, actionReceivers: Collection<B>): Boolean {
        logger.info(
            "actionCondition.id={} actionInvoker.id={} actionReceivers.size={} id={} simpleName={}",
            actionCondition?.id,
            actionInvoker.id,
            actionReceivers.size,
            id,
            simpleName
        )
        return actionCondition?.check(actionInvoker, actionReceivers) ?: false
    }

    private fun checkActionTarget(actionInvoker: A, actionReceivers: Collection<B>): Boolean {
        logger.info(
            "actionTarget.id={} actionInvoker.id={} actionReceivers.size={} id={} simpleName={}",
            actionTarget?.id,
            actionInvoker.id,
            actionReceivers.size,
            id,
            simpleName
        )
        val targetedActionInvokers = actionTarget?.target(actionInvoker, actionReceivers)
        if (targetedActionInvokers.isNullOrEmpty()) {
            return false
        }
        return checkAbility(actionInvoker, targetedActionInvokers)
    }

    fun use(actionInvoker: A, actionReceivers: Collection<B>): Boolean {
        logger.info(
            "actionInvoker.id={} actionReceivers.size={} id={} priorityType={} simpleName={}",
            actionInvoker.id,
            actionReceivers.size,
            id,
            priorityType,
            simpleName
        )
        val checkValue =
            checkActionCondition(actionInvoker, actionReceivers) && checkActionTarget(actionInvoker, actionReceivers)
        logger.info(
            "checkValue={} id={} simpleName={}", checkValue, id, simpleName
        )
        return checkValue
    }
}