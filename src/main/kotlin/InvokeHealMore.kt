package dqbb

class InvokeHealMore<A : HealMoreInvoker, B : HealReceiver>(
    magicCost: Int,
) : HealMagic<A, B>(
    magicCost = magicCost
) {
    override fun getHealValue(invoker: A): Int {
        val healMore = invoker.healMore
        logger.debug(
            "invoker.healMore={} invoker.healMoreScale={} invoker.healMoreShift={} invoker.healRangeMaximum={} invoker.healRangeMinimum={} invoker.uuid={} uuid={}",
            healMore,
            invoker.healMoreScale,
            invoker.healMoreShift,
            invoker.healRangeMaximum,
            invoker.healRangeMinimum,
            invoker.uuid,
            uuid,
        )
        return healMore
    }
}