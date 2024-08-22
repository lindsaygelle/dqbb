package dqbb

class HealMore<A : HealMoreInvoker, B : HealReceiver>(
    magicCost: Int,
) : HealMagic<A, B>(
    magicCost = magicCost
) {
    override fun getHealPoints(invoker: A): Int {
        val healMore = invoker.healMore
        logger.debug(
            "id={} invoker.healMore={} invoker.healMoreScale={} invoker.healMoreShift={} invoker.healRangeMaximum={} invoker.healRangeMinimum={} invoker.id={}",
            id,
            healMore,
            invoker.healMoreScale,
            invoker.healMoreShift,
            invoker.healRangeMaximum,
            invoker.healRangeMinimum,
            invoker.id
        )
        return healMore
    }
}