package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class BattleSystem : Identifier,
    TurnAccumulator {
    private var attributeName: AttributeName = AttributeName.AGILITY
        set(value) {
            field = value
            logger.debug(
                "attributeName={} id={} simpleName={}", field, id, simpleName
            )
        }

    private var indexedValueActor: IndexedValue<Actor>? = null

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override var turn: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} turn={}", id, simpleName, field
            )
        }

    private fun checkActorAction(
        action: Action<Actor, Actor>, actionIndex: Int, actor: Actor, actors: Collection<Actor>, index: Int,
    ): Boolean {
        val checkValue: Boolean = action.ability != null && action.actionCondition?.check(actor, actors) ?: false
        logger.info(
            "action.ability.id={} action.ability.simpleName={} action.actionCondition.id={} action.id={} actionIndex={} actor.actions.size={} actor.id={} actors.size={} checkValue={} id={} index={} simpleName={} turn={}",
            action.ability?.id,
            action.ability?.simpleName,
            action.actionCondition?.id,
            action.id,
            actionIndex,
            actor.actions.size,
            actor.id,
            actors.size,
            checkValue,
            id,
            index,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorActions(actor: Actor, index: Int): Boolean {
        val checkValue: Boolean = actor.actions.isNotEmpty()
        logger.info(
            "actor.actions.size={} actor.id={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.actions.size,
            actor.id,
            checkValue,
            id,
            index,
            simpleName,
            turn,
        )
        return checkValue
    }

    private fun checkActorHitPoints(actor: Actor, index: Int): Boolean {
        val checkValue: Boolean = actor.hitPoints > 0
        logger.info(
            "actor.hitPoints={} actor.hitPointsMaximum={} actor.id={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.hitPoints,
            actor.hitPointsMaximum,
            actor.id,
            checkValue,
            id,
            index,
            simpleName,
            turn
        )
        return checkValue
    }

    fun checkActorRemoval(actor: Actor, index: Int): Boolean {
        val checkValue: Boolean =
            !checkActorActions(actor, index) || !checkActorHitPoints(actor, index) || checkActorRunning(actor, index)
        logger.trace(
            "actor.allegiance={} actor.id={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.allegiance,
            actor.id,
            checkValue,
            id,
            index,
            simpleName, turn,
        )
        return checkValue
    }

    private fun checkActorRunning(actor: Actor, index: Int): Boolean {
        val checkValue: Boolean = actor.isRunning
        logger.info(
            "actor.id={} actor.isRunning={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.id,
            actor.isRunning,
            checkValue,
            id,
            index,
            simpleName,
            turn,
        )
        return checkValue
    }

    private fun checkActorSleep(actor: Actor, index: Int): Boolean {
        val checkValue: Boolean = !checkActorTurnsSleep(actor, index) ||
                checkActorTurnsSleepMinimum(actor, index) && checkActorSleepResolution(actor, index)
        logger.info(
            "actor.id={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.id,
            checkValue,
            id,
            index,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorSleepResolution(actor: Actor, index: Int): Boolean {
        val sleepResolution: Int = actor.sleepResolution
        val checkValue: Boolean = sleepResolution == actor.sleepResolutionMaximum
        logger.info(
            "actor.id={} actor.sleepResolution={} actor.sleepResolutionMaximum={} actor.sleepResolutionMinimum={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.id,
            sleepResolution,
            actor.sleepResolutionMaximum,
            actor.sleepResolutionMinimum,
            checkValue,
            id,
            index,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorStopSpell(actor: Actor, index: Int): Boolean {
        val checkValue: Boolean = !checkActorTurnsStopSpell(actor, index) ||
                checkActorTurnsStopSpellMinimum(actor, index) && checkActorStopSpellResolution(actor, index)
        logger.info(
            "actor.id={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.id,
            checkValue,
            id,
            index,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorStopSpellResolution(actor: Actor, index: Int): Boolean {
        val stopSpellResolution: Int = actor.stopSpellResolution
        val checkValue: Boolean = stopSpellResolution == actor.stopSpellResolutionMaximum
        logger.info(
            "actor.id={} actor.stopSpellResolution={} actor.stopSpellResolutionMaximum={} actor.stopSpellResolutionMinimum={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.id,
            stopSpellResolution,
            actor.stopSpellResolutionMaximum,
            actor.stopSpellResolutionMinimum,
            checkValue,
            id,
            index,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorTurnsSleep(actor: Actor, index: Int): Boolean {
        val checkValue: Boolean = actor.turnsSleep > 0
        logger.info(
            "actor.id={} actor.turnsSleep={} actor.turnsSleepMaximum={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.id,
            actor.turnsSleep,
            actor.turnsSleepMaximum,
            checkValue,
            id,
            index,
            simpleName, turn,
        )
        return checkValue
    }

    private fun checkActorTurnsSleepMinimum(actor: Actor, index: Int): Boolean {
        val checkValue: Boolean = actor.turnsSleep > actor.turnsSleepMinimum
        logger.info(
            "actor.id={} actor.turnsSleep={} actor.turnsSleepMinimum={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.id,
            actor.turnsSleep,
            actor.turnsSleepMinimum,
            checkValue,
            id,
            index,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorTurnsStopSpell(actor: Actor, index: Int): Boolean {
        val checkValue: Boolean = actor.turnsStopSpell > 0
        logger.info(
            "actor.id={} actor.turnsStopSpell={} actor.turnsStopSpellMaximum={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.id,
            actor.turnsStopSpell,
            actor.turnsStopSpellMaximum,
            checkValue,
            id,
            index,
            simpleName,
            turn,
        )
        return checkValue
    }

    private fun checkActorTurnsStopSpellMinimum(actor: Actor, index: Int): Boolean {
        val checkValue: Boolean = (actor.turnsStopSpell > 0) && (actor.turnsStopSpell > actor.turnsStopSpellMinimum)
        logger.info(
            "actor.id={} actor.turnsStopSpell={} actor.turnsStopSpellMinimum={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.id,
            actor.turnsStopSpell,
            actor.turnsStopSpellMinimum,
            checkValue,
            id,
            index,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorValid(actor: Actor, index: Int): Boolean {
        val checkValue: Boolean = checkActorActions(actor, index) && checkActorHitPoints(actor, index)
        logger.info(
            "actor.id={} checkValue={} id={} index={} simpleName={} turn={}",
            actor.id,
            checkValue,
            id,
            index,
            simpleName,
            turn
        )
        return checkValue
    }

    fun getAction(actor: Actor, actors: Collection<Actor>, index: Int): IndexedValue<Action<Actor, Actor>>? {
        val indexedValue: IndexedValue<Action<Actor, Actor>>? =
            actor.actions.withIndex().firstOrNull { indexedValue: IndexedValue<Action<Actor, Actor>> ->
                checkActorAction(indexedValue.value, indexedValue.index, actor, actors, index)
            }
        logger.info(
            "action.id={} actionIndex={} actor.id={} actors.size={} id={} index={} simpleName={} turn={}",
            indexedValue?.value?.id,
            indexedValue?.index,
            actor.id,
            actors.size,
            id,
            index,
            simpleName,
            turn
        )
        return indexedValue
    }

    fun getActionTargets(
        action: Action<Actor, Actor>, actionIndex: Int, actor: Actor, actors: Collection<Actor>, index: Int,
    ): Collection<Actor> {
        val targetedActors = action.actionTarget?.target(actor, actors) ?: emptyList()
        logger.info(
            "action.actionTarget.id={} action.id={} actionIndex={} actor.id={} actors.size={} id={} index={} simpleName={} targetedActors.size={} turn={}",
            action.actionTarget?.id,
            action.id,
            actionIndex,
            actor.id,
            actors.size,
            id,
            index,
            simpleName,
            targetedActors.size,
            turn
        )
        return targetedActors
    }

    fun getActionTargetOrder(
        action: Action<Actor, Actor>, actionIndex: Int, actor: Actor, actors: Collection<Actor>, index: Int,
    ): Collection<Actor> {
        logger.info(
            "action.attributeSort.id={} action.attributeSort.sortType={} action.id={} actionIndex={} actor.id={} actors.size={} id={} index={} simpleName={} turn={}",
            action.attributeSort?.id,
            action.attributeSort?.sortType,
            action.id,
            actionIndex,
            actor.id,
            actors.size,
            id,
            index,
            simpleName,
            turn
        )
        return action.attributeSort?.sort(actors) ?: actors
    }

    fun getActor(actorIterator: Iterator<IndexedValue<Actor>>): IndexedValue<Actor>? {
        if (actorIterator.hasNext()) {
            indexedValueActor = actorIterator.next()
            indexedValueActor?.let {
                tickActor(it.value, it.index)
            }
        }
        return indexedValueActor
    }

    private fun getActorOrder(actor: Actor, index: Int): Int {
        val attributeValue: Int = actor.getAttribute(attributeName)
        logger.info(
            "actor.id={} attributeName={} attributeValue={} id={} index={} simpleName={} turn={}",
            actor.id,
            attributeName,
            attributeValue,
            id,
            index,
            simpleName,
            turn
        )
        return attributeValue
    }

    fun getActors(actors: Collection<Actor>): Iterator<IndexedValue<Actor>> {
        return actors.filterIndexed { index: Int, actor: Actor ->
            checkActorValid(actor, index)
        }.withIndex().sortedByDescending { indexedValue: IndexedValue<Actor> ->
            getActorOrder(indexedValue.value, indexedValue.index)
        }.iterator()
    }

    private fun tickActor(actor: Actor, index: Int) {
        tickActorIndex(actor, index)
        tickActorTurn(actor, index)
        tickActorSleep(actor, index)
        tickActorStopSpell(actor, index)
    }

    private fun tickActorIndex(actor: Actor, index: Int) {
        actor.index = index
        logger.info(
            "actor.id={} actor.index={} id={} index={} simpleName={} turn={}",
            actor.id,
            actor.index,
            id,
            index,
            simpleName,
            turn,
        )
    }

    private fun tickActorSleep(actor: Actor, index: Int) {
        if (!checkActorSleep(actor, index)) {
            actor.turnsSleep += 1
        }
        logger.info(
            "actor.id={} actor.turnsSleep={} id={} index={} simpleName={} turn={}",
            actor.id,
            actor.turnsSleep,
            id,
            index,
            simpleName,
            turn
        )
    }

    private fun tickActorStopSpell(actor: Actor, index: Int) {
        if (!checkActorStopSpell(actor, index)) {
            actor.turnsStopSpell += 1
        }
        logger.info(
            "actor.id={} actor.turnsStopSpell={} id={} index={} simpleName={} turn={}",
            actor.id,
            actor.turnsStopSpell,
            id,
            index,
            simpleName,
            turn
        )
    }

    private fun tickActorTurn(actor: Actor, index: Int) {
        actor.turn = turn++
        logger.info(
            "actor.id={} actor.turn={} id={} index={} simpleName={} turn={}",
            actor.id,
            actor.turn,
            id,
            index,
            simpleName,
            turn,
        )
    }
}