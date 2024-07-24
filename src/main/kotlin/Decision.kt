package dqbb

class Decision(
    val ability: Ability,
    private val apply: Qualifier,
    private val condition: Qualifier,
    val priority: Priority,
) {
    var actors: List<Actor> = listOf()

    fun isValid(actor: Actor, otherActors: List<Actor>): Boolean {
        println("${this::class.simpleName} ability=${ability::class.simpleName}")
        actors = condition.check(actor, otherActors)
        println("${this::class.simpleName} conditionActors.size=${actors.size}")
        if (actors.isEmpty()) {
            return false
        }
        actors = apply.check(actor, otherActors)
        println("${this::class.simpleName} applyActors.size=${actors.size}")
        return actors.isNotEmpty()
    }
}
