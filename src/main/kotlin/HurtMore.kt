package dqbb


class HurtMore<A, B : HurtReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    invokable = InvokeHurtMore(
        magicCost = magicCost
    )
) where A : HitPointer, A : HurtMoreInvoker, A : SleepAccumulator, A : StopSpellAccumulator