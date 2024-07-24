package dqbb

abstract class Magic : Ability() {

}

class MagicHeal : Magic() {
    override fun use(actor: Actor, otherActor: Actor): Boolean {
        otherActor.hitPoints +=1
        return true
    }
}

class MagicSleep : Magic() {
    override fun use(actor: Actor, otherActor: Actor): Boolean {
        return true
    }
}