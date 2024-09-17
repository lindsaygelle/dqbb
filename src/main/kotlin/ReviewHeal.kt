package dqbb

data class ReviewHeal(
    override val abilityId: Int,
    override val abilitySimpleName: String,
    val invokerHeal: Int?,
    override val invokerId: Int,
    val invokerMagicPoints: Int,
    override val invokerName: String?,
    override val invokerSimpleName: String,
    override val magicCost: Int,
    val receiverHitPoints: Int,
    override val receiverId: Int,
    override val receiverName: String?,
    override val receiverSimpleName: String,
) : ReviewableMagic {
    override val timeMilliseconds: Long = System.currentTimeMillis()

    override fun isValid(): Boolean {
        return invokerHeal != null
    }

    override fun toString(): String {
        return "abilityId=$abilityId abilitySimpleName=$abilitySimpleName id=$id invokerHeal=$invokerHeal invokerId=$invokerId invokerMagicPoints=$invokerMagicPoints invokerName=$invokerName invokerSimpleName=$invokerSimpleName magicCost=$magicCost receiverHitPoints=$receiverHitPoints receiverId=$receiverId receiverName=$receiverName receiverSimpleName=$receiverSimpleName simpleName=$simpleName timeMilliseconds=$timeMilliseconds"
    }
}