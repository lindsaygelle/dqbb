package dqbb

abstract class AbilityAttack<A : AttackInvoker, B : AttackReceiver> : Ability<A, B>() {

    final override fun apply(invoker: A, receiver: B): Reviewable {
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        if (!checkReceiver(receiver)) {
            return getReviewableReceiverInvalid(invoker, receiver)
        }
        return applyEffect(invoker, receiver)
    }

    private fun applyEffect(invoker: A, receiver: B): Reviewable {
        val attackPoints: Int = getAttackPoints(invoker, receiver)
        var attackType: AttackType = AttackType.MISS
        if (!checkReceiverEvasion(receiver)) {
            receiver.hitPoints -= attackPoints
            attackType = AttackType.HIT
        }
        logger.info(
            "id={} invoker.attackPoints={} invoker.id={} invoker.simpleName={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            attackPoints,
            invoker.id,
            invoker.simpleName,
            receiver.hitPoints,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return getReviewable(invoker, attackPoints, attackType, true, receiver, true)
    }


    private fun calculateAttackPointsStandard(invoker: A, receiver: B): Int {
        val attackPointsStandard: Int = getInvokerAttack(invoker) - (getReceiverDefense(receiver))
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
        val checkValue: Boolean = checkReceiverHitPoints(receiver)
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

    private fun checkReceiverEvasion(receiver: B): Boolean {
        val evasionRequirement: Int = receiver.evasionRequirement
        val checkValue: Boolean = evasionRequirement == receiver.evasionRequirementMaximum
        logger.info(
            "checkValue={} id={} receiver.evasionRequirement={} receiver.evasionRequirementMaximum={} receiver.evasionRequirementMinimum={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            evasionRequirement,
            receiver.evasionRequirementMaximum,
            receiver.evasionRequirementMinimum,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkValue
    }

    private fun checkReceiverHitPoints(receiver: B): Boolean {
        val checkValue: Boolean = receiver.hitPoints > 0
        logger.info(
            "checkValue={} id={} receiver.hitPoints={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            receiver.hitPoints,
            receiver.id,
            receiver.simpleName,
            simpleName,
        )
        return checkValue
    }

    protected abstract fun getAttackPoints(invoker: A, receiver: B): Int

    protected fun getAttackPointsStandardRange(invoker: A, receiver: B): IntRange {
        val attackPointsStandardRangeMaximum: Int = maxOf(1, getAttackPointsStandardRangeMaximum(invoker, receiver))
        val attackPointsStandardRangeMinimum: Int = maxOf(0, getAttackPointsStandardRangeMinimum(invoker, receiver))
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
        val attackPointsStandardMaximum: Int = calculateAttackPointsStandard(invoker, receiver) / 2
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
        val attackPointsStandardMinimum: Int = calculateAttackPointsStandard(invoker, receiver) / 4
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

    protected fun getAttackPointsRangeWeak(invoker: A, receiver: B): IntRange {
        val attackPointsWeakRangeMaximum: Int = getAttackPointsRangeWeakMaximum(invoker, receiver)
        val attackPointsWeakRangeMinimum: Int = getAttackPointsRangeWeakMinimum(invoker, receiver)
        logger.info(
            "attackPointsWeakRangeMaximum={} attackPointsWeakRangeMinimum={} id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} simpleName={}",
            attackPointsWeakRangeMaximum,
            attackPointsWeakRangeMinimum,
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return (attackPointsWeakRangeMinimum..attackPointsWeakRangeMaximum)
    }

    protected abstract fun getAttackPointsRangeWeakMaximum(invoker: A, receiver: B): Int

    protected abstract fun getAttackPointsRangeWeakMinimum(invoker: A, receiver: B): Int


    protected fun getInvokerAttack(invoker: A): Int {
        val attack: Int = getInvokerStrength(invoker) + getInvokerWeaponAttack(invoker)
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
            invoker.weapon?.id
        )
        return invoker.weapon?.attack ?: 0
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
        val defense: Int = (getReceiverAgility(receiver) / 2) + getReceiverEquipmentDefense(receiver)
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
        val equipmentDefense: Int = getReceiverArmorDefense(receiver) + getReceiverShieldDefense(receiver)
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

    private fun getReviewable(
        invoker: A,
        invokerAttack: Int?,
        invokerAttackType: AttackType?,
        invokerIsValid: Boolean,
        receiver: B,
        receiverIsValid: Boolean,
    ): Reviewable {
        return ReviewAttack(
            id,
            simpleName,
            invokerAttack,
            invokerAttackType,
            invoker.id,
            invokerIsValid,
            invoker.name,
            invoker.simpleName,
            invoker.weapon?.attack,
            invoker.weapon?.id,
            invoker.weapon?.name,
            invoker.weapon?.simpleName,
            receiver.armor?.defense,
            receiver.armor?.id,
            receiver.armor?.name,
            receiver.armor?.simpleName,
            receiver.hitPoints,
            receiver.id,
            receiverIsValid,
            receiver.name,
            receiver.shield?.defense,
            receiver.shield?.id,
            receiver.shield?.name,
            receiver.shield?.simpleName,
            receiver.simpleName,
        )
    }

    override fun getReviewableInvokerInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, null, null, false, receiver, false)
    }

    private fun getReviewableReceiverInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, null, null, true, receiver, false)
    }
}