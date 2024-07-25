package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


open class Qualifier(
    checkers: List<Checker>,
    match: Match,
    priority: Priority,
    target: Target,
) : Prioritized {

    private var checkers: List<Checker> = listOf()
        set(value) {
            field = value.sortedBy { checker -> checker.priority }
            logger.debug("checkers.size=${field.size}")
        }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private var match: Match = Match.OR
        set(value) {
            field = value
            logger.debug("match=$field")
        }

    final override var priority: Priority = Priority.LOWEST
        set(value) {
            field = value
            logger.debug("priority=$field")
        }
    private var target: Target = target
        set(value) {
            field = value
            logger.debug("target=$field")
        }

    fun check(actor: Actor, otherActors: List<Actor>): List<Actor> {
        logger.info("match=$match target=$target")
        return otherActors.filter { otherActor ->
            (checkTarget(actor, otherActor) && checkMatch(otherActor))
        }
    }

    private fun checkMatch(actor: Actor): Boolean {
        return when (match) {
            Match.ALL -> matchAll(actor)
            Match.OR -> matchOr(actor)
        }
    }

    private fun checkTarget(actor: Actor, otherActor: Actor): Boolean {
        return when (target) {
            Target.ALLY -> actor.checkAlly(otherActor)
            Target.ANY -> true
            Target.ENEMY -> actor.checkEnemy(otherActor)
            Target.SELF -> actor == otherActor
        }
    }

    private fun matchAll(actor: Actor): Boolean {
        return checkers.all { checker -> checker.check(actor) }
    }

    private fun matchOr(actor: Actor): Boolean {
        return checkers.any { checker -> checker.check(actor) }
    }

    init {
        this.checkers = checkers
        this.match = match
        this.priority = priority
        this.target = target

        logger.info(
            "checkers.size=${this.checkers.size} " +
                    "match=$match " +
                    "priority=$priority " +
                    "target=$target"
        )
    }
}
