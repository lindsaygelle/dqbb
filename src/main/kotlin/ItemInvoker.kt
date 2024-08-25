package dqbb

interface ItemInvoker : Invoker {
    val items: MutableMap<ItemName, Int>
}