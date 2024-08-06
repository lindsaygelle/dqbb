package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Selector(
    private val matchers: Collection<Matcher>,
    private val selectionType: SelectionType,
) {
    private var iterations: Int = 0
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private fun check(character: Character, characterOther: Character, index: Int): Boolean {
        logger.debug(
            "character.hashCode=${character.hashCode()} " +
            "characterOther.hashCode=${characterOther.hashCode()} " +
            "index=$index"
        )
        val checkTarget = checkTarget(character, characterOther)
        logger.info("checkTarget=$checkTarget")
        if (!checkTarget) {
            return false
        }
        val checkMatchers = checkMatchers(characterOther)
        logger.info("checkMatchers=$checkMatchers")
        return checkMatchers
    }

    private fun checkMatchers(character: Character): Boolean {
        return (matchers.isEmpty()) || return matchers.withIndex().all { (index, matcher) ->
            match(character, index, matcher)
        }
    }

    private fun checkTarget(character: Character, characterOther: Character): Boolean {
        logger.debug(
            "character.allegiance=${character.allegiance} " +
            "character.hashCode=${character.hashCode()} " +
            "characterOther.allegiance=${characterOther.allegiance} " +
            "characterOther.hashCode=${characterOther.hashCode()}"
        )
        return when (selectionType) {
            SelectionType.ALLY -> character.allegiance == characterOther.allegiance
            SelectionType.ANY -> true
            SelectionType.ENEMY -> character.allegiance != characterOther.allegiance
            SelectionType.SELF -> character == characterOther
        }
    }

    private fun match(character: Character, index: Int, matcher: Matcher): Boolean {
        val matchValue = matcher.match(character)
        logger.debug(
            "character.hashCode=${character.hashCode()} " +
            "index=$index " +
            "iterations=$iterations " +
            "matcher.hashCode=${matcher.hashCode()} " +
            "matcher.match=$matchValue"
        )
        return matchValue
    }

    fun select(character: Character, characters: Collection<Character>): Collection<Character> {
        logger.debug("character.hashCode=${character.hashCode()} characters.size=${characters.size}")
        val filteredCharacters = mutableSetOf<Character>()
        characters.forEachIndexed { index, characterOther ->
            if (check(character, characterOther, index)) {
                filteredCharacters.add(characterOther)
            }
            iterations += 1
        }
        logger.info("filteredCharacters.size=${filteredCharacters.size} iterations=$iterations")
        return filteredCharacters
    }

    override fun toString(): String {
        return "class=${super.toString()} " +
               "hashCode=${hashCode()} " +
               "matchers.size=${matchers.size} " +
               "selectionType=$selectionType"
    }
}
