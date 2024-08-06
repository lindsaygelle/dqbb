package dqbb

class Attack<A : InvokerAttack, B : ReceiverAttack> : Action<A, B>() {
    override fun apply(invoker: A, receiver: B): Boolean {
        receiver.hitPoints -= 1
        logger.debug(
            "invoker.hashCode=${invoker.hashCode()} " +
            "receiver.hashCode=${receiver.hashCode()} " +
            "receiver.hitPoints=${receiver.hitPoints}"
        )
        return true
    }

    override fun check(invoker: A, receiver: B): Boolean {
        return receiver.isAlive
    }
}