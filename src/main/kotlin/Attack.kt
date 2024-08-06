package dqbb

abstract class Attack<A : AttackInvoker, B : AttackReceiver> : Invocation<A, B>() {
    override fun apply(invoker: A, receiver: B): Boolean {
        val hitPoints = receiver.hitPoints
        var attackValue = getAttackValue(invoker, receiver)
        if (checkEvasion(invoker, receiver)) {
            attackValue = 0
        }
        receiver.hitPoints -= attackValue
        return receiver.hitPoints < hitPoints
    }

    override fun check(invoker: A, receiver: B): Boolean {
        return true
    }

    private fun checkEvasion(invoker: A, receiver: B): Boolean {
        return false
    }

    protected abstract fun getAttackValue(invoker: A, receiver: B): Int
}