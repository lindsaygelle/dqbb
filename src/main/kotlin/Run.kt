package dqbb

class Run<A : RunInvoker, B : RunReceiver> : Ability<A, B>() {
    override fun apply(invoker: A, receiver: B): Boolean {
        val checkValue: Boolean = checkReceiver(receiver) || checkRequirement(invoker, receiver)
        invoker.isRunning = checkValue
        logger.info(
            "checkValue={} id={} invoker.id={} invoker.isRunning={}invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            invoker.id,
            invoker.isRunning,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkValue
    }

    override fun checkReceiver(receiver: B): Boolean {
        val checkValue: Boolean = checkReceiverHitPoints(receiver) || checkReceiverTurnsSleep(receiver)
        logger.info(
            "checkValue={} id={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkValue
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        val checkValue: Boolean = receiver.hitPoints == 0
        logger.info(
            "checkValue={} id={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            receiver.hitPoints,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkValue
    }

    private fun checkReceiverTurnsSleep(receiver: B): Boolean {
        val checkValue: Boolean = receiver.turnsSleep > 0
        logger.info(
            "checkValue={} id={} receiver.id={} receiver.simpleName={} receiver.turnsSleep={} simpleName={}",
            checkValue,
            id,
            receiver.id,
            receiver.simpleName,
            receiver.turnsSleep,
            simpleName
        )
        return checkValue
    }

    private fun checkRequirement(invoker: A, receiver: B): Boolean {
        val checkValue: Boolean = getInvokerRequirement(invoker) > getReceiverResistance(receiver)
        logger.trace(
            "checkValue={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkValue
    }

    private fun getInvokerRequirement(invoker: A): Int {
        val runRequirement = invoker.runRequirement
        logger.info(
            "id={} invoker.agility={} invoker.id={} invoker.runRequirement={} invoker.runRangeMaximum={} invoker.runRangeMinimum={} invoker.simpleName={} simpleName={}",
            id,
            invoker.agility,
            invoker.id,
            invoker.runRequirement,
            invoker.runRangeMaximum,
            invoker.runRangeMinimum,
            invoker.simpleName,
            simpleName
        )
        return runRequirement
    }

    private fun getReceiverResistance(receiver: B): Int {
        val runResistance = receiver.runResistance
        logger.info(
            "id={} receiver.agility={} receiver.id={} receiver.runRangeMaximum={} receiver.runRangeMinimum={} receiver.simpleName={} simpleName={}",
            id,
            receiver.agility,
            receiver.id,
            receiver.runRangeMaximum,
            receiver.runRangeMinimum,
            receiver.simpleName,
            simpleName
        )
        return runResistance
    }

    override fun use(invoker: A, receiver: B): Boolean {
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        if (!checkInvoker(invoker)) {
            return false
        }
        return apply(invoker, receiver)
    }
}