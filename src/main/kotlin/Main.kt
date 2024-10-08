package dqbb

import java.awt.BorderLayout
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.Timer
import kotlin.system.exitProcess


fun main() {

    val imageFolders = File({}.javaClass.getResource("/images").toURI()).listFiles().filter { file: File? ->
        file?.isDirectory() ?: false
    }
    val imageFolder = imageFolders.random()
    val imageSceneFolder = File(imageFolder.absolutePath + "/scenes")
    val imageSpriteFolder = File(imageFolder.absolutePath + "/sprites")
    val sceneImageFiles = imageSceneFolder.listFiles()
    val spriteImageFiles = imageSpriteFolder.listFiles()

    val attackEnemy = AttackEnemy<Actor, Actor>()
    val attackHero = AttackHero<Actor, Actor>()
    val breatheFire = BreatheFire<Actor, Actor>(20)
    val heal = Heal<Actor, Actor>(2)
    val healMore = HealMore<Actor, Actor>(10)
    val herb = Herb<Actor, Actor>()
    val hurt = Hurt<Actor, Actor>(2)
    val hurtMore = HurtMore<Actor, Actor>(4)
    val run = Run<Actor, Actor>()
    val magicPotion = MagicPotion<Actor, Actor>()
    val sleep = Sleep<Actor, Actor>(2)
    val stopSpell = StopSpell<Actor, Actor>(2)

    val actionAttackEnemy = Action(
        ability = attackEnemy, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ), selectionType = SelectionType.ENEMY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ENEMY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS, sortType = SortType.DESCENDING
        ), priorityType = PriorityType.LOW
    )

    val actionAttackHero = Action(
        ability = attackHero, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ), selectionType = SelectionType.ENEMY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ENEMY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS, sortType = SortType.DESCENDING
        ), priorityType = PriorityType.LOW
    )

    val actionBreatheFire = Action(
        ability = breatheFire, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = breatheFire.magicCost
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ), selectionType = SelectionType.SELF
                ), ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ), selectionType = SelectionType.ENEMY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ENEMY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS, sortType = SortType.DESCENDING
        ), priorityType = PriorityType.entries.random()
    )

    val actionHeal = Action(
        ability = heal, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = heal.magicCost
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ), selectionType = SelectionType.SELF
                ), ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN_EQUAL,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ), selectionType = SelectionType.ALLY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ALLY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS, sortType = SortType.ASCENDING
        ), priorityType = PriorityType.entries.random()
    )

    val actionHerb = Action(
        ability = herb, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.LESS_THAN,
                                    value = heal.magicCost
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_SLEEP,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                ), AttributeComparison(
                                    attributeName = AttributeName.HERB_COUNT,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            ), matchType = MatchType.ALL
                        )
                    ), selectionType = SelectionType.SELF
                ), ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN_EQUAL,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ), selectionType = SelectionType.ALLY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ALLY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS, sortType = SortType.ASCENDING
        ), priorityType = PriorityType.entries.random()
    )

    val actionHealMore = Action(
        ability = healMore, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = healMore.magicCost
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ), selectionType = SelectionType.SELF
                ), ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN_EQUAL,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ), selectionType = SelectionType.ALLY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ALLY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS, sortType = SortType.ASCENDING
        ), priorityType = PriorityType.entries.random()
    )

    val actionHurt = Action(
        ability = hurt, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = hurt.magicCost
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ), selectionType = SelectionType.SELF
                ), ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ), selectionType = SelectionType.ENEMY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ENEMY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS, sortType = SortType.DESCENDING
        ), priorityType = PriorityType.entries.random()
    )

    val actionHurtMore = Action(
        ability = hurtMore, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = hurtMore.magicCost
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ), selectionType = SelectionType.SELF
                ), ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ), selectionType = SelectionType.ENEMY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ENEMY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS, sortType = SortType.DESCENDING
        ), priorityType = PriorityType.entries.random()
    )

    val actionMagicPotion = Action(
        ability = magicPotion, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.LESS_THAN,
                                    value = heal.magicCost
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_SLEEP,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POTION_COUNT,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            ), matchType = MatchType.ALL
                        )
                    ), selectionType = SelectionType.SELF
                ), ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN_EQUAL,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ), selectionType = SelectionType.ALLY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.MAGIC_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ALLY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.MAGIC_POINTS, sortType = SortType.ASCENDING
        ), priorityType = PriorityType.entries.random()
    )

    val actionRun = Action(
        ability = run, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.LESS_THAN_EQUAL,
                                    value = 5
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_SLEEP,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ), selectionType = SelectionType.SELF
                ), ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                )
                            )
                        )
                    ), selectionType = SelectionType.ENEMY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ENEMY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.AGILITY, sortType = SortType.ASCENDING
        ), priorityType = PriorityType.entries.random()
    )

    val actionSleep = Action(
        ability = sleep, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = sleep.magicCost
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ), selectionType = SelectionType.SELF
                ), ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_SLEEP,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ), selectionType = SelectionType.ENEMY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ENEMY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS, sortType = SortType.DESCENDING
        ), priorityType = PriorityType.entries.random()
    )

    val actionStopSpell = Action(
        ability = stopSpell, actionCondition = ActionCondition(
            actionChecks = listOf(
                ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS_PERCENTAGE,
                                    operatorType = OperatorType.GREATER_THAN,
                                    value = 25
                                ), AttributeComparison(
                                    attributeName = AttributeName.MAGIC_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = stopSpell.magicCost
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ), selectionType = SelectionType.SELF
                ), ActionCheck(
                    attributeCriteria = listOf(
                        AttributeCriterion(
                            attributeComparisons = listOf(
                                AttributeComparison(
                                    attributeName = AttributeName.HIT_POINTS,
                                    operatorType = OperatorType.GREATER_THAN_EQUAL,
                                    value = 1
                                ), AttributeComparison(
                                    attributeName = AttributeName.TURNS_STOP_SPELL,
                                    operatorType = OperatorType.EQUAL,
                                    value = 0
                                )
                            )
                        )
                    ), selectionType = SelectionType.ENEMY
                )
            )
        ), actionTarget = ActionTarget(
            attributeCriteria = listOf(
                AttributeCriterion(
                    attributeComparisons = listOf(
                        AttributeComparison(
                            attributeName = AttributeName.HIT_POINTS,
                            operatorType = OperatorType.GREATER_THAN_EQUAL,
                            value = 1
                        )
                    )
                )
            ), selectionType = SelectionType.ENEMY
        ), attributeSort = AttributeSort(
            attributeName = AttributeName.HIT_POINTS, sortType = SortType.DESCENDING
        ), priorityType = PriorityType.entries.random()
    )

    val actors: MutableList<Actor> = mutableListOf()

    for (i in (0..4)) {

        val actions = mutableListOf(
            listOf(actionAttackEnemy, actionAttackHero).random(), actionRun, actionMagicPotion
        )

        var action = listOf(actionHeal, actionHealMore, actionHerb, null).random()
        if (action != null) {
            actions.add(action)
        }

        action = listOf(actionBreatheFire, actionHurt, actionHurtMore, null).random()

        if (action != null) {
            actions.add(action)
        }

        action = listOf(actionSleep, actionStopSpell, null).random()
        if (action != null) {
            actions.add(action)
        }

        val actor = Actor()

        actor.actions = actions
        actor.allegiance = i % 2
        actor.agility = (0..10).random()
        if ((0..1).random() == 1) {
            val armor = Armor(
                blocksSleep = listOf(true, false).random(),
                blocksStopSpell = listOf(true, false).random(),
                breatheFireReduction = (1..10).random(),
                defense = (3..10).random(),
                hurtReduction = (1..10).random(),
            )
            actor.armor = armor
        }
        actor.breatheFireRangeMaximum = 255
        actor.breatheFireScale = 0x10
        actor.breatheFireShift = 0x07
        actor.bufferedImage = ImageIO.read(spriteImageFiles.random())
        actor.evasionRequirementMaximum = 32
        actor.healMoreScale = 0x55 // 10
        actor.healMoreShift = 0x0F //
        actor.healRangeMaximum = (10..100).random()
        actor.healScale = 7
        actor.healShift = 3
        actor.herbRangeMaximum = (10..50).random()
        actor.herbScale = 3
        actor.herbShift = 7
        actor.hitPointsMaximum = (10..30).random()
        actor.hitPoints = actor.hitPointsMaximum
        actor.hurtMoreScale = listOf(0x1E, 0x3A).random()
        actor.hurtMoreShift = listOf(0x0F, 0x07).random()
        actor.hurtRangeMaximum = (0..255).random()
        actor.hurtRequirementMaximum = (0..16).random()
        actor.hurtResistanceMaximum = (0..16).random()
        actor.hurtScale = (3..10).random()
        actor.hurtShift = (0..10).random()
        actor.items[ItemName.HERB] = (3..10).random()
        actor.items[ItemName.MAGIC_POTION] = (3..10).random()
        actor.magicPointsMaximum = (6..12).random()
        actor.magicPoints = actor.magicPointsMaximum
        actor.magicPotionRangeMaximum = (10..50).random()
        actor.magicPotionScale = 3
        actor.magicPotionShift = 7
        actor.runRangeMaximum = (0..16).random()
        actor.runShift = 2
        if ((0..1).random() == 0) {
            val shield = Shield(
                defense = (3..10).random()
            )
            actor.shield = shield
        }
        actor.sleepRequirementMaximum = (0..16).random()
        actor.sleepResistanceMaximum = (0..16).random()
        actor.sleepResolutionMaximum = 3
        actor.strength = (1..10).random()
        actor.turnsSleepMaximum = (3..6).random()
        actor.turnsSleepMinimum = (0..<actor.turnsSleepMaximum).random()
        if ((0..1).random() == 1) {
            val weapon = Weapon(
                attack = (3..10).random()
            )
            actor.weapon = weapon
        }
        actors.add(actor)
    }

    val panelBattle = PanelBattle()
    panelBattle.actors = actors
    panelBattle.bufferedImageField = ImageIO.read(sceneImageFiles.random())

    val frame = Frame()
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.layout = BorderLayout()
    frame.add(panelBattle, BorderLayout.CENTER)
    frame.pack()
    frame.setResizable(false)
    frame.setLocationRelativeTo(null)
    frame.setVisible(true) // Timer to run at 60 FPS (16.67 ms per frame)
    val delay = (1000 / 60)  // milliseconds for 60 FPS
    val timer = Timer(delay) {
        panelBattle.run()  // Update the panel
    }
    timer.start()
}