package dqbb

data class ReviewHerb(
    override val abilityId: Int,
    override val abilitySimpleName: String,
    val invokerHerb: Int?,
    override val invokerId: Int,
    override val invokerIsValid: Boolean,
    override val invokerItemCount: Int,
    override val invokerName: String?,
    override val invokerSimpleName: String,
    val receiverHitPoints: Int,
    override val receiverId: Int,
    override val receiverIsValid: Boolean,
    override val receiverName: String?,
    override val receiverSimpleName: String,
) : ReviewableItem {
    override val timeMilliseconds: Long = System.currentTimeMillis()

    override fun isValid(): Boolean {
        return invokerHerb != null
    }

    override fun toString(): String {
        return "abilityId=$abilityId abilitySimpleName=$abilitySimpleName id=$id invokerHerb=$invokerHerb invokerId=$invokerId invokerIsValid=$invokerIsValid invokerItemCount=$invokerItemCount invokerName=$invokerName invokerSimpleName=$invokerSimpleName receiverHitPoints=$receiverHitPoints receiverId=$receiverId receiverIsValid=$receiverIsValid receiverName=$receiverName receiverSimpleName=$receiverSimpleName simpleName=$simpleName timeMilliseconds=$timeMilliseconds"
    }
}