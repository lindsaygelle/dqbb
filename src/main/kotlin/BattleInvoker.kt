package dqbb

interface BattleInvoker : AttackInvoker,
    BreatheFireInvoker,
    HealInvoker,
    HealMoreInvoker,
    HerbInvoker,
    HurtInvoker,
    HurtMoreInvoker,
    RunInvoker,
    SleepInvoker,
    StopSpellInvoker