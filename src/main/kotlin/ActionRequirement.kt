package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class ActionRequirement<A : ActionInvoker, B : ActionReceiver> : Identifier,
        Nameable {
    var attributeCriteria: Collection<AttributeCriterion<B>> = emptyList()
        set(value) {
            field = value.filter { attributeCriterion: AttributeCriterion<B> ->
                attributeCriterion.attributeComparisons.isNotEmpty()
            }.sortedByDescending { attributeCriterion: AttributeCriterion<B> ->
                attributeCriterion.priorityType.ordinal
            }
            logger.debug(
                "attributeCriteria.size={} id={} simpleName={}", field.size, id, simpleName
            )
        }

    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    final override var name: String? = null
        set(value) {
            field = value
            logger.debug(
                "id={} name={} simpleName={}", id, name, simpleName
            )
        }

    var selectionType: SelectionType = SelectionType.ANY
        set(value) {
            field = value
            logger.debug(
                "id={} selectionType={} simpleName={}", id, field, simpleName
            )
        }

    private fun checkAllegiance(actionInvoker: A, actionReceiver: B, actionReceiverIndex: Int): Boolean {
        val checkValue: Boolean = getActionInvokerAllegiance(actionInvoker) == getActionReceiverAllegiance(
            actionReceiver, actionReceiverIndex
        )
        logger.trace(
            "actionInvoker.id={} actionInvoker.simpleName={} actionReceiver.id={} actionReceiver.index={} actionReceiver.simpleName={} checkValue={} id={} operation=checkAllegiance simpleName={}",
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceiver.id,
            actionReceiverIndex,
            actionReceiver.simpleName,
            checkValue,
            id,
            simpleName
        )
        return checkValue
    }

    private fun checkHashCode(actionInvoker: A, actionReceiver: B, actionReceiverIndex: Int): Boolean {
        val checkValue: Boolean =
            getActionInvokerHashCode(actionInvoker) == getActionReceiverHashCode(actionReceiver, actionReceiverIndex)
        logger.trace(
            "actionInvoker.id={} actionInvoker.simpleName={} actionReceiver.id={} actionReceiver.index={} actionReceiver.simpleName={} checkValue={} id={} operation=checkHashCode simpleName={}",
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceiver.id,
            actionReceiverIndex,
            actionReceiver.simpleName,
            checkValue,
            id,
            simpleName
        )
        return checkValue
    }

    protected fun checkSelectionType(actionInvoker: A, actionReceiver: B, actionReceiverIndex: Int): Boolean {
        val checkValue: Boolean = when (selectionType) {
            SelectionType.ANY -> true

            SelectionType.ALLY -> checkSelectionTypeAlly(
                actionInvoker, actionReceiver, actionReceiverIndex
            )

            SelectionType.ENEMY -> checkSelectionTypeEnemy(
                actionInvoker, actionReceiver, actionReceiverIndex
            )

            SelectionType.SELF -> checkSelectionTypeSelf(
                actionInvoker, actionReceiver, actionReceiverIndex
            )
        }
        logger.info(
            "actionInvoker.id={} actionInvoker.simpleName={} actionReceiver.id={} actionReceiver.index={} actionReceiver.simpleName={} checkValue={} id={} selectionType={} simpleName={}",
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceiver.id,
            actionReceiverIndex,
            actionReceiver.simpleName,
            checkValue,
            id,
            selectionType,
            simpleName
        )
        return checkValue
    }

    private fun checkSelectionTypeAlly(actionInvoker: A, actionReceiver: B, actionReceiverIndex: Int): Boolean {
        val checkValue: Boolean = !checkHashCode(actionInvoker, actionReceiver, actionReceiverIndex) && checkAllegiance(
            actionInvoker, actionReceiver, actionReceiverIndex
        )
        logger.trace(
            "actionInvoker.id={} actionInvoker.simpleName={} actionReceiver.id={} actionReceiver.index={} actionReceiver.simpleName={} checkValue={} id={} operation=checkSelectionTypeAlly simpleName={}",
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceiver.id,
            actionReceiverIndex,
            actionReceiver.simpleName,
            checkValue,
            id,
            simpleName
        )
        return checkValue
    }

    private fun checkSelectionTypeEnemy(actionInvoker: A, actionReceiver: B, actionReceiverIndex: Int): Boolean {
        val checkValue: Boolean =
            !checkHashCode(actionInvoker, actionReceiver, actionReceiverIndex) && !checkAllegiance(
                actionInvoker, actionReceiver, actionReceiverIndex
            )
        logger.trace(
            "actionInvoker.id={} actionInvoker.simpleName={} actionReceiver.id={} actionReceiver.index={} actionReceiver.simpleName={} checkValue={} id={} operation=checkSelectionTypeEnemy simpleName={}",
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceiver.id,
            actionReceiverIndex,
            actionReceiver.simpleName,
            checkValue,
            id,
            simpleName
        )
        return checkValue
    }

    private fun checkSelectionTypeSelf(actionInvoker: A, actionReceiver: B, actionReceiverIndex: Int): Boolean {
        val checkValue: Boolean = checkHashCode(actionInvoker, actionReceiver, actionReceiverIndex)
        logger.trace(
            "actionInvoker.id={} actionInvoker.simpleName={} actionReceiver.id={} actionReceiver.index={} actionReceiver.simpleName={} checkValue={} id={} operation=checkSelectionTypeSelf simpleName={}",
            actionInvoker.id,
            actionInvoker.simpleName,
            actionReceiver.id,
            actionReceiverIndex,
            actionReceiver.simpleName,
            checkValue,
            id,
            simpleName
        )
        return checkValue
    }

    protected fun checkAttributeCriteria(actionReceiver: B, actionReceiverIndex: Int): Boolean {
        logger.info(
            "actionReceiver.id={} actionReceiver.index={} actionReceiver.simpleName={} id={} simpleName={}",
            actionReceiver.id,
            actionReceiverIndex,
            actionReceiver.simpleName,
            id,
            simpleName
        )
        return attributeCriteria.withIndex().all { (index: Int, attributeCriterion: AttributeCriterion<B>) ->
            checkAttributeCriterion(attributeCriterion, index, actionReceiver, actionReceiverIndex)
        }
    }

    private fun checkAttributeCriterion(
        attributeCriterion: AttributeCriterion<B>,
        attributeCriterionIndex: Int,
        actionReceiver: B,
        actionReceiverIndex: Int,
    ): Boolean {
        logger.info(
            "actionReceiver.id={} actionReceiver.index={} actionReceiver.simpleName={} attributeCriterion.id={} attributeCriterion.index={} id={} simpleName={}",
            actionReceiver.id,
            actionReceiverIndex,
            actionReceiver.simpleName,
            attributeCriterion.id,
            attributeCriterionIndex,
            id,
            simpleName
        )
        return attributeCriterion.check(actionReceiver)
    }

    private fun getActionInvokerAllegiance(actionInvoker: A): Int {
        logger.info(
            "actionInvoker.allegiance={} actionInvoker.id={} actionInvoker.simpleName={} id={} simpleName={}",
            actionInvoker.allegiance,
            actionInvoker.id,
            actionInvoker.simpleName,
            id,
            simpleName
        )
        return actionInvoker.allegiance
    }

    private fun getActionInvokerHashCode(actionInvoker: A): Int {
        val hashCode: Int = actionInvoker.id
        logger.info(
            "actionInvoker.hashCode={} actionInvoker.simpleName={} id={}", id, hashCode, actionInvoker.simpleName
        )
        return hashCode
    }

    private fun getActionReceiverAllegiance(actionReceiver: B, actionReceiverIndex: Int): Int {
        logger.info(
            "actionReceiver.allegiance={} actionReceiver.id={} actionReceiver.index={} actionReceiver.simpleName={} id={} simpleName={}",
            actionReceiver.allegiance,
            actionReceiver.id,
            actionReceiverIndex,
            actionReceiver.simpleName,
            id,
            simpleName
        )
        return actionReceiver.allegiance
    }

    private fun getActionReceiverHashCode(actionReceiver: B, actionReceiverIndex: Int): Int {
        val hashCode: Int = actionReceiver.id
        logger.info(
            "actionReceiver.hashCode={} actionReceiver.index={} actionReceiver.simpleName={} id={} simpleName={}",
            hashCode,
            actionReceiverIndex,
            actionReceiver.simpleName,
            id,
            simpleName
        )
        return hashCode
    }

    override fun toString(): String {
        return "attributeCriteria.size=${attributeCriteria} id=$id name=$name selectionType=$selectionType simpleName=$simpleName"
    }
}