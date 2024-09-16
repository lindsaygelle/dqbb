package dqbb

class AttackHero<A : AttackInvoker, B : AttackReceiver> : Attack<A, B>() {
    override fun getAttackPoints(invoker: A, receiver: B): Int {
        val attackPointsRange: IntRange = getAttackPointsRange(invoker, receiver)
        val attackPoints: Int = attackPointsRange.random()
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
        return getAttackPointsRangeWeak(invoker, receiver)
    }

    override fun getAttackPointsRangeWeakMaximum(invoker: A, receiver: B): Int {
        val attackPointsWeakRangeMaximum: Int = (receiver.strength + 4) / 6
        logger.info(
            "attackPointsWeakRangeMaximum={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPointsWeakRangeMaximum,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return attackPointsWeakRangeMaximum
    }

    override fun getAttackPointsRangeWeakMinimum(invoker: A, receiver: B): Int {
        val attackPointsWeakRangeMinimum = 0
        logger.info(
            "attackPointsWeakRangeMinimum={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPointsWeakRangeMinimum,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return attackPointsWeakRangeMinimum
    }
}