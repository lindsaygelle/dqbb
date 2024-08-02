package dqbb

abstract class Equipment(
    name: String,
) : Identifier {

    override val id: String = Integer.toHexString(System.identityHashCode(this))

    val name: String = name.uppercase()
}
