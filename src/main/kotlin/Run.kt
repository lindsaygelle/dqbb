package dqbb

class Run<A : RunInvoker, B : RunReceiver> : Ability<A, B>() {
    override fun apply(invoker: A, receiver: B): Boolean {
        if (!checkReceiver(receiver) || !checkRequirement(invoker, receiver)) {
            return false
        }
        invoker.isRunning = true
        logger.debug(
            "id={} invoker.id={} invoker.isRunning={} receiver.id={}", id, invoker.id, invoker.isRunning, receiver.id
        )
        return invoker.isRunning
    }

    override fun checkReceiver(receiver: B): Boolean {
        return checkReceiverHitPoints(receiver) || checkReceiverTurnsSleep(receiver)
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.hitPoints={} receiver.id={}", id, receiver.hitPoints, receiver.id
        )
        return receiver.hitPoints == 0
    }

    private fun checkReceiverTurnsSleep(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.id={} receiver.turnsSleep={}", id, receiver.id, receiver.turnsSleep
        )
        return receiver.turnsSleep > 0
    }

    private fun checkRequirement(invoker: A, receiver: B): Boolean {
        return getRequirement(invoker) > getResistance(receiver)
    }

    private fun getRequirement(invoker: A): Int {
        val runRequirement = invoker.runRequirement
        logger.debug(
            "id={} invoker.agility={} invoker.id={} invoker.runRequirement={} invoker.runRangeMaximum={} invoker.runRangeMinimum={}",
            id,
            invoker.agility,
            invoker.id,
            invoker.runRequirement,
            invoker.runRangeMaximum,
            invoker.runRangeMinimum,
        )
        return runRequirement
    }

    private fun getResistance(receiver: B): Int {
        val runResistance = receiver.runResistance
        logger.debug(
            "id={} receiver.agility={} receiver.id={} receiver.runRangeMaximum={} receiver.runRangeMinimum={}",
            id,
            receiver.agility,
            receiver.id,
            receiver.runRangeMaximum,
            receiver.runRangeMinimum
        )
        return runResistance
    }

    override fun use(invoker: A, receiver: B): Boolean {
        if (!checkInvoker(invoker)) {
            return false
        }
        return apply(invoker, receiver)
    }
}