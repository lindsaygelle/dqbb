package dqbb

interface AttackReceiver : AgilityPointer,
    ArmorWearer,
    AttackEvader,
    HitPointer,
    Receiver,
    ShieldWielder,
    StrengthPointer {
    var canReceiveExcellentAttack: Boolean
}