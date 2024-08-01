package dqbb

//import org.apache.logging.log4j.LogManager
//import org.apache.logging.log4j.Logger


class State(
    private val matchType: MatchType,
    qualifiers: List<Qualify>,
) : Identifier {

    val actors: MutableSet<Actor> = mutableSetOf()

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    private val logger: String? = null //: Logger = LogManager.getLogger(this::class.simpleName)

    private val qualifiers: List<Qualify> = qualifiers.sortedByDescending { it.priorityType.ordinal }

    fun check(actor: Actor, otherActors: Collection<Actor>): Boolean {
        this.actors.clear() // Make sure to clear this, otherwise it is possible to reuse? Could be a method?
        println(//logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "matchType=$matchType " +
                    "otherActors.size=${otherActors.size} " +
                    "qualifiers.size=${qualifiers.size}"
        )
        val matchValue = when (matchType) {
            MatchType.ALL -> matchAll(actor, otherActors)
            MatchType.ANY -> matchAny(actor, otherActors)
        }
        println(//logger.debug(
            "$this: " +
                    "matchValue=$matchValue"
        )
        return matchValue
    }

    private fun matchAll(actor: Actor, otherActors: Collection<Actor>): Boolean {
        this.qualifiers.forEachIndexed { index, qualify ->
            val actors = performMatch(actor, index, otherActors, qualify)
            if (actors.isEmpty()) {
                return false
            }
            this.actors.addAll(actors)
        }
        return true
    }

    private fun matchAny(actor: Actor, otherActors: Collection<Actor>): Boolean {
        this.qualifiers.forEachIndexed { index, qualify ->
            val actors = performMatch(actor, index, otherActors, qualify)
            if (actors.isNotEmpty()) {
                this.actors.addAll(actors)
                return true
            }
        }
        return false
    }

    private fun performMatch(actor: Actor, index: Int, otherActors: Collection<Actor>, qualify: Qualify): Set<Actor> {
        println(//logger.debug(
            "$this: " +
                    "actor.id=${actor.id} " +
                    "index=$index " +
                    "otherActors.size=${otherActors.size} " +
                    "qualify.id=${qualify.id}"
        )
        val actors = qualify.qualify(actor, otherActors)
        println(//logger.debug(
            "$this: " +
                    "actors.size=${actors.size}"
        )
        return actors
    }
}
