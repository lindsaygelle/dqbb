package dqbb

interface RunInvoker : Invoker,
    RunRequester {
    var isRunning: Boolean
}