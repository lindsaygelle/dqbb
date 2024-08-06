package dqbb

class InvokeRun<A : RunInvoker, B : RunReceiver> : Invocation<A, B>() {
    override fun apply(invoker: A, receiver: B): Boolean {
        invoker.statusRunning = true
        return invoker.statusRunning
    }

    override fun check(invoker: A, receiver: B): Boolean {
        if (!checkInvoker(invoker)) {
            return false
        }
        if (checkReceiver(receiver)) {
            return true
        }
        if (!checkRequirement(invoker, receiver)) {
            return false
        }
        return apply(invoker, receiver)
    }

    private fun checkInvoker(invoker: A): Boolean {
        return checkInvokerStatusRunning(invoker)
    }

    private fun checkInvokerStatusRunning(invoker: A): Boolean {
        val statusRunning = invoker.statusRunning
        logger.debug(
            "invoker.statusRunning={} invoker.uuid={} uuid={}", statusRunning, invoker.uuid, uuid
        )
        return !statusRunning
    }

    private fun checkReceiver(receiver: B): Boolean {
        val statusSleep = receiver.statusSleep
        logger.debug(
            "receiver.statusSleep={} receiver.uuid={} uuid={}", statusSleep, receiver.uuid, uuid
        )
        return statusSleep
    }

    private fun checkRequirement(invoker: A, receiver: B): Boolean {
        val invokerRequirement = getRequirement(invoker)
        val receiverResistance = getResistance(receiver)
        val checkResult = invokerRequirement > receiverResistance
        logger.info(
            "checkResult={} invoker.requirement={} invoker.uuid={} receiver.resistance={} receiver.uuid={} uuid={}",
            checkResult,
            invokerRequirement,
            invoker.uuid,
            receiverResistance,
            receiver.uuid,
            uuid
        )
        return checkResult
    }

    private fun getRequirement(invoker: A): Int {
        val runRequirement = invoker.runRequirement
        logger.debug(
            "invoker.agility={} receiver.runRangeMaximum={} receiver.runRangeMinimum={} invoker.runRequirement={} invoker.uuid={} uuid={}",
            invoker.agility,
            invoker.runRangeMaximum,
            invoker.runRangeMinimum,
            runRequirement,
            invoker.uuid,
            uuid
        )
        return runRequirement
    }

    private fun getResistance(receiver: B): Int {
        val runResistance = receiver.runResistance
        logger.debug(
            "receiver.agility={} receiver.runRangeMaximum={} receiver.runRangeMinimum={} receiver.runResistance={} receiver.runShift={} receiver.uuid={}",
            receiver.agility,
            runResistance,
            receiver.runRangeMaximum,
            receiver.runRangeMinimum,
            receiver.runShift,
            receiver.uuid
        )
        return runResistance
    }
}