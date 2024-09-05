package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ActionCondition<A : ActionInvoker, B : ActionReceiver>() : Identifier,
    Nameable {
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

    fun check(actionInvoker: A, actionReceivers: Collection<B>): Boolean {
        logger.info(
            "actionChecks.size={} actionInvoker.id={} actionInvoker.simpleName={} actionReceivers.size={} id={} simpleName={}",
            actionChecks.size,
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceivers.size,
            id,
            simpleName
        )
        val checkValue = actionChecks.withIndex().all { (index: Int, actionCheck: ActionCheck<A, B>) ->
            checkActionCheck(actionCheck, actionInvoker, index, actionReceivers)
        }
        logger.info(
            "checkValue={} id={} simpleName={}", checkValue, id, simpleName
        )
        return checkValue
    }

    private fun checkActionCheck(
        actionCheck: ActionCheck<A, B>, actionInvoker: A, index: Int, actionReceivers: Collection<B>,
    ): Boolean {
        logger.info(
            "actionCheck.id={} actionInvoker.id={} actionInvoker.simpleName={} actionReceivers.size={} id={} index={} simpleName={}",
            actionCheck.id,
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceivers.size,
            id,
            index,
            simpleName
        )
        return actionCheck.check(actionInvoker, actionReceivers)
    }

    override fun toString(): String {
        return "actionChecks.size=${actionChecks.size} id=$id name=$name simpleName=$simpleName"
    }
}