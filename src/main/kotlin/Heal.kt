package dqbb

class Heal<A, B : HealReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    invokable = InvokeHeal(
        magicCost = magicCost
    )
) where A : HealInvoker, A : HitPointer, A : SleepAccumulator, A : StopSpellAccumulator
