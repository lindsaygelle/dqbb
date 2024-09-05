    package dqbb

    class Run<A : RunInvoker, B : RunReceiver> : Ability<A, B>() {
        override fun apply(invoker: A, receiver: B): Boolean {
            if (!checkReceiver(receiver) || !checkRequirement(invoker, receiver)) {
                return false
            }
            invoker.isRunning = true
            logger.info(
                "id={} invoker.id={} invoker.isRunning={}invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
                id,
                invoker.id,
                invoker.isRunning,
                invoker.simpleName,
                receiver.id,
                receiver.simpleName,
                simpleName
            )
            return invoker.isRunning
        }

        override fun checkReceiver(receiver: B): Boolean {
            logger.trace(
                "id={} receiver.id={} receiver.simpleName={} simpleName={}",
                id,
                receiver.id,
                receiver.simpleName,
                simpleName
            )
            return checkReceiverHitPoints(receiver) || checkReceiverTurnsSleep(receiver)
        }

        private fun checkReceiverHitPoints(receiver: B): Boolean {
            logger.info(
                "id={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
                id,
                receiver.hitPoints,
                receiver.id,
                receiver.simpleName,
                simpleName
            )
            return receiver.hitPoints == 0
        }

        private fun checkReceiverTurnsSleep(receiver: B): Boolean {
            logger.info(
                "id={} receiver.id={} receiver.simpleName={} receiver.turnsSleep={} simpleName={}",
                id,
                receiver.id,
                receiver.simpleName,
                receiver.turnsSleep,
                simpleName
            )
            return receiver.turnsSleep > 0
        }

        private fun checkRequirement(invoker: A, receiver: B): Boolean {
            logger.trace(
                "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
                id,
                invoker.id,
                invoker.simpleName,
                receiver.id,
                receiver.simpleName,
                simpleName
            )
            return getRequirement(invoker) > getResistance(receiver)
        }

        private fun getRequirement(invoker: A): Int {
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

        private fun getResistance(receiver: B): Int {
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