package dqbb

class ActionCheck<A : ActionInvoker, B : ActionReceiver>() : ActionRequirement<A, B>(),
        Prioritized {
    override var priorityType: PriorityType = PriorityType.EQUAL
        set(value) {
            field = value
            logger.debug(
                "id={} priorityType={} simpleName={}", id, field, simpleName
            )
        }

    constructor(
        attributeCriteria: Collection<AttributeCriterion<B>>,
        selectionType: SelectionType,
    ) : this() {
        this.attributeCriteria = attributeCriteria
        this.selectionType = selectionType
    }

    fun check(actionInvoker: A, actionReceivers: Collection<B>): Boolean {
        logger.info(
            "actionInvoker.id={} actionInvoker.simpleName={} actionReceivers.size={} attributeCriteria.size={} id={} selectionType={} simpleName={}",
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceivers.size,
            attributeCriteria.size,
            id,
            selectionType,
            simpleName
        )
        val checkValue = actionReceivers.filterIndexed { index: Int, receiver: B ->
            checkSelectionType(actionInvoker, receiver, index)
        }.withIndex().all { (index: Int, receiver: B) ->
            checkAttributeCriteria(receiver, index)
        }
        logger.info(
            "checkValue={} id={} simpleName={}", checkValue, id, simpleName
        )
        return checkValue
    }

    override fun toString(): String {
        return "attributeCriteria.size=${attributeCriteria.size} id=$id name=$name priorityType=$priorityType selectionType=$selectionType simpleName=$simpleName"
    }
}