package dqbb

class HurtMore<A : HurtMoreInvoker, B : HurtReceiver>(
    magicCost: Int,
) : HurtMagic<A, B>(
    magicCost = magicCost
) {
    override fun getHurtPoints(invoker: A): Int {
        val hurtMore: Int = invoker.hurtMore
        logger.info(
            "id={} invoker.hurtMoreScale={} invoker.hurtMoreShift={} invoker.hurtShift={} invoker.hurtRangeMaximum={} invoker.hurtRangeMinimum={} invoker.id={} invoker.simpleName={} simpleName={}",
            id,
            hurtMore,
            invoker.hurtMoreScale,
            invoker.hurtMoreShift,
            invoker.hurtRangeMaximum,
            invoker.hurtRangeMinimum,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return hurtMore
    }
}