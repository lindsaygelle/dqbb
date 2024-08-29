package dqbb

interface AttributeProvider : Identifier {
    fun getAttribute(attributeName: AttributeName): Int?
}