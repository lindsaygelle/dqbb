package dqbb

class AttackHero<A : AttackInvoker, B : AttackReceiver> : Attack<A, B>() {
    override fun getAttackPoints(invoker: A, receiver: B): Int {
        val attackPointsRange = getAttackPointsRange(invoker, receiver)
        return attackPointsRange.random()
    }

    private fun getAttackPointsRange(invoker: A, receiver: B): IntRange {
        if (invoker.strength < getReceiverDefense(receiver)) {
            return getAttackPointsStandardRange(invoker, receiver)
        }
        return getAttackPointsWeakRange(receiver)
    }

    private fun getAttackPointsWeakRange(receiver: B): IntRange {
        val attackPointsWeakRangeMaximum = getAttackPointsWeakRangeMaximum(receiver)
        val attackPointsWeakRangeMinimum = getAttackPointsWeakRangeMinimum()
        logger.debug(
            "attackPointsWeakRangeMaximum={} attackPointsWeakRangeMinimum={} id={} receiver.id={}",
            attackPointsWeakRangeMaximum,
            attackPointsWeakRangeMinimum,
            id,
            receiver.id,
        )
        return (attackPointsWeakRangeMinimum..attackPointsWeakRangeMaximum)
    }

    private fun getAttackPointsWeakRangeMaximum(receiver: B): Int {
        return (receiver.strength + 4) / 6
    }

    private fun getAttackPointsWeakRangeMinimum(): Int {
        return 0
    }
}