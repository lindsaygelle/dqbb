package dqbb

abstract class Attack<A : AttackInvoker, B : AttackReceiver> : Ability<A, B>() {
    final override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        val attackPoints = getAttackPoints(invoker, receiver)
        logger.debug(
            "attackPoints={} id={} invoker.id={} receiver.id={}", attackPoints, id, invoker.id, receiver.id
        )
        if (checkReceiverEvasion(receiver)) {
            return false
        }
        receiver.hitPoints -= attackPoints
        logger.info(
            "attackPoints={} id={} invoker.id={} receiver.hitPoints={} receiver.id={}",
            attackPoints,
            id,
            invoker.id,
            receiver.hitPoints,
            receiver.id
        )
        return receiver.hitPoints < hitPoints
    }

    private fun calculateAttackPointsStandard(invoker: A, receiver: B): Int {
        return getInvokerAttack(invoker) - (getReceiverDefense(receiver))
    }

    final override fun checkReceiver(receiver: B): Boolean {
        return checkReceiverHitPoints(receiver)
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        logger.debug(
            "id={} receiver.hitPoints={} receiver.id={}", id, receiver.hitPoints, receiver.id,
        )
        return receiver.hitPoints > 0
    }

    private fun checkReceiverEvasion(receiver: B): Boolean {
        val evasionRequirement = receiver.evasionRequirement
        logger.debug(
            "id={} receiver.evasionRequirement={} receiver.evasionRequirementMaximum={} receiver.evasionRequirementMinimum={} receiver.id={}",
            id,
            evasionRequirement,
            receiver.evasionRequirementMaximum,
            receiver.evasionRequirementMinimum,
            receiver.id
        )
        return evasionRequirement == receiver.evasionRequirementMaximum
    }

    protected abstract fun getAttackPoints(invoker: A, receiver: B): Int

    protected fun getInvokerAttack(invoker: A): Int {
        return getInvokerStrength(invoker) + getInvokerWeaponAttack(invoker)
    }

    private fun getInvokerStrength(invoker: A): Int {
        logger.debug(
            "id={} invoker.id={} invoker.strength={}", id, invoker.id, invoker.strength
        )
        return invoker.strength
    }

    private fun getInvokerWeaponAttack(invoker: A): Int {
        logger.debug(
            "id={} invoker.id={} invoker.weapon.attack={} invoker.weapon.id={}",
            id,
            invoker.id,
            invoker.weapon?.attack,
            invoker.weapon?.attack
        )
        return invoker.weapon?.attack ?: 0
    }

    protected fun getAttackPointsStandardRange(invoker: A, receiver: B): IntRange {
        val attackPointsStandardRangeMaximum = getAttackPointsStandardRangeMaximum(invoker, receiver)
        val attackPointsStandardRangeMinimum = getAttackPointsStandardRangeMinimum(invoker, receiver)
        logger.debug(
            "attackPointsStandardRangeMaximum={} attackPointsStandardRangeMinimum={} id={} invoker.id={} receiver.id={}",
            attackPointsStandardRangeMaximum,
            attackPointsStandardRangeMinimum,
            id,
            invoker.id,
            receiver.id
        )
        return (attackPointsStandardRangeMinimum..attackPointsStandardRangeMaximum)
    }

    private fun getAttackPointsStandardRangeMaximum(invoker: A, receiver: B): Int {
        return calculateAttackPointsStandard(invoker, receiver) / 2
    }

    private fun getAttackPointsStandardRangeMinimum(invoker: A, receiver: B): Int {
        return calculateAttackPointsStandard(invoker, receiver) / 4
    }

    private fun getReceiverAgility(receiver: B): Int {
        logger.debug(
            "id={} receiver.agility={} receiver.id={}", id, receiver.agility, receiver.id
        )
        return receiver.agility
    }

    private fun getReceiverArmorDefense(receiver: B): Int {
        logger.debug(
            "id={} receiver.armor.defense={} receiver.armor.id={} receiver.id={}",
            id,
            receiver.armor?.defense,
            receiver.armor?.id,
            receiver.id
        )
        return receiver.armor?.defense ?: 0
    }

    protected fun getReceiverDefense(receiver: B): Int {
        return (getReceiverAgility(receiver) / 2) + getReceiverEquipmentDefense(receiver)
    }

    private fun getReceiverEquipmentDefense(receiver: B): Int {
        return getReceiverArmorDefense(receiver) + getReceiverShieldDefense(receiver)
    }

    private fun getReceiverShieldDefense(receiver: B): Int {
        logger.debug(
            "id={} receiver.id={} receiver.shield.defense={} receiver.shield.id={}",
            id,
            receiver.id,
            receiver.shield?.defense,
            receiver.shield?.id,
        )
        return receiver.shield?.defense ?: 0
    }

    final override fun use(invoker: A, receiver: B): Boolean {
        if (!checkInvoker(invoker) || !checkReceiver(receiver)) {
            return false
        }
        return apply(invoker, receiver)
    }
}