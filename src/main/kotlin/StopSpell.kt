package dqbb

class StopSpell<A, B : StopSpellReceiver>(magicCost: Int) : AbilityMagic<A, B>(
    invokable = InvokeStopSpell(
        magicCost = magicCost
    )
) where A : HitPointer, A : SleepAccumulator, A : StopSpellAccumulator, A : StopSpellInvoker