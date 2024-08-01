package dqbb

class Attack(
    conditionType: ConditionType,
    orderType: OrderType? = null,
) : Ability(
    conditionType = conditionType,
    orderType = orderType,
) {

    override val name: String = "ATTACK"

    override fun apply(actor: Actor, otherActor: Actor): Boolean {
        actor.trail.add(
            Trail(
                "$actor ATTACKS $otherActor"
            )
        )
        val attackPower = actor.getAttackPower(otherActor)
        var attackScore = attackPower
        val defensePower = otherActor.getDefensePower(actor)
        val hitPoints = otherActor.hitPoints
        if (attackPower < 1) {
            attackScore = (0..1).random()
        }
        println(//logger.debug(
            "$this: " +
                    "actor.attackPower=$attackPower " +
                    "actor.attackScore=$attackScore " +
                    "actor.id=${actor.id} " +
                    "otherActor.armor.id=${otherActor.armor?.id} " +
                    "otherActor.armor.name=${otherActor.armor?.name} " +
                    "otherActor.hitPoints=$hitPoints " +
                    "otherActor.id=${otherActor.id}"
        )
        val excellentMoveChanceMaximum = actor.excellentMoveChanceMaximum
        val excellentMoveChanceMinimum = actor.excellentMoveChanceMinimum
        val excellentMoveChanceRange = (excellentMoveChanceMinimum..excellentMoveChanceMaximum)
        val excellentMoveScore = excellentMoveChanceRange.random()
        println(//logger.debug(
            "$this: " +
                    "actor.excellentMoveChanceMaximum=$excellentMoveChanceMaximum " +
                    "actor.excellentMoveChanceMinimum=$excellentMoveChanceMinimum " +
                    "actor.excellentMoveScore=$excellentMoveScore " +
                    "actor.id=${actor.id}"
        )
        if (excellentMoveScore > 31) {
            actor.trail.add(
                Trail(
                    "$actor PERFORMED AN EXCELLENT ATTACK!"
                )
            )
            val attackValue = actor.attackValue
            attackScore = attackValue
        }
        actor.trail.add(
            Trail(
                "$actor does $attackScore damage to $otherActor"
            )
        )
        otherActor.hitPoints -= attackScore
        println(//logger.debug(
            "$this: " +
                    "actor.attackScore=$attackScore " +
                    "actor.id=${actor.id} " +
                    "otherActor.hitPoints=${otherActor.hitPoints} " +
                    "otherActor.id=${otherActor.id}"
        )
        return true
    }

    override fun check(actor: Actor, otherActor: Actor): Boolean {
        return true
    }
}
