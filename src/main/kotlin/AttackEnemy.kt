package dqbb

class AttackEnemy<A : AttackInvoker, B : AttackReceiver> : Attack<A, B>() {
    private fun checkInvokerExcellent(invoker: A): Boolean {
        val excellentAttackRequirement: Int = invoker.excellentAttackRequirement
        val checkValue: Boolean = excellentAttackRequirement == invoker.excellentAttackRequirementMaximum
        logger.info(
            "checkValue={} id={} invoker.excellentAttackRequirement={} invoker.excellentAttackRequirementMaximum={} invoker.excellentAttackRequirementMinimum={} invoker.id={} invoker.simpleName={} simpleName={}",
            checkValue,
            id,
            excellentAttackRequirement,
            invoker.excellentAttackRequirementMaximum,
            invoker.excellentAttackRequirementMinimum,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return checkValue
    }

    private fun checkReceiverExcellent(receiver: B): Boolean {
        logger.info(
            "id={} receiver.canReceiveExcellentAttack={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.canReceiveExcellentAttack,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return receiver.canReceiveExcellentAttack
    }

    override fun getAttackPoints(invoker: A, receiver: B): Int {
        val attackPointsRange: IntRange = getAttackPointsRange(invoker, receiver)
        var attackPoints: Int = attackPointsRange.random()
        if (attackPoints < 0) {
            val attackPointsWeakRange: IntRange = getAttackPointsWeakRange(invoker)
            attackPoints = attackPointsWeakRange.random()
        }
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
        if (checkInvokerExcellent(invoker) && checkReceiverExcellent(receiver)) {
            return getAttackPointsExcellentRange(invoker)
        }
        return getAttackPointsStandardRange(invoker, receiver)
    }

    private fun getAttackPointsExcellentRange(invoker: A): IntRange {
        val attackPointsExcellentRangeMaximum: Int = getAttackPointsExcellentRangeMaximum(invoker)
        val attackPointsExcellentRangeMinimum: Int = getAttackPointsExcellentRangeMinimum(invoker)
        logger.info(
            "attackPointsExcellentRangeMaximum={} attackPointsExcellentRangeMinimum={} id={} invoker.id={} invoker.simpleName={} simpleName={}",
            attackPointsExcellentRangeMaximum,
            attackPointsExcellentRangeMinimum,
            id,
            invoker.id,
            invoker.simpleName,
            simpleName,
        )
        return (attackPointsExcellentRangeMinimum..attackPointsExcellentRangeMaximum)
    }

    private fun getAttackPointsExcellentRangeMaximum(invoker: A): Int {
        val attackPointsExcellentRangeMaximum: Int = getInvokerAttack(invoker)
        logger.info(
            "attackPointsExcellentRangeMaximum={} id={} invoker.id={} invoker.simpleName={} simpleName={}",
            attackPointsExcellentRangeMaximum,
            id,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return attackPointsExcellentRangeMaximum
    }

    private fun getAttackPointsExcellentRangeMinimum(invoker: A): Int {
        val attackPointsExcellentRangeMinimum: Int = getInvokerAttack(invoker) / 2
        logger.info(
            "attackPointsExcellentRangeMinimum={} id={} invoker.id={} invoker.simpleName={} simpleName={}",
            attackPointsExcellentRangeMinimum,
            id,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return attackPointsExcellentRangeMinimum
    }

    private fun getAttackPointsWeakRange(invoker: A): IntRange {
        val attackPointsWeakRangeMaximum: Int = getAttackPointsWeakRangeMaximum(invoker)
        val attackPointsWeakRangeMinimum: Int = getAttackPointsWeakRangeMinimum(invoker)
        logger.info(
            "attackPointsWeakRangeMaximum={} attackPointsWeakRangeMinimum={} id={} invoker.id={} invoker.simpleName={} simpleName={}",
            attackPointsWeakRangeMaximum,
            attackPointsWeakRangeMinimum,
            id,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return (attackPointsWeakRangeMinimum..attackPointsWeakRangeMaximum)
    }

    private fun getAttackPointsWeakRangeMaximum(invoker: A): Int {
        val attackPointsWeakRangeMaximum = 1
        logger.info(
            "attackPointsWeakRangeMaximum={} id={} invoker.id={} invoker.simpleName={} simpleName={}",
            attackPointsWeakRangeMaximum,
            id,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return attackPointsWeakRangeMaximum
    }

    private fun getAttackPointsWeakRangeMinimum(invoker: A): Int {
        val attackPointsWeakRangeMinimum = 0
        logger.info(
            "attackPointsWeakRangeMinimum={} id={} invoker.id={} invoker.simpleName={} simpleName={}",
            attackPointsWeakRangeMinimum,
            id,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return attackPointsWeakRangeMinimum
    }
}