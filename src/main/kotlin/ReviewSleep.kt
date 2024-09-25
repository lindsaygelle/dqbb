package dqbb

data class ReviewSleep(
    override val abilityId: Int,
    override val abilitySimpleName: String,
    override val invokerId: Int,
    override val invokerIsValid: Boolean,
    override val invokerName: String?,
    override val invokerSimpleName: String,
    override val magicCost: Int,
    val receiverArmorBlocksSleep: Boolean?,
    val receiverArmorId: Int?,
    val receiverArmorName: String?,
    val receiverArmorSimpleName: String?,
    override val receiverId: Int,
    override val receiverIsValid: Boolean,
    override val receiverName: String?,
    override val receiverSimpleName: String,
    val receiverTurnsSleep: Int,
) : ReviewableMagic {
    override val timeMilliseconds: Long = System.currentTimeMillis()

    override fun isValid(): Boolean {
        return receiverTurnsSleep > 0
    }

    override fun toString(): String {
        return "abilityId=$abilityId abilitySimpleName=$abilitySimpleName id=$id invokerId=$invokerId invokerIsValid=$invokerIsValid invokerName=$invokerName invokerSimpleName=$invokerSimpleName magicCost=$magicCost receiverArmorBlocksSleep=$receiverArmorBlocksSleep receiverArmorId=$receiverArmorId receiverArmorName=$receiverArmorName receiverArmorSimpleName=$receiverArmorSimpleName receiverId=$receiverId receiverIsValid=$receiverIsValid receiverName=$receiverName receiverSimpleName=$receiverSimpleName receiverTurnsSleep=$receiverTurnsSleep simpleName=$simpleName timeMilliseconds=$timeMilliseconds"
    }
}