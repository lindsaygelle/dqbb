package dqbb

class Attack(
    condition: ConditionType
) : Ability(
    condition = condition,
) {
    override fun apply(actor: Actor, otherActor: Actor): Boolean {
        val attackPower = actor.getAttackPower(otherActor)
        val defensePower = otherActor.getDefensePower(actor)
        var attackScore = attackPower
        if (attackPower < 1) {
            attackScore = (0..1).random()
        }
        val excellentMoveChance = actor.excellentMoveChance
        val excellentMoveScore = (0..excellentMoveChance).random()
        if (excellentMoveScore > 31) {
            val attackValue = actor.attackValue
            attackScore = attackValue
        }
        val hitPoints = otherActor.hitPoints
        logger.debug(
            "$this: " +
                    "actor.attackPower=$attackPower " +
                    "actor.attackScore=$attackScore " +
                    "actor.excellentMoveChance=$excellentMoveChance " +
                    "actor.excellentMoveScore=$excellentMoveScore " +
                    "actor.id=$actor " +
                    "otherActor.defensePower=$defensePower " +
                    "otherActor.hitPoints=$hitPoints " +
                    "otherActor.id=$otherActor"
        )
        otherActor.hitPoints -= attackScore
        return otherActor.hitPoints < hitPoints
        return otherActor.hitPoints < hitPoints
    }

    override fun check(actor: Actor, otherActor: Actor): Boolean {
        return true
    }
}