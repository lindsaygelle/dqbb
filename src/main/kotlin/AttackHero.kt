package dqbb

class AttackHero<A : AttackInvoker, B : AttackReceiver> : Attack<A, B>() {
    override fun getAttackPoints(invoker: A, receiver: B): Int {
        val attackPointsRange = getAttackPointsRange(invoker, receiver)
        val attackPoints = attackPointsRange.random()
        logger.info(
            "attackPoints={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPoints,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return attackPoints
    }

    private fun getAttackPointsRange(invoker: A, receiver: B): IntRange {
        logger.trace(
            "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        if (invoker.strength < getReceiverDefense(receiver)) {
            return getAttackPointsStandardRange(invoker, receiver)
        }
        return getAttackPointsWeakRange(receiver)
    }

    private fun getAttackPointsWeakRange(receiver: B): IntRange {
        val attackPointsWeakRangeMaximum = getAttackPointsWeakRangeMaximum(receiver)
        val attackPointsWeakRangeMinimum = getAttackPointsWeakRangeMinimum(receiver)
        logger.info(
            "attackPointsWeakRangeMaximum={} attackPointsWeakRangeMinimum={} id={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPointsWeakRangeMaximum,
            attackPointsWeakRangeMinimum,
            id,
            receiver.id,
            receiver.simpleName,
            simpleName,
        )
        return (attackPointsWeakRangeMinimum..attackPointsWeakRangeMaximum)
    }

    private fun getAttackPointsWeakRangeMaximum(receiver: B): Int {
        val attackPointsWeakRangeMaximum = (receiver.strength + 4) / 6
        logger.info(
            "attackPointsWeakRangeMaximum={} id={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPointsWeakRangeMaximum,
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return attackPointsWeakRangeMaximum
    }

    private fun getAttackPointsWeakRangeMinimum(receiver: B): Int {
        val attackPointsWeakRangeMinimum = 0
        logger.info(
            "attackPointsWeakRangeMinimum={} id={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPointsWeakRangeMinimum,
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return attackPointsWeakRangeMinimum
    }
}