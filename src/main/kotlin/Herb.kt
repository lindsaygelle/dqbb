package dqbb

class Herb<A, B : HerbReceiver> : Ability<A, B>(
    invokable = InvokeHerb()
) where A : HerbInvoker, A : HitPointer, A : SleepAccumulator