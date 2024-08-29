package dqbb

abstract class AbilityItem<A : ItemInvoker, B : Receiver>(
    val itemName: ItemName,
) : Ability<A, B>() {
    final override fun checkInvoker(invoker: A): Boolean {
        return super.checkInvoker(invoker) && checkInvokerItem(invoker)
    }

    private fun checkInvokerItem(invoker: A): Boolean {
        logger.trace(
            "id={} invoker.id={} invoker.simpleName={} itemName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            itemName,
            simpleName
        )
        return checkInvokerItemExists(invoker) && checkInvokerItemCount(invoker)
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

    private fun checkInvokerItemCount(invoker: A): Boolean {
        val itemCount = invoker.items.getOrDefault(itemName, 0)
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} itemCount={} itemName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            itemCount,
            itemName,
            simpleName
        )
        return itemCount > 0
    }

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

    override fun use(invoker: A, receiver: B): Boolean {
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} itemName={} receiver.id={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            itemName,
            receiver.id,
            simpleName
        )
        if (!checkInvoker(invoker) || !checkReceiver(receiver)) {
            return false
        }
        reduceInvokerItemCount(invoker)
        return apply(invoker, receiver)
    }

    override fun toString(): String {
        return "id=$id itemName=$itemName simpleName=$simpleName"
    }
}