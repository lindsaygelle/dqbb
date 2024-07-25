package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Decision(
    val ability: Ability,
    private val apply: Qualifier,
    private val condition: Qualifier,
    priority: Priority,
) : Prioritized {

    var actors: List<Actor> = listOf()

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override var priority: Priority = Priority.LOWEST
        set(value) {
            field = value
            logger.debug("priority=$field")
        }

    fun isValid(actor: Actor, otherActors: List<Actor>): Boolean {
        logger.info("ability=${ability::class.simpleName}")
        actors = condition.check(actor, otherActors)
        logger.info("conditionActors.size=${actors.size}")
        if (actors.isEmpty()) {
            return false
        }
        actors = apply.check(actor, otherActors)
        logger.info("applyActors.size=${actors.size}")
        return actors.isNotEmpty()
    }

    init {
        this.priority = priority

        logger.info(
            "ability=${this.ability} " +
                    "apply=${this.ability} " +
                    "condition=${this.condition} " +
                    "priority=${this.priority}"
        )
    }
}
