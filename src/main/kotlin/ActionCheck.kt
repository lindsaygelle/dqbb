package dqbb

class ActionCheck<A, B>() : ActionRequirement<A, B>(),
    Prioritized where A : AbilityInvoker, A : AllegianceKeeper, A : AttributeProvider, B : AbilityReceiver, B : AllegianceKeeper, B : AttributeProvider {
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

    fun check(invoker: A, receivers: Collection<B>): Boolean {
        logger.info(
            "attributeCriteria.size={} id={} invoker.id={} invoker.simpleName={} receivers.size={} selectionType={} simpleName={}",
            attributeCriteria.size,
            id,
            invoker.id,
            invoker.simpleName,
            receivers.size,
            selectionType,
            simpleName
        )
        val checkValue = receivers.filterIndexed { index: Int, receiver: B ->
            checkSelectionType(invoker, index, receiver)
        }.withIndex().all { (index: Int, receiver: B) ->
            checkAttributeCriteria(index, receiver)
        }
        logger.info(
            "checkValue={} id={} simpleName={}", checkValue, id, simpleName
        )
        return checkValue
    }
}