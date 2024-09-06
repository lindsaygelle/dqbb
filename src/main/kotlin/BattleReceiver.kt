package dqbb

interface BattleReceiver : ActionReceiver,
    SleepLimiter,
    SleepResolver,
    StopSpellLimiter,
    StopSpellResolver,
    TurnAccumulator