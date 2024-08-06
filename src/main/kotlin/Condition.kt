package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Condition(
    private val selectors: Collection<Selector>,
) {
    private var iterations: Int = 0
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    fun check(character: Character, characters: Collection<Character>): Boolean {
        logger.debug("character.hashCode=${character.hashCode()} characters.size=${characters.size}")
        val check = selectors.withIndex().all { (index, selector) ->
            select(character, characters, index, selector)
        }
        logger.info("check=$check iterations=$iterations")
        return check
    }

    private fun select(
        character: Character, characters: Collection<Character>, index: Int, selector: Selector
    ): Boolean {
        val selection = selector.select(character, characters)
        logger.debug(
            "character.hashCode=${character.hashCode()} " +
            "index=$index" +
            "selection.size=${selection.size} " +
            "selector.hashCode=${selector.hashCode()}"

        )
        iterations += 1
        return selection.isNotEmpty()
    }

    override fun toString(): String {
        return "class=${super.toString()} hashCode=${hashCode()} selectors.size=${selectors.size}"
    }
}