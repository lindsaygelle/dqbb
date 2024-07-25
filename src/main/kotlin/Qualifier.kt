package dqbb


open class Qualifier(
    checkers: List<Checker> = listOf(),
    private val match: Match = Match.OR,
    final override val priority: Priority = Priority.HIGHEST,
    private val target: Target = Target.ANY,
) : Prioritized {

    private val checkers: List<Checker> = checkers.sortedBy { checker: Checker -> checker.priority }

    fun check(actor: Actor, otherActors: List<Actor>): List<Actor> {
        println("${this::class.simpleName} checkers.size=${checkers.size} match=$match target=$target")
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
}
