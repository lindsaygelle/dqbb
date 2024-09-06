package dqbb

class ActionTarget<A : ActionInvoker, B : ActionReceiver>() : ActionRequirement<A, B>() {
    constructor(
        attributeCriteria: Collection<AttributeCriterion<B>>,
        name: String? = null,
        selectionType: SelectionType,
    ) : this() {
        this.attributeCriteria = attributeCriteria
        this.name = name
        this.selectionType = selectionType
    }

    private fun filterReceiver(actionInvoker: A, index: Int, actionReceiver: B): Boolean {
        logger.trace(
            "actionInvoker.id={} actionInvoker.simpleName={} actionReceiver.id={} actionReceiver.simpleName={} id={} index={} simpleName={}",
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceiver.id,
            actionReceiver.simpleName,
            id,
            index,
            simpleName
        )
        return checkSelectionType(actionInvoker, index, actionReceiver) && checkAttributeCriteria(index, actionReceiver)
    }

    fun target(actionInvoker: A, actionReceivers: Collection<B>): Collection<B> {
        logger.info(
            "actionInvoker.id={} actionInvoker.simpleName={} actionReceivers.size={} id={} simpleName={}",
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceivers.size,
            id,
            simpleName
        )
        val filteredActionReceivers = actionReceivers.filterIndexed { index: Int, actionReceiver: B ->
            filterReceiver(actionInvoker, index, actionReceiver)
        }
        logger.info(
            "filteredActionReceivers.size={} id={} simpleName={}", filteredActionReceivers.size, id, simpleName
        )
        return filteredActionReceivers
    }
}