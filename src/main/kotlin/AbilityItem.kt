package dqbb

abstract class AbilityItem<A : ItemInvoker, B : Receiver>(
    val itemName: ItemName,
) : Ability<A, B>() {
    override fun apply(invoker: A, receiver: B): Reviewable {
        reduceInvokerItemCount(invoker)
        if (!checkReceiver(receiver)) {
            return getReviewableReceiverInvalid(invoker, receiver)
        }
        return applyEffect(invoker, receiver)
    }

    protected abstract fun applyEffect(invoker: A, receiver: B): Reviewable

    final override fun checkInvoker(invoker: A): Boolean {
        return super.checkInvoker(invoker) && checkInvokerItem(invoker)
    }

    private fun checkInvokerItem(invoker: A): Boolean {
        return checkInvokerItemExists(invoker) && checkInvokerItemCount(invoker)
    }

    private fun checkInvokerItemCount(invoker: A): Boolean {
        val itemCount: Int = invoker.items.getOrDefault(itemName, 0)
        val checkValue: Boolean = itemCount > 0
        logger.info(
            "checkValue={} id={} invoker.id={} invoker.simpleName={} itemCount={} itemName={} simpleName={}",
            checkValue,
            id,
            invoker.id,
            invoker.simpleName,
            itemCount,
            itemName,
            simpleName
        )
        return checkValue
    }

    private fun checkInvokerItemExists(invoker: A): Boolean {
        val itemExists = invoker.items.contains(itemName)
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} itemExists={} itemName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            itemExists,
            itemName,
            simpleName
        )
        return itemExists
    }

    protected abstract fun getReviewableReceiverInvalid(invoker: A, receiver: B): Reviewable

    private fun reduceInvokerItemCount(invoker: A) {
        invoker.items[itemName] = invoker.items.getOrDefault(itemName, 1) - 1
        logger.info(
            "id={} invoker.id={} invoker.items.{}={} invoker.simpleName={} itemName={} simpleName={}",
            id,
            invoker.id,
            itemName,
            invoker.items[itemName],
            invoker.simpleName,
            itemName,
            simpleName
        )
    }

    override fun toString(): String {
        return "id=$id itemName=$itemName simpleName=$simpleName"
    }
}