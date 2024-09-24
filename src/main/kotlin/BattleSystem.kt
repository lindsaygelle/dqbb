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

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override var turn: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} simpleName={} turn={}", id, simpleName, field
            )
        }

    private fun checkAction(
        action: Action<Actor, Actor>, actionIndex: Int, actor: Actor, actorIndex: Int, actors: Collection<Actor>,
    ): Boolean {
        val actionId: Int = actor.id
        val checkValue: Boolean = checkActionAbility(action.ability, actionId, actionIndex) && checkActionCondition(
            action.actionCondition, actionId, actionIndex, actor, actorIndex, actors
        ) && checkActionTarget(action.actionTarget, actionId, actionIndex)
        logger.info(
            "action.id={} action.index={} actor.actions.size={} actor.id={} actor.index={} actors.size={} checkValue={} id={} simpleName={} turn={}",
            action.id,
            actionIndex,
            actor.actions.size,
            actor.id,
            actorIndex,
            actors.size,
            checkValue,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActionAbility(ability: Ability<Actor, Actor>?, actionId: Int, actionIndex: Int): Boolean {
        val checkValue: Boolean = ability != null
        logger.info(
            "checkValue={} ability.id={} ability.simpleName={} action.id={} action.index={} id={} simpleName={} turn={}",
            checkValue,
            ability?.id,
            ability?.simpleName,
            actionId,
            actionIndex,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActionCondition(
        actionCondition: ActionCondition<Actor, Actor>?,
        actionId: Int,
        actionIndex: Int,
        actor: Actor,
        actorIndex: Int,
        actors: Collection<Actor>,
    ): Boolean {
        val checkValue: Boolean = actionCondition?.check(actor, actors) ?: false
        logger.info(
            "actionCondition.id={} actionCondition.actionChecks.size={} actionCondition.simpleName={} action.id={} action.index={} actor.id={} actor.index={} actors.size={} id={} simpleName={} turn={}",
            actionCondition?.id,
            actionCondition?.actionChecks?.size,
            actionCondition?.simpleName,
            actionId,
            actionIndex,
            actor.id,
            actorIndex,
            actors.size,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActionTarget(
        actionTarget: ActionTarget<Actor, Actor>?,
        actionId: Int,
        actionIndex: Int,
    ): Boolean {
        val checkValue: Boolean = actionTarget != null
        logger.info(
            "actionTarget.attributeCriteria.size={} actionTarget.id={} action.id={} action.index={} id={} simpleName={} turn={}",
            actionTarget?.id,
            actionTarget?.attributeCriteria?.size,
            actionId,
            actionIndex,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorActions(actor: Actor, actorIndex: Int): Boolean {
        val checkValue: Boolean = actor.actions.isNotEmpty()
        logger.info(
            "actor.actions.size={} actor.id={} actor.index={} checkValue={} id={} simpleName={} turn={}",
            actor.actions.size,
            actor.id,
            actorIndex,
            checkValue,
            id,
            simpleName,
            turn,
        )
        return checkValue
    }

    private fun checkActorHitPoints(actor: Actor, actorIndex: Int): Boolean {
        val checkValue: Boolean = actor.hitPoints > 0
        logger.info(
            "actor.hitPoints={} actor.hitPointsMaximum={} actor.id={} actor.index={} checkValue={} id={} simpleName={} turn={}",
            actor.hitPoints,
            actor.hitPointsMaximum,
            actor.id,
            actorIndex,
            checkValue,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    fun checkActorRemoval(actor: Actor, actorIndex: Int): Boolean {
        val checkValue: Boolean = !checkActorActions(actor, actorIndex) || !checkActorHitPoints(
            actor, actorIndex
        ) || checkActorRunning(
            actor, actorIndex
        )
        logger.trace(
            "actor.allegiance={} actor.id={} actor.index={} checkValue={} id={} simpleName={} turn={}",
            actor.allegiance,
            actor.id,
            actorIndex,
            checkValue,
            id,
            simpleName,
            turn,
        )
        return checkValue
    }

    private fun checkActorRunning(actor: Actor, actorIndex: Int): Boolean {
        val checkValue: Boolean = actor.isRunning
        logger.info(
            "actor.id={} actor.index={} actor.isRunning={} checkValue={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            actor.isRunning,
            checkValue,
            id,
            simpleName,
            turn,
        )
        return checkValue
    }

    private fun checkActorSleep(actor: Actor, actorIndex: Int): Boolean {
        val checkValue: Boolean = !checkActorTurnsSleep(actor, actorIndex) || checkActorTurnsSleepMinimum(
            actor, actorIndex
        ) && checkActorSleepResolution(actor, actorIndex)
        logger.info(
            "actor.id={} actor.index={} checkValue={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            checkValue,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorSleepResolution(actor: Actor, actorIndex: Int): Boolean {
        val sleepResolution: Int = actor.sleepResolution
        val checkValue: Boolean = sleepResolution == actor.sleepResolutionMaximum
        logger.info(
            "actor.id={} actor.index={} actor.sleepResolution={} actor.sleepResolutionMaximum={} actor.sleepResolutionMinimum={} checkValue={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            sleepResolution,
            actor.sleepResolutionMaximum,
            actor.sleepResolutionMinimum,
            checkValue,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorStopSpell(actor: Actor, actorIndex: Int): Boolean {
        val checkValue: Boolean = !checkActorTurnsStopSpell(actor, actorIndex) || checkActorTurnsStopSpellMinimum(
            actor, actorIndex
        ) && checkActorStopSpellResolution(actor, actorIndex)
        logger.info(
            "actor.id={} actor.index={} checkValue={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            checkValue,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorStopSpellResolution(actor: Actor, actorIndex: Int): Boolean {
        val stopSpellResolution: Int = actor.stopSpellResolution
        val checkValue: Boolean = stopSpellResolution == actor.stopSpellResolutionMaximum
        logger.info(
            "actor.id={} actor.index={} actor.stopSpellResolution={} actor.stopSpellResolutionMaximum={} actor.stopSpellResolutionMinimum={} checkValue={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            stopSpellResolution,
            actor.stopSpellResolutionMaximum,
            actor.stopSpellResolutionMinimum,
            checkValue,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorTurnsSleep(actor: Actor, actorIndex: Int): Boolean {
        val checkValue: Boolean = actor.turnsSleep > 0
        logger.info(
            "actor.id={} actor.index={} actor.turnsSleep={} actor.turnsSleepMaximum={} checkValue={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            actor.turnsSleep,
            actor.turnsSleepMaximum,
            checkValue,
            id,
            simpleName,
            turn,
        )
        return checkValue
    }

    private fun checkActorTurnsSleepMinimum(actor: Actor, actorIndex: Int): Boolean {
        val checkValue: Boolean = actor.turnsSleep > actor.turnsSleepMinimum
        logger.info(
            "actor.id={} actor.index={} actor.turnsSleep={} actor.turnsSleepMinimum={} checkValue={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            actor.turnsSleep,
            actor.turnsSleepMinimum,
            checkValue,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorTurnsStopSpell(actor: Actor, actorIndex: Int): Boolean {
        val checkValue: Boolean = actor.turnsStopSpell > 0
        logger.info(
            "actor.id={} actor.index={} actor.turnsStopSpell={} actor.turnsStopSpellMaximum={} checkValue={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            actor.turnsStopSpell,
            actor.turnsStopSpellMaximum,
            checkValue,
            id,
            simpleName,
            turn,
        )
        return checkValue
    }

    private fun checkActorTurnsStopSpellMinimum(actor: Actor, actorIndex: Int): Boolean {
        val checkValue: Boolean = (actor.turnsStopSpell > 0) && (actor.turnsStopSpell > actor.turnsStopSpellMinimum)
        logger.info(
            "actor.id={} actor.index={} actor.turnsStopSpell={} actor.turnsStopSpellMinimum={} checkValue={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            actor.turnsStopSpell,
            actor.turnsStopSpellMinimum,
            checkValue,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    private fun checkActorValid(actor: Actor, actorIndex: Int): Boolean {
        val checkValue: Boolean = checkActorActions(actor, actorIndex) && checkActorHitPoints(actor, actorIndex)
        logger.info(
            "actor.id={} actor.index={} checkValue={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            checkValue,
            id,
            simpleName,
            turn
        )
        return checkValue
    }

    fun getActionTarget(actors: Collection<Actor>): Actor? {
        val actor: Actor? = actors.firstOrNull()
        logger.info(
            "actor.id={} actors.size={} id={} simpleName={} turn={}", actor?.id, actors.size, id, simpleName, turn
        )
        return actor
    }

    fun getActionTargets(
        actors: Collection<Actor>,
        indexedValueAction: IndexedValue<Action<Actor, Actor>>,
        indexedValueActor: IndexedValue<Actor>,
    ): Collection<Actor> {
        val targetedActors: Collection<Actor> = indexedValueAction.value.actionTarget?.target(
            indexedValueActor.value, actors
        ) ?: emptyList()
        logger.info(
            "action.actionTarget.id={} action.id={} action.index={} actor.id={} actor.index={} actors.size={} id={} simpleName={} targetedActors.size={} turn={}",
            indexedValueAction.value.actionTarget?.id,
            indexedValueAction.value.id,
            indexedValueAction.index,
            indexedValueActor.value.id,
            indexedValueActor.index,
            actors.size,
            id,
            simpleName,
            targetedActors.size,
            turn
        )
        return targetedActors
    }

    fun getActionTargetOrder(
        actors: Collection<Actor>,
        indexedValueAction: IndexedValue<Action<Actor, Actor>>,
    ): Collection<Actor> {
        logger.info(
            "action.attributeSort.attributeName={} action.attributeSort.id={} action.attributeSort.sortType={} action.id={} action.index={} actors.size={} id={} simpleName={} turn={}",
            indexedValueAction.value.attributeSort?.attributeName,
            indexedValueAction.value.attributeSort?.id,
            indexedValueAction.value.attributeSort?.sortType,
            indexedValueAction.value.id,
            indexedValueAction.index,
            actors.size,
            id,
            simpleName,
            turn
        )
        return indexedValueAction.value.attributeSort?.sort(actors) ?: actors
    }

    private fun getActorOrder(actor: Actor, actorIndex: Int): Int {
        val attributeValue: Int = actor.getAttribute(attributeName)
        logger.info(
            "actor.id={} actor.index={} attributeName={} attributeValue={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            attributeName,
            attributeValue,
            id,
            simpleName,
            turn
        )
        return attributeValue
    }

    fun getActorIterator(actors: Collection<Actor>): Iterator<IndexedValue<Actor>> {
        logger.info(
            "actors.size={} id={} simpleName={} turn={}", actors.size, id, simpleName, turn
        )
        return actors.filterIndexed { index: Int, actor: Actor ->
            checkActorValid(actor, index)
        }.withIndex().sortedByDescending { indexedValue: IndexedValue<Actor> ->
            getActorOrder(indexedValue.value, indexedValue.index)
        }.iterator()
    }

    fun getIndexedAction(
        actors: Collection<Actor>,
        indexedValueActor: IndexedValue<Actor>,
    ): IndexedValue<Action<Actor, Actor>>? {
        val indexedValue: IndexedValue<Action<Actor, Actor>>? = indexedValueActor.value.actions.withIndex()
            .firstOrNull { indexedValue: IndexedValue<Action<Actor, Actor>> ->
                checkAction(
                    indexedValue.value, indexedValue.index, indexedValueActor.value, indexedValueActor.index, actors
                )
            }
        logger.info(
            "action.id={} action.index={} actor.id={} actor.index={} actors.size={} id={} simpleName={} turn={}",
            indexedValue?.value?.id,
            indexedValue?.index,
            indexedValueActor.value.id,
            indexedValueActor.value,
            actors.size,
            id,
            simpleName,
            turn
        )
        return indexedValue
    }

    fun getIndexedActor(actorIterator: Iterator<IndexedValue<Actor>>): IndexedValue<Actor>? {
        val hasNext: Boolean = actorIterator.hasNext()
        var indexedValueActor: IndexedValue<Actor>? = null
        if (hasNext) {
            indexedValueActor = actorIterator.next()
            tickActor(indexedValueActor.value, indexedValueActor.index)
        }
        logger.info(
            "actorIterator.hasNext={} actor.id={} actor.index={} id={} simpleName={}",
            hasNext,
            indexedValueActor?.value?.id,
            indexedValueActor?.index,
            id,
            simpleName
        )
        return indexedValueActor
    }

    fun getReviewable(
        indexedValueAction: IndexedValue<Action<Actor, Actor>>,
        indexedValueActor: IndexedValue<Actor>,
        targetActor: Actor,
    ): Reviewable? {
        val reviewable: Reviewable? = indexedValueAction.value.ability?.use(indexedValueActor.value, targetActor)
        logger.info(
            "action.id={} action.index={} actor.id={} actor.index={} id={} simpleName={} targetActor.id={} turn={}",
            indexedValueAction.value.id,
            indexedValueAction.index,
            indexedValueActor.value.id,
            indexedValueActor.index,
            id,
            simpleName,
            targetActor.id,
            turn
        )
        return reviewable
    }

    private fun tickActor(actor: Actor, actorIndex: Int) {
        logger.info(
            "actor.id={} actor.index={} id={} simpleName={} turn={}", actor.id, actorIndex, id, simpleName, turn
        )
        tickActorTurn(actor, actorIndex)
        tickActorSleep(actor, actorIndex)
        tickActorStopSpell(actor, actorIndex)
    }

    private fun tickActorSleep(actor: Actor, actorIndex: Int) {
        if (!checkActorSleep(actor, actorIndex)) {
            actor.turnsSleep += 1
        }
        logger.info(
            "actor.id={} actor.index={} actor.turnsSleep={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            actor.turnsSleep,
            id,
            simpleName,
            turn
        )
    }

    private fun tickActorStopSpell(actor: Actor, actorIndex: Int) {
        if (!checkActorStopSpell(actor, actorIndex)) {
            actor.turnsStopSpell += 1
        }
        logger.info(
            "actor.id={} actor.index={} actor.turnsStopSpell={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            actor.turnsStopSpell,
            id,
            simpleName,
            turn
        )
    }

    private fun tickActorTurn(actor: Actor, actorIndex: Int) {
        actor.turn = turn++
        logger.info(
            "actor.id={} actor.index={} actor.turn={} id={} simpleName={} turn={}",
            actor.id,
            actorIndex,
            actor.turn,
            id,
            simpleName,
            turn,
        )
    }
}