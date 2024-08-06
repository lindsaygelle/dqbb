package dqbb

interface BattleReceiver : AttackReceiver,
    BreatheFireReceiver,
    HealReceiver,
    HerbReceiver,
    HurtReceiver,
    RunReceiver,
    SleepReceiver,
    SleepResolver,
    StopSpellReceiver,
    StopSpellResolver {
    val isAlive: Boolean
        get() = hitPoints > 0
}