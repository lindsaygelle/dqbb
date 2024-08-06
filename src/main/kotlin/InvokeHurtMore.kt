package dqbb

class InvokeHurtMore<A : HurtMoreInvoker, B : HurtReceiver>(
    magicCost: Int,
) : HurtMagic<A, B>(
    magicCost = magicCost
) {
    override fun getHurtValue(invoker: A): Int {
        val hurtMore = invoker.hurtMore
        logger.debug(
            "invoker.hurtMore={} invoker.hurtMoreScale={} invoker.hurtMoreShift={} invoker.hurtRangeMaximum={} invoker.hurtRangeMinimum={} uuid={}",
            hurtMore,
            invoker.hurtMoreScale,
            invoker.hurtMoreShift,
            invoker.hurtRequirementMaximum,
            invoker.hurtRequirementMinimum,
            uuid
        )
        return hurtMore
    }
}