package dqbb

abstract class AbilityItem<A : ItemInvoker, B : Receiver>(
    private val itemName: ItemName,
) : Ability<A, B>() {
    final override fun checkInvoker(invoker: A): Boolean {
        return super.checkInvoker(invoker) && checkInvokerItem(invoker)
    }

    private fun checkInvokerItem(invoker: A): Boolean {
        return checkInvokerItemExists(invoker) && checkInvokerItemCount(invoker)
    }

    private fun checkInvokerItemExists(invoker: A): Boolean {
        val itemExists = invoker.items.contains(itemName)
        logger.debug(
            "id={} invoker.id={} itemExists={} itemName={}", id, invoker.id, itemExists, itemName
        )
        return itemExists
    }

    private fun checkInvokerItemCount(invoker: A): Boolean {
        val itemCount = invoker.items.getOrDefault(itemName, 0)
        logger.debug(
            "id={} invoker.id={} itemCount={} itemName={}", id, invoker.id, itemCount, itemName
        )
        return itemCount > 0
    }

    private fun reduceInvokerItemCount(invoker: A) {
        invoker.items[itemName] = invoker.items.getOrDefault(itemName, 1) - 1
        logger.info(
            "id={} invoker.id={} invoker.items.{}={}", id, invoker.id, itemName, invoker.items.get(itemName)
        )
    }

    override fun use(invoker: A, receiver: B): Boolean {
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