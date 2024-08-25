package dqbb

class HurtMore<A : HurtMoreInvoker, B : HurtReceiver>(
    magicCost: Int,
) : HurtMagic<A, B>(
    magicCost = magicCost
) {
    override fun getHurtPoints(invoker: A): Int {
        val hurtMore = invoker.hurtMore
        logger.debug(
            "id={} invoker.hurtMoreScale={} invoker.hurtMoreShift={} invoker.hurtShift={} invoker.hurtRangeMaximum={} invoker.hurtRangeMinimum={} invoker.id={}",
            id,
            hurtMore,
            invoker.hurtMoreScale,
            invoker.hurtMoreShift,
            invoker.hurtRangeMaximum,
            invoker.hurtRangeMinimum,
            invoker.id
        )
        return hurtMore
    }
}