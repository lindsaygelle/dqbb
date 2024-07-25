package dqbb

abstract class Ability {
    abstract fun use(actor: Actor, otherActor: Actor): Boolean
}
