package dqbb

class Decision(
    val action: Action<Character, Character>,
    private val condition: Condition,
    private val retriever: Retriever,
) {
    fun check(character: Character, characters: Collection<Character>): Boolean {
        return condition.check(character, characters)
    }

    fun retrieve(character: Character, characters: Collection<Character>): Collection<Character> {
        return retriever.retrieve(character, characters)
    }
}