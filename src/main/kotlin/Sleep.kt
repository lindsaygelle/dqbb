package dqbb

class Sleep<A, B : SleepReceiver>(magicCost: Int) : AbilityMagic<A, B>(
    invokable = InvokeSleep(
        magicCost = magicCost
    )
) where A : HitPointer, A : SleepAccumulator, A : SleepInvoker, A : StopSpellAccumulator