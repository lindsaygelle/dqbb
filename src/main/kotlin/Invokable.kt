package dqbb

interface Invokable<A : Invoker, B : Receiver> : Identifier {
    fun invoke(invoker: A, receiver: B): Boolean
}