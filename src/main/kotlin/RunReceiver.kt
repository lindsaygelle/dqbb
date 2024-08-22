package dqbb

interface RunReceiver : HitPointer,
    Receiver,
    RunResister,
    SleepAccumulator