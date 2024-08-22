package dqbb

class AttackEnemy<A : AttackInvoker, B : AttackReceiver> : Attack<A, B>() {
    private fun checkInvokerExcellent(invoker: A): Boolean {
        val excellentAttackRequirement = invoker.excellentAttackRequirement
        logger.debug(
            "id={} invoker.excellentAttackRequirement={} invoker.excellentAttackRequirementMaximum={} invoker.excellentAttackRequirementMinimum={} invoker.id={}",
            id,
            excellentAttackRequirement,
            invoker.excellentAttackRequirementMaximum,
            invoker.excellentAttackRequirementMinimum,
            invoker.id
        )
        return excellentAttackRequirement == invoker.excellentAttackRequirementMaximum
    }

    private fun checkReceiverExcellent(receiver: B): Boolean {
        logger.info(
            "id={} receiver.canReceiveExcellentAttack={} receiver.id={}",
            id,
            receiver.canReceiveExcellentAttack,
            receiver.id
        )
        return receiver.canReceiveExcellentAttack
    }

    override fun getAttackPoints(invoker: A, receiver: B): Int {
        val attackPointsRange = getAttackPointsRange(invoker, receiver)
        val attackPoints = attackPointsRange.random()
        if (attackPoints > 0) {
            return attackPoints
        }
        return (0..1).random()
    }

    private fun getAttackPointsRange(invoker: A, receiver: B): IntRange {
        if (checkInvokerExcellent(invoker) && checkReceiverExcellent(receiver)) {
            return getAttackPointsExcellentRange(invoker)
        }
        return getAttackPointsStandardRange(invoker, receiver)
    }

    private fun getAttackPointsExcellentRange(invoker: A): IntRange {
        val attackPointsExcellentRangeMaximum = getAttackPointsExcellentRangeMaximum(invoker)
        val attackPointsExcellentRangeMinimum = getAttackPointsExcellentRangeMinimum(invoker)
        logger.debug(
            "attackPointsExcellentRangeMaximum={} attackPointsExcellentRangeMinimum={} id={} invoker.id={}",
            attackPointsExcellentRangeMaximum,
            attackPointsExcellentRangeMinimum,
            id,
            invoker.id,
        )
        return (attackPointsExcellentRangeMinimum..attackPointsExcellentRangeMaximum)
    }

    private fun getAttackPointsExcellentRangeMaximum(invoker: A): Int {
        return getInvokerAttack(invoker)
    }

    private fun getAttackPointsExcellentRangeMinimum(invoker: A): Int {
        return getInvokerAttack(invoker) / 2
    }
}