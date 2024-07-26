package dqbb


class MagicHeal(
    condition: ConditionType,
) : Magic(
    condition = condition,
    magicPoints = 2,
) {

    override fun applyEffect(actor: Actor, otherActor: Actor): Boolean {
        val healValue = actor.healValue
        val hitPoints = otherActor.hitPoints
        logger.debug(
            "$this: " +
                    "actor.healValue=$healValue " +
                    "actor.id=$actor " +
                    "otherActor.hitPoints=$hitPoints " +
                    "otherActor.id=$otherActor"
        )
        otherActor.hitPoints += healValue
        return otherActor.hitPoints > hitPoints
    }

    override fun checkResistance(actor: Actor, otherActor: Actor): Boolean {
        return false
    }

    override fun getActor(otherActors: Set<Actor>): Actor {
        return when (condition) {
            ConditionType.HIT_POINTS -> otherActors.sortedBy { otherActor ->
                otherActor.hitPoints
            }.first()

            else -> otherActors.random()
        }
    }
}
