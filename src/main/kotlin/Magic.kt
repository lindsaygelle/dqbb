package dqbb

abstract class Magic(
    magicPoints: Int,
) : Ability() {

    private val magicPoints: Int = maxOf(0, magicPoints)
    override fun apply(actor: Actor, otherActor: Actor): Boolean {
        val checkResistanceValue = checkResistance(actor, otherActor)
        logger.info("checkResistance=$checkResistanceValue")
        if (!checkResistanceValue) {
            return false
        }
        return applyEffect(actor, otherActor)
    }

    protected abstract fun applyEffect(actor: Actor, otherActor: Actor): Boolean
    override fun check(actor: Actor, otherActor: Actor): Boolean {
        val statusStopSpell = actor.statusStopSpell
        logger.debug("$actor.statusStopSpell=$statusStopSpell")
        if (actor.statusStopSpell) {
            return false
        }
        val magicPointsValue = actor.magicPoints - magicPoints
        logger.debug("$actor.magicPoints=${actor.magicPoints} magicPoints=$magicPoints")
        if (magicPointsValue < 0) {
            return false
        }
        actor.magicPoints = magicPointsValue
        return true
    }

    protected abstract fun checkResistance(actor: Actor, otherActor: Actor): Boolean

    init {
        logger.info("magicPoints=${this.magicPoints}")
    }
}

class MagicHeal(
    magicPoints: Int,
) : Magic(
    magicPoints = magicPoints,
) {
    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val hitPointsPrevious = otherActor.hitPoints
        val hitPoints = otherActor.hitPoints + actor.healValue
        logger.debug("hitPointsPrevious=$hitPointsPrevious hitPoints=$hitPoints")
        otherActor.hitPoints = hitPoints
        return otherActor.hitPoints > hitPointsPrevious
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        val hasResistance = false
        logger.debug("hasResistance=$hasResistance")
        return hasResistance
    }
}

/*
class MagicSleep(
    magicPoints: Int,
) : Magic(
    magicPoints = magicPoints,
) {
    override fun use(actor: Actor, otherActor: Actor): Boolean {
        return true
    }
}

 */
