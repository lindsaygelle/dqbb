package dqbb

interface BattleReceiver : AbilityReceiver,
    AllegianceKeeper,
    SleepLimiter,
    SleepResolver,
    StopSpellLimiter,
    StopSpellResolver,
    TurnAccumulator