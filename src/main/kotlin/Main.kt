package dqbb


fun main() {

    val defaultAttackDecision = Decision(
        action = Attack(),
        condition = Condition(
            selectors = listOf(
                Selector(
                    matchers = listOf(
                        Matcher(
                            checkers = listOf(
                                Checker(
                                    checkType = CheckType.HIT_POINTS,
                                    operatorType = OperatorType.LESS_THAN,
                                    value = 10
                                )
                            ),
                            matchType = MatchType.ALL
                        )
                    ),
                    selectionType = SelectionType.ENEMY
                )
            )
        ),
        retriever = Retriever(
            selectors = listOf(
                Selector(
                    matchers = listOf(),
                    selectionType = SelectionType.ENEMY
                )
            )
        )
    )

    val characterAlly = Character(allegiance = 0, decisions = listOf(defaultAttackDecision))
    val characterEnemy = Character(allegiance = 1, decisions = listOf(defaultAttackDecision), statusResistance = 255)

    SpellHurt<Character, Character>(magicCost = 0).use(characterAlly, characterEnemy)
    SpellHurt<Character, Character>(magicCost = 0).use(characterEnemy, characterAlly)
    SpellSleep<Character, Character>(magicCost = 0).use(characterAlly, characterEnemy)
    SpellStopSpell<Character, Character>(magicCost = 0).use(characterAlly, characterEnemy)

}