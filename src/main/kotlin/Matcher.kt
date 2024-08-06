package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Matcher(
    private val checkers: Collection<Checker>,
    private val matchType: MatchType,
) {
    private var iterations: Int = 0
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    private fun checkCharacter(character: Character, checker: Checker, index: Int): Boolean {
        val checkerValue = checker.check(character)
        logger.debug(
            "character.hashCode=${character.hashCode()} " +
            "checker.check=$checkerValue " +
            "checker.hashCode=${checker.hashCode()} " +
            "index=$index " +
            "iterations=$iterations"
        )
        iterations += 1
        return checkerValue
    }

    private fun checkMatch(character: Character): Boolean {
        return when (matchType) {
            MatchType.ALL -> matchAll(character)
            MatchType.ANY -> matchAny(character)
        }
    }

    fun match(character: Character): Boolean {
        val checkMatch = checkMatch(character)
        logger.info("character.hashCode=${character.hashCode()} checkMatch=$checkMatch iterations=$iterations")
        return checkMatch
    }

    private fun matchAll(character: Character): Boolean {
        return checkers.withIndex().all { (index, checker) ->
            checkCharacter(character, checker, index)
        }
    }

    private fun matchAny(character: Character): Boolean {
        return checkers.withIndex().any { (index, checker) ->
            checkCharacter(character, checker, index)
        }
    }

    override fun toString(): String {
        return "checkers.size=${checkers.size} " +
               "class=${super.toString()} " +
               "hashCode=${hashCode()} " +
               "iterations=$iterations " +
               "matchType=$matchType"
    }
}