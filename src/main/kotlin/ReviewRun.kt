package dqbb

data class ReviewRun(
    override val abilityId: Int,
    override val abilitySimpleName: String,
    override val invokerId: Int,
    val invokerIsRunning: Boolean,
    override val invokerName: String?,
    override val invokerSimpleName: String,
    override val receiverId: Int,
    override val receiverName: String?,
    override val receiverSimpleName: String,
    val receiverTurnsSleep: Int,
) : Reviewable {
    override val timeMilliseconds: Long = System.currentTimeMillis()

    override fun isValid(): Boolean {
        return invokerIsRunning
    }

    override fun toString(): String {
        return "abilityId=$abilityId abilitySimpleName=$abilitySimpleName id=$id invokerId=$invokerId invokerIsRunning=$invokerIsRunning invokerName=$invokerName invokerSimpleName=$invokerSimpleName receiverId=$receiverId receiverName=$receiverName receiverSimpleName=$receiverSimpleName receiverTurnsSleep=$receiverTurnsSleep simpleName=$simpleName timeMilliseconds=$timeMilliseconds"
    }
}