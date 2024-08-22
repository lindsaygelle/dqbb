package dqbb

interface BattleReceiver : AbilityReceiver,
    SleepLimiter,
    SleepResolver,
    StopSpellLimiter,
    StopSpellResolver,
    TurnAccumulator