package dqbb

class Action(
    private val ability: Ability<Actor, Actor>,
    private val actorCondition: ActorAggregate,
    private val actorPriority: ActorPriority,
    private val actorSelection: ActorAggregate,
    override val priorityType: PriorityType = PriorityType.EQUAL,
) : Prioritized {
    fun use(actor: Actor, actors: Collection<Actor>): Boolean {
        if (actors.isEmpty()) {
            return false
        }
        var aggregatedActors = actorCondition.aggregate(actor, actors)
        if (aggregatedActors.isEmpty()) {
            return false
        }
        aggregatedActors = actorSelection.aggregate(actor, actors)
        if (aggregatedActors.isEmpty()) {
            return false
        }
        aggregatedActors = actorPriority.prioritize(aggregatedActors)
        return ability.use(actor, aggregatedActors.first())
    }
}