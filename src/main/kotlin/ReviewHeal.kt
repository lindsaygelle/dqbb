package dqbb

data class ReviewHeal(
    override val abilityId: Int,
    override val abilitySimpleName: String,
    val invokerHeal: Int?,
    override val invokerId: Int,
    override val invokerIsValid: Boolean,
    val invokerMagicPoints: Int,
    override val invokerName: String?,
    override val invokerSimpleName: String,
    override val magicCost: Int,
    val receiverHitPoints: Int,
    val receiverHitPointsMaximum: Int,
    override val receiverId: Int,
    override val receiverIsValid: Boolean,
    override val receiverName: String?,
    override val receiverSimpleName: String,
) : ReviewableMagic {
    override val timeMilliseconds: Long = System.currentTimeMillis()

    override fun isValid(): Boolean {
        return invokerHeal != null
    }

    override fun toString(): String {
        return "abilityId=$abilityId abilitySimpleName=$abilitySimpleName id=$id invokerHeal=$invokerHeal invokerId=$invokerId invokerIsValid=$invokerIsValid invokerMagicPoints=$invokerMagicPoints invokerName=$invokerName invokerSimpleName=$invokerSimpleName magicCost=$magicCost receiverHitPoints=$receiverHitPoints receiverHitPointsMaximum=$receiverHitPointsMaximum receiverId=$receiverId receiverIsValid=$receiverIsValid receiverName=$receiverName receiverSimpleName=$receiverSimpleName simpleName=$simpleName timeMilliseconds=$timeMilliseconds"
    }
}