package dqbb

data class ReviewStopSpell(
    override val abilityId: Int,
    override val abilitySimpleName: String,
    override val invokerId: Int,
    override val invokerName: String?,
    override val invokerSimpleName: String,
    override val magicCost: Int,
    val receiverArmorBlocksStopSpell: Boolean?,
    val receiverArmorId: Int?,
    val receiverArmorName: String?,
    val receiverArmorSimpleName: String?,
    override val receiverId: Int,
    override val receiverName: String?,
    override val receiverSimpleName: String,
    val receiverTurnsStopSpell: Int,
) : ReviewableMagic {
    override val timeMilliseconds: Long = System.currentTimeMillis()

    override fun isValid(): Boolean {
        return receiverTurnsStopSpell > 0
    }

    override fun toString(): String {
        return "abilityId=$abilityId abilitySimpleName=$abilitySimpleName id=$id invokerId=$invokerId invokerName=$invokerName invokerSimpleName=$invokerSimpleName magicCost=$magicCost receiverArmorBlocksStopSpell=$receiverArmorBlocksStopSpell receiverArmorId=$receiverArmorId receiverArmorName=$receiverArmorName receiverArmorSimpleName=$receiverArmorSimpleName receiverId=$receiverId receiverName=$receiverName receiverSimpleName=$receiverSimpleName receiverTurnsStopSpell=$receiverTurnsStopSpell simpleName=$simpleName timeMilliseconds=$timeMilliseconds"
    }
}