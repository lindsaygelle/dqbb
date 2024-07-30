package dqbb

class Attack(
    condition: ConditionType
) : Ability(
    condition = condition,
) {
    override fun apply(actor: Actor, otherActor: Actor): Boolean {
        val attackPower = actor.getAttackPower(otherActor)
        var attackScore = attackPower
        val defensePower = otherActor.getDefensePower(actor)
        val hitPoints = otherActor.hitPoints
        if (attackPower < 1) {
            attackScore = (0..1).random()
        }
        logger.debug(
            "$this: " +
                    "actor.attackPower=$attackPower " +
                    "actor.attackScore=$attackScore " +
                    "actor.id=$actor " +
                    "otherActor.defensePower=$defensePower " +
                    "otherActor.hitPoints=$hitPoints " +
                    "otherActor.id=$otherActor"
        )
        val excellentMoveMaximum = actor.excellentMoveMaximum
        val excellentMoveMinimum = actor.excellentMoveMinimum
        val excellentMoveRandomRange = (excellentMoveMinimum..excellentMoveMaximum).random()
        logger.debug(
            "$this: " +
                    "actor.excellentMoveMaximum=$excellentMoveMaximum " +
                    "actor.excellentMoveMinimum=$excellentMoveMinimum " +
                    "actor.id=$actor " +
                    "otherActor.id=$otherActor"
        )
        if (excellentMoveRandomRange > 31) {
            val attackValue = actor.attackValue
            attackScore = attackValue
        }
        logger.debug(
            "$this: " +
                    "actor.attackScore=$attackScore " +
                    "actor.id=$actor " +
                    "otherActor.id=$otherActor"
        )
        otherActor.hitPoints -= attackScore
        return otherActor.hitPoints < hitPoints
    }

    override fun check(actor: Actor, otherActor: Actor): Boolean {
        return true
    }
}