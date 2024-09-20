package dqbb

data class ReviewMagicPotion(
    override val abilityId: Int,
    override val abilitySimpleName: String,
    override val invokerId: Int,
    override val invokerIsValid: Boolean,
    override val invokerItemCount: Int,
    val invokerMagicPotion: Int?,
    override val invokerName: String?,
    override val invokerSimpleName: String,
    override val receiverId: Int,
    override val receiverIsValid: Boolean,
    val receiverMagicPoints: Int,
    val receiverMagicPointsMaximum: Int,
    override val receiverName: String?,
    override val receiverSimpleName: String,
) : ReviewableItem {
    override val timeMilliseconds: Long = System.currentTimeMillis()

    override fun isValid(): Boolean {
        return invokerMagicPotion != null
    }

    override fun toString(): String {
        return "abilityId=$abilityId abilitySimpleName=$abilitySimpleName id=$id invokerId=$invokerId invokerIsValid=$invokerIsValid invokerItemCount=$invokerItemCount invokerMagicPotion=$invokerMagicPotion invokerName=$invokerName invokerSimpleName=$invokerSimpleName receiverId=$receiverId receiverIsValid=$receiverIsValid receiverMagicPoints=$receiverMagicPoints receiverMagicPointsMaximum=$receiverMagicPointsMaximum receiverName=$receiverName receiverSimpleName=$receiverSimpleName simpleName=$simpleName timeMilliseconds=$timeMilliseconds"
    }
}