package dqbb

class InvokeHurt<A : HurtInvoker, B : HurtReceiver>(
    magicCost: Int,
) : HurtMagic<A, B>(
    magicCost = magicCost
) {
    override fun getHurtValue(invoker: A): Int {
        val hurt = invoker.hurt
        logger.debug(
            "invoker.hurt={} invoker.hurtRangeMaximum={} invoker.hurtRangeMinimum={} invoker.hurtScale={} invoker.hurtShift={} uuid={}",
            hurt,
            invoker.hurtRangeMaximum,
            invoker.hurtRangeMinimum,
            invoker.hurtScale,
            invoker.hurtShift,
            uuid
        )
        return hurt
    }
}