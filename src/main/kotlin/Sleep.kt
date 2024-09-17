package dqbb

class Sleep<A : SleepInvoker, B : SleepReceiver>(
    magicCost: Int,
) : AbilityMagicRequirement<A, B>(
    magicCost = magicCost
) {
    override fun applyUpdate(invoker: A, receiver: B): Reviewable {
        if (!checkReceiverArmor(receiver)) {
            receiver.turnsSleep = 1
        }
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} receiver.turnsSleep={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            receiver.turnsSleep
        )
        return getReviewable(invoker, receiver)
    }

    override fun checkReceiver(receiver: B): Boolean {
        val checkValue: Boolean = checkReceiverHitPoints(receiver) && checkReceiverTurnsSleep(receiver)
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

    private fun checkReceiverArmor(receiver: B): Boolean {
        val checkValue: Boolean = receiver.armor?.blocksSleep ?: false
        logger.info(
            "checkValue={} id={} receiver.armor.blocksSleep={} receiver.armor.id={} receiver.id={} receiver.simpleName={} simpleName={}",
            checkValue,
            id,
            receiver.armor?.blocksSleep,
            receiver.armor?.id,
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
            simpleName
        )
        return checkValue
    }

    private fun checkReceiverTurnsSleep(receiver: B): Boolean {
        val checkValue: Boolean = receiver.turnsSleep == 0
        logger.info(
            "checkValue={} id={} receiver.id={} receiver.simpleName={} receiver.turnsSleep={} simpleName={}",
            checkValue,
            id,
            receiver.id,
            receiver.simpleName,
            receiver.turnsSleep,
            simpleName
        )
        return checkValue
    }

    override fun getInvokerRequirement(invoker: A): Int {
        val sleepRequirement: Int = invoker.sleepRequirement
        logger.info(
            "id={} invoker.id={} invoker.simpleName={} invoker.sleepRequirement={} invoker.sleepRequirementMaximum={} invoker.sleepRequirementMaximum={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            sleepRequirement,
            invoker.sleepRequirementMaximum,
            invoker.sleepRequirementMinimum,
            simpleName
        )
        return sleepRequirement
    }

    override fun getReceiverResistance(receiver: B): Int {
        val sleepResistance: Int = receiver.sleepResistance
        logger.info(
            "id={} receiver.id={} receiver.simpleName={} receiver.sleepResistance={} receiver.sleepResistanceMaximum={} receiver.sleepResistanceMinimum={} simpleName={}",
            id,
            sleepResistance,
            receiver.id,
            receiver.simpleName,
            receiver.sleepResistanceMaximum,
            receiver.sleepResistanceMinimum,
            simpleName
        )
        return sleepResistance
    }

    private fun getReviewable(invoker: A, receiver: B): Reviewable {
        return ReviewSleep(
            id,
            simpleName,
            invoker.id,
            invoker.name,
            invoker.simpleName,
            magicCost,
            receiver.armor?.blocksSleep,
            receiver.armor?.id,
            receiver.armor?.name,
            receiver.armor?.simpleName,
            receiver.id,
            receiver.name,
            receiver.simpleName,
            receiver.turnsSleep,
        )
    }

    override fun getReviewableInvokerInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, receiver)
    }

    override fun getReviewableReceiverInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, receiver)
    }

    override fun getReviewableRequirementInvalid(invoker: A, receiver: B): Reviewable {
        return getReviewable(invoker, receiver)
    }
}