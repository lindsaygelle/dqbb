package dqbb

abstract class Equipment(
    name: String,
) : Wearable {

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    override val name: String = name.uppercase()
}
