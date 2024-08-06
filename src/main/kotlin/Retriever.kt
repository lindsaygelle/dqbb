package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Retriever(
    private val selectors: Collection<Selector>
) {
    private var iterations: Int = 0
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)
    fun retrieve(character: Character, characters: Collection<Character>): Collection<Character> {
        val selection = mutableSetOf<Character>()
        selectors.forEachIndexed { index, selector ->
            selection.addAll(select(character, characters, index, selector))
            iterations += 1
        }
        logger.info("iterations=$iterations selection.size=${selection.size}")
        return selection
    }

    private fun select(
        character: Character, characters: Collection<Character>, index: Int, selector: Selector
    ): Collection<Character> {
        val selection = selector.select(character, characters)
        logger.debug("selection.size=${selection.size} selector.hashCode=${selector.hashCode()} index=$index")
        return selection
    }

    override fun toString(): String {
        return "class=${super.toString()} " +
               "hashCode=${hashCode()} " +
               "iterations=$iterations " +
               "selectors.size=${selectors.size}"
    }
}
