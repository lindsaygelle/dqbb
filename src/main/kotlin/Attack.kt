package dqbb

abstract class Attack<A : AttackInvoker, B : AttackReceiver> : Ability<A, B>() {
    final override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        val attackPoints = getAttackPoints(invoker, receiver)
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
        if (checkReceiverEvasion(receiver)) {
            return false
        }
        receiver.hitPoints -= attackPoints
        logger.info(
            "attackPoints={} id={} invoker.id={} invoker.simpleName={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPoints,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.hitPoints,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return receiver.hitPoints < hitPoints
    }

    private fun calculateAttackPointsStandard(invoker: A, receiver: B): Int {
        val attackPointsStandard = getInvokerAttack(invoker) - (getReceiverDefense(receiver))
        logger.info(
            "attackPointsStandard={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPointsStandard,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return attackPointsStandard
    }

    final override fun checkReceiver(receiver: B): Boolean {
        logger.trace(
            "id={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkReceiverHitPoints(receiver)
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        logger.info(
            "id={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.hitPoints,
            receiver.id,
            receiver.simpleName,
            simpleName,
        )
        return receiver.hitPoints > 0
    }

    private fun checkReceiverEvasion(receiver: B): Boolean {
        val evasionRequirement = receiver.evasionRequirement
        logger.info(
            "id={} receiver.evasionRequirement={} receiver.evasionRequirementMaximum={} receiver.evasionRequirementMinimum={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            evasionRequirement,
            receiver.evasionRequirementMaximum,
            receiver.evasionRequirementMinimum,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return evasionRequirement == receiver.evasionRequirementMaximum
    }

    protected abstract fun getAttackPoints(invoker: A, receiver: B): Int

    protected fun getInvokerAttack(invoker: A): Int {
        val attack = getInvokerStrength(invoker) + getInvokerWeaponAttack(invoker)
        logger.info(
            "attack={} id={} invoker.id={} invoker.simpleName={} simpleName={}",
            attack,
            id,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return attack
    }

    private fun getInvokerStrength(invoker: A): Int {
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} invoker.strength={}",
            id,
            invoker.id,
            invoker.simpleName,
            invoker.strength
        )
        return invoker.strength
    }

    private fun getInvokerWeaponAttack(invoker: A): Int {
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} invoker.weapon.attack={} invoker.weapon.id={}",
            id,
            invoker.id,
            invoker.simpleName,
            invoker.weapon?.attack,
            invoker.weapon?.attack
        )
        return invoker.weapon?.attack ?: 0
    }

    protected fun getAttackPointsStandardRange(invoker: A, receiver: B): IntRange {
        val attackPointsStandardRangeMaximum = getAttackPointsStandardRangeMaximum(invoker, receiver)
        val attackPointsStandardRangeMinimum = getAttackPointsStandardRangeMinimum(invoker, receiver)
        logger.info(
            "attackPointsStandardRangeMaximum={} attackPointsStandardRangeMinimum={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPointsStandardRangeMaximum,
            attackPointsStandardRangeMinimum,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return (attackPointsStandardRangeMinimum..attackPointsStandardRangeMaximum)
    }

    private fun getAttackPointsStandardRangeMaximum(invoker: A, receiver: B): Int {
        val attackPointsStandardMaximum = calculateAttackPointsStandard(invoker, receiver) / 2
        logger.info(
            "attackPointsStandardMaximum={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPointsStandardMaximum,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            invoker.simpleName,
            simpleName
        )
        return attackPointsStandardMaximum
    }

    private fun getAttackPointsStandardRangeMinimum(invoker: A, receiver: B): Int {
        val attackPointsStandardMinimum = calculateAttackPointsStandard(invoker, receiver) / 4
        logger.info(
            "attackPointsStandardMinimum={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPointsStandardMinimum,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return attackPointsStandardMinimum
    }

    private fun getReceiverAgility(receiver: B): Int {
        logger.info(
            "id={} receiver.agility={} receiver.id={} receiver.simpleName={}",
            id,
            receiver.agility,
            receiver.id,
            receiver.simpleName
        )
        return receiver.agility
    }

    private fun getReceiverArmorDefense(receiver: B): Int {
        logger.info(
            "id={} receiver.armor.defense={} receiver.armor.id={} receiver.id={} receiver.simpleName={}",
            id,
            receiver.armor?.defense,
            receiver.armor?.id,
            receiver.id,
            receiver.simpleName
        )
        return receiver.armor?.defense ?: 0
    }

    protected fun getReceiverDefense(receiver: B): Int {
        val defense = (getReceiverAgility(receiver) / 2) + getReceiverEquipmentDefense(receiver)
        logger.info(
            "defense={} id={} receiver.id={} receiver.simpleName={} simpleName={}",
            defense,
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return defense
    }

    private fun getReceiverEquipmentDefense(receiver: B): Int {
        val equipmentDefense = getReceiverArmorDefense(receiver) + getReceiverShieldDefense(receiver)
        logger.info(
            "equipmentDefense={} id={} receiver.id={} receiver.simpleName={} simpleName={}",
            equipmentDefense,
            id,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return equipmentDefense
    }

    private fun getReceiverShieldDefense(receiver: B): Int {
        logger.info(
            "id={} receiver.id={} receiver.shield.defense={} receiver.shield.id={} receiver.simpleName={}",
            id,
            receiver.id,
            receiver.shield?.defense,
            receiver.shield?.id,
            receiver.simpleName
        )
        return receiver.shield?.defense ?: 0
    }

    final override fun use(invoker: A, receiver: B): Boolean {
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        if (!checkInvoker(invoker) || !checkReceiver(receiver)) {
            return false
        }
        return apply(invoker, receiver)
    }
}