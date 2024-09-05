package dqbb

class ActionTarget<A, B>() : ActionRequirement<A, B>() where A : AbilityInvoker, A : AllegianceKeeper, A : AttributeProvider, B : AbilityReceiver, B : AllegianceKeeper, B : AttributeProvider {
    constructor(
        attributeCriteria: Collection<AttributeCriterion<B>>,
        name: String? = null,
        selectionType: SelectionType,
    ) : this() {
        this.attributeCriteria = attributeCriteria
        this.name = name
        this.selectionType = selectionType
    }

    private fun filterReceiver(invoker: A, index: Int, receiver: B): Boolean {
        return checkSelectionType(invoker, index, receiver) && checkAttributeCriteria(index, receiver)
    }

    fun target(invoker: A, receivers: Collection<B>): Collection<B> {
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} receivers.size={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receivers.size,
            simpleName
        )
        val filteredReceivers = receivers.filterIndexed { index: Int, receiver: B ->
            filterReceiver(invoker, index, receiver)
        }
        logger.info(
            "filteredReceivers.size={} id={} simpleName={}", filteredReceivers.size, id, simpleName
        )
        return filteredReceivers
    }
}