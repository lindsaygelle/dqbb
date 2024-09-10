package dqbb

class Hurt<A : HurtInvoker, B : HurtReceiver>(
    magicCost: Int,
) : HurtMagic<A, B>(
    magicCost = magicCost
) {
    override fun getHurtPoints(invoker: A): Int {
        val hurt: Int = invoker.hurt
        logger.info(
            "id={} invoker.hurt={} invoker.hurtRangeMaximum={} invoker.hurtRangeMinimum={} invoker.hurtScale={} invoker.hurtShift={} invoker.id={} invoker.simpleName={} simpleName={}",
            id,
            hurt,
            invoker.hurtRangeMaximum,
            invoker.hurtRangeMinimum,
            invoker.hurtScale,
            invoker.hurtShift,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return hurt
    }
}