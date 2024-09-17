package dqbb

interface Reviewable : Identifier {
    val abilityId: Int
    val abilitySimpleName: String
    val invokerId: Int
    val invokerName: String?
    val invokerSimpleName: String
    val receiverId: Int
    val receiverName: String?
    val receiverSimpleName: String
    val timeMilliseconds: Long

    fun isValid(): Boolean
}