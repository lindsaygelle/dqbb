package dqbb

interface RunInvoker : Invoker,
    RunRequester {
    var statusRunning: Boolean
}