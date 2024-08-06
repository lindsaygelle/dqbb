package dqbb

class InvokeHeal<A : HealInvoker, B : HealReceiver>(
    magicCost: Int,
) : HealMagic<A, B>(
    magicCost = magicCost
) {
    override fun getHealValue(invoker: A): Int {
        val heal = invoker.heal
        logger.debug(
            "invoker.heal={} invoker.healRangeMaximum={} invoker.healRangeMinimum={} invoker.healScale={} invoker.healShift={} invoker.uuid={} uuid={}",
            heal,
            invoker.healRangeMaximum,
            invoker.healRangeMinimum,
            invoker.healScale,
            invoker.healShift,
            invoker.uuid,
            uuid,
        )
        return heal
    }
}
