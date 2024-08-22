package dqbb

class Heal<A : HealInvoker, B : HealReceiver>(
    magicCost: Int,
) : HealMagic<A, B>(
    magicCost = magicCost
) {
    override fun getHealPoints(invoker: A): Int {
        val heal = invoker.heal
        logger.debug(
            "id={} invoker.heal={} invoker.healRangeMaximum={} invoker.healRangeMinimum={} invoker.healScale={} invoker.healShift={} invoker.id={}",
            id,
            heal,
            invoker.healRangeMaximum,
            invoker.healRangeMinimum,
            invoker.healScale,
            invoker.healShift,
            invoker.id
        )
        return heal
    }
}