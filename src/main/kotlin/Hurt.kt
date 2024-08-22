package dqbb

class Hurt<A : HurtInvoker, B : HurtReceiver>(
    magicCost: Int,
) : HurtMagic<A, B>(
    magicCost = magicCost
) {
    override fun getHurtPoints(invoker: A): Int {
        val hurt = invoker.hurt
        logger.debug(
            "id={} invoker.hurt={} invoker.hurtRangeMaximum={} invoker.hurtRangeMinimum={} invoker.hurtScale={} invoker.hurtShift={} invoker.id={}",
            id,
            hurt,
            invoker.hurtRangeMaximum,
            invoker.hurtRangeMinimum,
            invoker.hurtScale,
            invoker.hurtShift,
            invoker.id
        )
        return hurt
    }
}