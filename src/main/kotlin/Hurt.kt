package dqbb

class Hurt<A, B : HurtReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    invokable = InvokeHurt(
        magicCost = magicCost
    )
) where A : HitPointer, A : HurtInvoker, A : SleepAccumulator, A : StopSpellAccumulator
