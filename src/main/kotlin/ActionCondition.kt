package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ActionCondition<A, B>() : Identifier,
    Nameable where A : AbilityInvoker, A : AllegianceKeeper, A : AttributeProvider, B : AbilityReceiver, B : AllegianceKeeper, B : AttributeProvider {
    var actionChecks: Collection<ActionCheck<A, B>> = emptyList()
        set(value) {
            field = value.filter { actionCheck: ActionCheck<A, B> ->
                actionCheck.attributeCriteria.isNotEmpty()
            }.sortedByDescending { actionCheck: ActionCheck<A, B> ->
                actionCheck.priorityType.ordinal
            }
            logger.debug(
                "attributeCriteria.size={} id={} simpleName={}", field.size, id, simpleName
            )
        }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override var name: String? = null
        set(value) {
            field = value
            logger.debug(
                "id={} name={} simpleName={}", id, name, simpleName
            )
        }

    constructor(
        actionChecks: Collection<ActionCheck<A, B>>,
        name: String? = null,
    ) : this() {
        this.actionChecks = actionChecks
        this.name = name
    }

    fun check(invoker: A, receivers: Collection<B>): Boolean {
        logger.info(
            "actionChecks.size={} id={} invoker.id={} invoker.simpleName={} receivers.size={} simpleName={}",
            actionChecks.size,
            id,
            invoker.id,
            invoker.simpleName,
            receivers.size,
            simpleName
        )
        val checkValue = actionChecks.withIndex().all { (index: Int, actionCheck: ActionCheck<A, B>) ->
            checkActionCheck(actionCheck, invoker, index, receivers)
        }
        logger.info(
            "checkValue={} id={} simpleName={}", checkValue, id, simpleName
        )
        return checkValue
    }

    private fun checkActionCheck(
        actionCheck: ActionCheck<A, B>, invoker: A, index: Int, receivers: Collection<B>,
    ): Boolean {
        logger.info(
            "actionCheck.id={} id={} index={} invoker.id={} invoker.simpleName={} receivers.size={} simpleName={}",
            actionCheck.id,
            id,
            index,
            invoker.id,
            invoker.simpleName,
            receivers.size,
            simpleName
        )
        return actionCheck.check(invoker, receivers)
    }
}