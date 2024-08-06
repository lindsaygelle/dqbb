package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.*

class ActorAction(
    private val actorCondition: ActorAggregate,
    private val actorPriority: ActorPriority,
    private val actorSelection: ActorAggregate,
    private val ability: Ability<Actor, Actor>,
    val priorityType: PriorityType,
) : Identifier {
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override val uuid: UUID = UUID.randomUUID()

    fun invoke(actor: Actor, actors: Collection<Actor>): Boolean {
        var scannedActors = actorCondition.aggregate(actor, actors)
        if (scannedActors.isEmpty()) {
            return false
        }
        scannedActors = actorSelection.aggregate(actor, actors)
        if (scannedActors.isEmpty()) {
            return false
        }
        scannedActors = actorPriority.prioritize(scannedActors)
        return ability.use(actor, scannedActors.first())
    }
}