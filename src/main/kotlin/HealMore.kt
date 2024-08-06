package dqbb

class HealMore<A, B : HealReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    invokable = InvokeHealMore(
        magicCost = magicCost
    )
) where A : HealMoreInvoker, A : HitPointer, A : SleepAccumulator, A : StopSpellAccumulator