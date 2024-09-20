package dqbb

data class ReviewHurt(
    override val abilityId: Int,
    override val abilitySimpleName: String,
    val invokerHurt: Int?,
    override val invokerId: Int,
    override val invokerIsValid: Boolean,
    override val invokerName: String?,
    override val invokerSimpleName: String,
    override val magicCost: Int,
    val receiverArmorHurtReduction: Int?,
    val receiverArmorId: Int?,
    val receiverArmorName: String?,
    val receiverArmorSimpleName: String?,
    val receiverHitPoints: Int,
    override val receiverId: Int,
    override val receiverIsValid: Boolean,
    override val receiverName: String?,
    override val receiverSimpleName: String,
) : ReviewableMagic {
    override val timeMilliseconds: Long = System.currentTimeMillis()

    override fun isValid(): Boolean {
        return invokerHurt != null
    }

    override fun toString(): String {
        return "abilityId=$abilityId abilitySimpleName=$abilitySimpleName id=$id invokerHurt=$invokerHurt invokerId=$invokerId invokerIsValid=$invokerIsValid invokerName=$invokerName invokerSimpleName=$invokerSimpleName magicCost=$magicCost receiverArmorHurtReduction=$receiverArmorHurtReduction receiverArmorId=$receiverArmorId receiverArmorName=$receiverArmorName receiverArmorSimpleName=$receiverArmorSimpleName receiverHitPoints=$receiverHitPoints receiverId=$receiverId receiverIsValid=$receiverIsValid receiverName=$receiverName receiverSimpleName=$receiverSimpleName simpleName=$simpleName timeMilliseconds=$timeMilliseconds"
    }
}