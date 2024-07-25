package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


abstract class Ability {

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)
    protected abstract fun apply(actor: Actor, otherActor: Actor): Boolean
    protected abstract fun check(actor: Actor, otherActor: Actor): Boolean

    fun use(actor: Actor, otherActor: Actor): Boolean {
        logger.info("actor=$actor otherActor=$otherActor")
        val isAlive = actor.isAlive
        logger.debug("$actor.isAlive=$isAlive")
        if (!isAlive) {
            return false
        }
        val statusSleep = actor.statusSleep
        logger.debug("$actor.statusSleep=$statusSleep")
        if (statusSleep) {
            return false
        }
        val checkValue = check(actor, otherActor)
        logger.info("check=$checkValue")
        if (!checkValue) {
            return false
        }
        val applyValue = apply(actor, otherActor)
        logger.info("apply=$applyValue")
        return applyValue
    }
}
