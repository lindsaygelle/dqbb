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

    private fun filterReceiver(actionInvoker: A, actionReceiver: B, actionReceiverIndex: Int): Boolean {
        val checkValue: Boolean =
            checkSelectionType(actionInvoker, actionReceiver, actionReceiverIndex) && checkAttributeCriteria(
                actionReceiver, actionReceiverIndex
            )
        logger.info(
            "actionInvoker.id={} actionInvoker.simpleName={} actionReceiver.id={} actionReceiver.index={} actionReceiver.simpleName={} checkValue={} id={} simpleName={}",
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceiver.id,
            actionReceiverIndex,
            actionReceiver.simpleName,
            checkValue,
            id,
            simpleName
        )
        return checkValue
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
        val filteredActionReceivers: Collection<B> = actionReceivers.filterIndexed { index: Int, actionReceiver: B ->
            filterReceiver(actionInvoker, actionReceiver, index)
        }
        logger.info(
            "filteredActionReceivers.size={} id={} simpleName={}", filteredActionReceivers.size, id, simpleName
        )
        return filteredActionReceivers
    }
}