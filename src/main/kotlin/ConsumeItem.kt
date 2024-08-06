package dqbb

abstract class ConsumeItem<A : ItemInvoker, B : Receiver>(
    private val itemName: ItemName,
) : Invocation<A, B>() {
    override fun check(invoker: A, receiver: B): Boolean {
        if (!checkInvoker(invoker) || !checkReceiver(receiver)) {
            return false
        }
        invoker.items[itemName] = (invoker.items.getOrDefault(itemName, 1) - 1)
        return apply(invoker, receiver)
    }

    private fun checkInvoker(invoker: A): Boolean {
        return checkInvokerItemCount(invoker)
    }

    private fun checkInvokerItemCount(invoker: A): Boolean {
        val itemCount = invoker.items.getOrDefault(itemName, 0)
        logger.debug(
            "invoker.items.{}={} invoker.uuid={} uuid={}", itemName, itemCount, invoker.uuid, uuid
        )
        return itemCount > 0
    }

    protected abstract fun checkReceiver(receiver: B): Boolean
}