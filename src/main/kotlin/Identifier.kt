package dqbb

interface Identifier {
    val id: Int
        get() = hashCode()
    val simpleName: String
        get() = javaClass.simpleName
}