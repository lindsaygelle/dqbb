package dqbb

class BreatheFire<A, B : BreatheFireReceiver>(
    magicCost: Int,
) : AbilityMagic<A, B>(
    invokable = InvokeBreatheFire(
        magicCost = magicCost
    )
) where A : BreatheFireInvoker, A : HitPointer, A : SleepAccumulator, A : StopSpellAccumulator