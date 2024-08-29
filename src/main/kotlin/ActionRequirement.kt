package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

open class ActionRequirement<A, B> : Identifier,
    Nameable where A : AbilityInvoker, A : AllegianceKeeper, A : AttributeProvider, B : AbilityReceiver, B : AllegianceKeeper, B : AttributeProvider {
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

    private fun checkAllegiance(invoker: A, receiver: B): Boolean {
        logger.trace(
            "id={} invoker.id={} invoker.simpleName={} operation=checkAllegiance receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return getInvokerAllegiance(invoker) == getReceiverAllegiance(receiver)
    }

    private fun checkHashCode(invoker: A, receiver: B): Boolean {
        logger.trace(
            "id={} invoker.id={} invoker.simpleName={} operation=checkHashCode receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return getInvokerHashCode(invoker) == getReceiverHashCode(receiver)
    }

    protected fun checkSelectionType(invoker: A, index: Int, receiver: B): Boolean {
        val checkValue = when (selectionType) {
            SelectionType.ANY ->
                true

            SelectionType.ALLY ->
                checkSelectionTypeAlly(invoker, receiver)

            SelectionType.ENEMY ->
                checkSelectionTypeEnemy(invoker, receiver)

            SelectionType.SELF ->
                checkSelectionTypeSelf(invoker, receiver)
        }
        logger.info(
            "checkValue={} id={} index={} invoker.id={} invoker.simpleName={} receiver.id={} receiver.simpleName={} selectionType={} simpleName={}",
            checkValue,
            id,
            index,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            selectionType,
            simpleName
        )
        return checkValue
    }

    private fun checkSelectionTypeAlly(invoker: A, receiver: B): Boolean {
        logger.trace(
            "id={} invoker.id={} invoker.simpleName={} operation=checkSelectionTypeAlly receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return !checkHashCode(invoker, receiver) && checkAllegiance(invoker, receiver)
    }

    private fun checkSelectionTypeEnemy(invoker: A, receiver: B): Boolean {
        logger.trace(
            "id={} invoker.id={} invoker.simpleName={} operation=checkSelectionTypeEnemy receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return !checkHashCode(invoker, receiver) && !checkAllegiance(invoker, receiver)
    }

    private fun checkSelectionTypeSelf(invoker: A, receiver: B): Boolean {
        logger.trace(
            "id={} invoker.id={} invoker.simpleName={} operation=checkSelectionTypeSelf receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            invoker.id,
            invoker.simpleName,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return checkHashCode(invoker, receiver)
    }

    protected fun checkAttributeCriteria(index: Int, receiver: B): Boolean {
        logger.info(
            "id={} index={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            index,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return attributeCriteria.withIndex().all { (index: Int, attributeCriterion: AttributeCriterion<B>) ->
            checkAttributeCriterion(attributeCriterion, index, receiver)
        }
    }

    private fun checkAttributeCriterion(attributeCriterion: AttributeCriterion<B>, index: Int, receiver: B): Boolean {
        logger.info(
            "attributeCriterion.id={} id={} index={} receiver.id={} receiver.simpleName={} simpleName={}",
            attributeCriterion.id,
            id,
            index,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return attributeCriterion.check(receiver)
    }

    private fun getInvokerAllegiance(invoker: A): Int {
        logger.info(
            "id={} invoker.allegiance={} invoker.id={} invoker.simpleName={} simpleName={}",
            id,
            invoker.allegiance,
            invoker.id,
            invoker.simpleName,
            simpleName
        )
        return invoker.allegiance
    }

    private fun getInvokerHashCode(invoker: A): Int {
        val hashCode = invoker.id
        logger.info(
            "id={} invoker.hashCode={} invoker.simpleName={}", id, hashCode, invoker.simpleName
        )
        return hashCode
    }

    private fun getReceiverAllegiance(receiver: B): Int {
        logger.info(
            "id={} receiver.allegiance={} receiver.id={} receiver.simpleName={} simpleName={}",
            id,
            receiver.allegiance,
            receiver.id,
            receiver.simpleName,
            simpleName
        )
        return receiver.allegiance
    }

    private fun getReceiverHashCode(receiver: B): Int {
        val hashCode = receiver.id
        logger.info(
            "id={} receiver.hashCode={} receiver.simpleName={} simpleName={}",
            id,
            hashCode,
            receiver.simpleName,
            simpleName
        )
        return hashCode
    }
}