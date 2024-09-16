package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class Feedback {
    var abilityIdentifier: Int? = null
    var abilitySimpleName: String? = null
    protected val logger: Logger = LogManager.getLogger()
    var invokerIdentifier: Int? = null
    var invokerName: String? = null
    var invokerSimpleName: String? = null
}

class FeedbackAttack() : Feedback() {
    var invokerAttackPoints: Int? = null
    var receiverHitPoints: Int? = null
}

class FeedbackHerb() : FeedbackItem() {
    var itemCount: Int? = null
    override val itemName: ItemName = ItemName.HERB
}

abstract class FeedbackItem : Feedback() {
    abstract val itemName: ItemName
}

