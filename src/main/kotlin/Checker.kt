package dqbb


interface Checker : Prioritized {
    fun check(actor: Actor): Boolean
}
