package dqbb

class Run<A, B : RunReceiver> : Ability<A, B>(
    invokable = InvokeRun()
) where A : HitPointer, A : RunInvoker, A : SleepAccumulator