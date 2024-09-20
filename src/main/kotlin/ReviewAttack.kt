package dqbb

data class ReviewAttack(
    override val abilityId: Int,
    override val abilitySimpleName: String,
    val invokerAttack: Int?,
    val invokerAttackType: AttackType?,
    override val invokerId: Int,
    override val invokerIsValid: Boolean,
    override val invokerName: String?,
    override val invokerSimpleName: String,
    val invokerWeaponAttack: Int?,
    val invokerWeaponId: Int?,
    val invokerWeaponName: String?,
    val invokerWeaponSimpleName: String?,
    val receiverArmorDefense: Int?,
    val receiverArmorId: Int?,
    val receiverArmorName: String?,
    val receiverArmorSimpleName: String?,
    val receiverHitPoints: Int,
    override val receiverId: Int,
    override val receiverIsValid: Boolean,
    override val receiverName: String?,
    val receiverShieldDefense: Int?,
    val receiverShieldId: Int?,
    val receiverShieldName: String?,
    val receiverShieldSimpleName: String?,
    override val receiverSimpleName: String,
) : Reviewable {
    override val timeMilliseconds: Long = System.currentTimeMillis()

    override fun isValid(): Boolean {
        return invokerAttack != null
    }

    override fun toString(): String {
        return "abilityId=$abilityId abilitySimpleName=$abilitySimpleName id=$id invokerAttack=$invokerAttack invokerAttackType=$invokerAttackType invokerId=$invokerId invokerIsValid=$invokerIsValid invokerName=$invokerName invokerSimpleName=$invokerSimpleName invokerWeaponAttack=$invokerWeaponAttack invokerWeaponId=$invokerWeaponId invokerWeaponName=$invokerWeaponName invokerWeaponSimpleName=$invokerWeaponSimpleName receiverArmorDefense=$receiverArmorDefense receiverArmorId=$receiverArmorId receiverArmorName=$receiverArmorName receiverArmorSimpleName=$receiverArmorSimpleName receiverHitPoints=$receiverHitPoints receiverId=$receiverId receiverIsValid=$receiverIsValid receiverName=$receiverName receiverShieldDefense=$receiverShieldDefense receiverShieldId=$receiverShieldId receiverShieldName=$receiverShieldName receiverShieldSimpleName=$receiverShieldSimpleName receiverSimpleName=$receiverSimpleName simpleName=$simpleName timeMilliseconds=$timeMilliseconds"
    }
}