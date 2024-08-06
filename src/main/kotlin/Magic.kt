package dqbb

abstract class Magic<A : InvokerMagic, B : Receiver>(magicCost: Int) : Action<A, B>() {
    private val magicCost: Int = maxOf(0, magicCost)

    override fun apply(invoker: A, receiver: B): Boolean {
        invoker.magicPoints -= magicCost
        logger.debug("invoker.hashCode=${invoker.hashCode()} invoker.magicPoints=${invoker.magicPoints}")
        val checkResistance = checkResistance(invoker, receiver)
        logger.info("checkResistance=$checkResistance")
        if (checkResistance) {
            return false
        }
        applyEffect(invoker, receiver)
        return true
    }

    protected abstract fun applyEffect(invoker: A, receiver: B)

    override fun check(invoker: A, receiver: B): Boolean {
        val invokerHashCode = invoker.hashCode()
        val statusStopSpell = invoker.statusStopSpell
        logger.debug(
            "invoker.hashCode=$invokerHashCode invoker.statusStopSpell=$statusStopSpell"
        )
        if (invoker.statusStopSpell) {
            return false
        }
        val magicPoints = invoker.magicPoints
        logger.debug("invoker.hashCode=$invokerHashCode invoker.magicPoints=$magicPoints magicCost=$magicCost")
        return (magicPoints - magicCost) >= 0
    }

    protected abstract fun checkResistance(invoker: A, receiver: B): Boolean

    override fun toString(): String {
        return "class=${super.toString()} hashCode=${hashCode()} magicCost=$magicCost"
    }
}