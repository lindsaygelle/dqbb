package dqbb

import java.awt.*
import java.awt.Image
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import javax.swing.JButton

class PanelBattle(
    actors: Collection<Actor>,
) : Panel(),
        Runnable {
    private enum class StateType {
        RESET,
        RETRIEVE_INDEXED_ACTION,
        RETRIEVE_ACTOR_ITERATOR,
        RETRIEVE_INDEXED_ACTOR,
        RETRIEVE_REVIEWABLE,
        RETRIEVE_TARGET_ACTOR,
        RETRIEVE_TARGETED_ACTORS,
    }

    private var actors: Collection<Actor> = actors
        set(value) {
            field = value
            logger.debug(
                "actors.hashCode={} actors.size={} id={} simpleName={}", field.hashCode(), field.size, id, simpleName
            )
        }

    private val allegiances: MutableSet<Int> = mutableSetOf()

    private var actorIterator: Iterator<IndexedValue<Actor>>? = null
        set(value) {
            field = value
            logger.debug(
                "actorIterator.hashCode={} actorIterator.hasNext={} id={} simpleName={}",
                field?.hashCode(),
                field?.hasNext(),
                id,
                simpleName
            )
        }

    private val battleSystem: BattleSystem = BattleSystem()

    private val bufferedImage: BufferedImage = BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB)

    private val graphics2D: Graphics2D = bufferedImage.graphics as Graphics2D

    private val font: Font =
        Font.createFont(Font.TRUETYPE_FONT, javaClass.getResourceAsStream("/fonts/DRAGON_QUEST.ttf"))

    private var fontMetrics: FontMetrics

    private val fontSize: Float = 9f

    private var indexedValueAction: IndexedValue<Action<Actor, Actor>>? = null
        set(value) {
            field = value
            logger.debug(
                "id={} indexedValueAction.index={} indexedValueAction.value.id={} simpleName={}",
                id,
                field?.index,
                field?.value?.id,
                simpleName
            )
        }

    private var indexedValueActor: IndexedValue<Actor>? = null
        set(value) {
            field = value
            logger.debug(
                "id={} indexedValueActor.index={} indexedValueActor.value.id={} simpleName={}",
                id,
                field?.index,
                field?.value?.id,
                simpleName
            )
        }

    private val rectangle: Rectangle = Rectangle(bufferedImage.width, bufferedImage.height)

    private var reviewable: Reviewable? = null
        set(value) {
            field = value
            logger.debug(
                "id={} reviewable.id={} reviewable.simpleName={} simpleName={}",
                id,
                field?.id,
                field?.simpleName,
                simpleName
            )
        }

    private var stateType: StateType = StateType.RESET
        set(value) {
            field = value
            logger.debug(
                "id={} simpleName={} stateType.name={} stateType.ordinal={}", id, simpleName, field.name, field.ordinal
            )
        }

    private var targetActor: Actor? = null
        set(value) {
            field = value
            logger.debug(
                "id={} simpleName={} targetActor.id={}", id, simpleName, field?.id
            )
        }

    private var targetedActors: Collection<Actor>? = null
        set(value) {
            field = value
            logger.debug(
                "id={} simpleName={} targetActors.size={}", id, simpleName, field?.size
            )
        }

    private val jButtonNext = JButton(stateType.name)

    init {
        graphics2D.font = font.deriveFont(Font.PLAIN, fontSize)
        fontMetrics = graphics2D.fontMetrics
        layout = BorderLayout()
        preferredSize = Dimension(bufferedImage.width, bufferedImage.height)
        jButtonNext.addActionListener {
            process()
            jButtonNext.text = stateType.name
        }
        add(jButtonNext, BorderLayout.SOUTH)
    }

    private fun draw(
        graphics2D: Graphics2D,
        font: Font,
        fontMetrics: FontMetrics,
        rectangle: Rectangle,
    ) {
        graphics2D.color = Color.BLACK
        graphics2D.fillRect(0, 0, width, height)

        when (stateType) {
            StateType.RETRIEVE_ACTOR_ITERATOR -> {
                drawRetrieveActorIterator(font, fontMetrics, graphics2D, rectangle)
            }

            StateType.RETRIEVE_INDEXED_ACTOR -> {
                drawRetrieveIndexedActor(font, fontMetrics, graphics2D, rectangle)
            }

            StateType.RETRIEVE_INDEXED_ACTION -> {
                indexedValueActor?.let { indexedValueActor: IndexedValue<Actor> ->
                    drawRetrieveIndexedAction(
                        indexedValueActor.value, indexedValueActor.index, font, fontMetrics, graphics2D, rectangle
                    )
                }
            }

            StateType.RETRIEVE_TARGETED_ACTORS -> {
                indexedValueActor?.let { indexedValueActor: IndexedValue<Actor> ->
                    indexedValueAction?.let { indexedValueAction: IndexedValue<Action<Actor, Actor>> ->
                        drawRetrieveTargetedActors(
                            indexedValueAction.value,
                            indexedValueAction.index,
                            indexedValueActor.value,
                            indexedValueActor.index,
                            actors,
                            font,
                            fontMetrics,
                            graphics2D,
                            rectangle
                        )
                    }
                }
            }

            StateType.RETRIEVE_TARGET_ACTOR -> {
                indexedValueActor?.let { indexedValueActor: IndexedValue<Actor> ->
                    indexedValueAction?.let { indexedValueAction: IndexedValue<Action<Actor, Actor>> ->
                        indexedValueAction.value.ability?.let { ability: Ability<Actor, Actor> ->
                            targetActor?.let { targetActor: Actor ->
                                drawRetrieveTargetActor(
                                    ability,
                                    indexedValueActor.value,
                                    indexedValueActor.index,
                                    font,
                                    fontMetrics,
                                    graphics2D,
                                    rectangle,
                                    targetActor,
                                )
                            }
                        }
                    }
                }
            }

            StateType.RETRIEVE_REVIEWABLE -> {
                indexedValueActor?.let { indexedValueActor: IndexedValue<Actor> ->
                    targetActor?.let { targetActor: Actor ->
                        reviewable?.let { reviewable: Reviewable ->
                            drawRetrieveReviewable(
                                indexedValueActor.value,
                                indexedValueActor.index,
                                font,
                                fontMetrics,
                                graphics2D,
                                rectangle,
                                reviewable,
                                targetActor,
                            )
                        }
                    }
                }
            }

            StateType.RESET -> {

            }
        }
    }

    private fun drawAbilityIdentifier(
        ability: Ability<Actor, Actor>,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        val message = ability.simpleName
        val x: Int = (rectangle.centerX - (fontMetrics.stringWidth(message) / 2)).toInt()
        val y: Int = (rectangle.y + 60)
        graphics2D.color = Color.ORANGE
        graphics2D.drawString(message, x, y)
    }

    private fun drawActor(
        actor: Actor,
        actorIndex: Int,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        val lineHeight = 10
        val yPosition = drawActorEquipment(
            actor, Color.WHITE, font, fontMetrics, graphics2D, lineHeight, rectangle.x, rectangle.y
        )
        drawActorAttributes(
            actor, actorIndex, Color.WHITE, font, fontMetrics, graphics2D, lineHeight, rectangle.x, yPosition
        )
        drawActorAttributeBufferedImage(actor, graphics2D, rectangle)
    }

    private fun drawActorAttributes(
        actor: Actor,
        actorIndex: Int,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        x: Int,
        y: Int,
    ): Int {
        val xPosition = x + lineHeight
        var yPosition: Int = y + lineHeight
        drawString(Color.YELLOW, font, fontMetrics, graphics2D, "Attributes", xPosition, yPosition)
        yPosition += lineHeight
        val drawActorAttributes = listOf(
            ::drawActorAttributeActions,
            ::drawActorAttributeAgility,
            ::drawActorAttributeAllegiance,
            //::drawActorAttributeBreatheFireRangeMaximum,
            //::drawActorAttributeBreatheFireRangeMinimum,
            ::drawActorAttributeBreathe, // TODO
            ::drawActorAttributeBreatheFireScale,
            ::drawActorAttributeBreatheFireShift,
            ::drawActorAttributeCanReceiveExcellentAttack,
            ::drawActorAttributeEvasionRequirement, // TODO
            //::drawActorAttributeEvasionRequirementMaximum,
            //::drawActorAttributeEvasionRequirementMinimum,
            ::drawActorAttributeExcellentAttackRequirement, // TODO
            //::drawActorAttributeExcellentAttackRequirementMaximum,
            //::drawActorAttributeExcellentAttackRequirementMinimum,
            ::drawActorAttributeHealMoreScale,
            ::drawActorAttributeHealMoreShift,
            ::drawActorAttributeHealRange, // TODO
            //::drawActorAttributeHealRangeMaximum,
            //::drawActorAttributeHealRangeMinimum,
            ::drawActorAttributeHerbCount,
            ::drawActorAttributeHealScale,
            ::drawActorAttributeHealShift,
            ::drawActorAttributeHerbRange, // TODO
            //::drawActorAttributeHerbRangeMaximum,
            //::drawActorAttributeHerbRangeMinimum,
            ::drawActorAttributeHerbScale,
            ::drawActorAttributeHerbShift,
            //::drawActorAttributeHitPoints,
            //::drawActorAttributeHitPointsMaximum,
            //::drawActorAttributeHitPointsPercentage,
            ::drawActorAttributeHitPointsRange, // TODO
            ::drawActorAttributeHurtMoreScale,
            ::drawActorAttributeHurtMoreShift,
            ::drawActorAttributeHurtRange, // TODO
            //::drawActorAttributeHurtRangeMaximum,
            //::drawActorAttributeHurtRangeMinimum,
            ::drawActorAttributeHurtRequirement, // TODO
            //::drawActorAttributeHurtRequirementMaximum,
            //::drawActorAttributeHurtRequirementMinimum,
            ::drawActorAttributeHurtResistance, // TODO
            //::drawActorAttributeHurtResistanceMaximum,
            //::drawActorAttributeHurtResistanceMinimum,
            ::drawActorAttributeHurtScale,
            ::drawActorAttributeHurtShift,
            ::drawActorAttributeIsRunning,
            //::drawActorAttributeMagicPoints,
            //::drawActorAttributeMagicPointsMaximum,
            //::drawActorAttributeMagicPointsPercentage,
            ::drawActorAttributeMagicPointsRange,
            ::drawActorAttributeMagicPotionCount,
            ::drawActorAttributeMagicPotionRange, // TODO
            //::drawActorAttributeMagicPotionRangeMaximum,
            //::drawActorAttributeMagicPotionRangeMinimum,
            ::drawActorAttributeMagicPotionScale,
            ::drawActorAttributeMagicPotionShift,
            ::drawActorAttributeName,
            ::drawActorAttributeRunRange, // TODO
            //::drawActorAttributeRunRangeMaximum,
            //::drawActorAttributeRunRangeMinimum,
            ::drawActorAttributeRunShift,
            ::drawActorAttributeSleepRequirement, // TODO
            //::drawActorAttributeSleepRequirementMaximum,
            //::drawActorAttributeSleepRequirementMinimum,
            ::drawActorAttributeSleepResistance, // TODO
            //::drawActorAttributeSleepResistanceMaximum,
            //::drawActorAttributeSleepResistanceMinimum,
            ::drawActorAttributeSleepResolution, // TODO
            //::drawActorAttributeSleepResolutionMaximum,
            //::drawActorAttributeSleepResolutionMinimum,
            ::drawActorAttributeStopSpellRequirement, // TODO
            //::drawActorAttributeStopSpellRequirementMaximum,
            //::drawActorAttributeStopSpellRequirementMinimum,
            ::drawActorAttributeStopSpellResistance, // TODO
            //::drawActorAttributeStopSpellResistanceMaximum,
            //::drawActorAttributeStopSpellResistanceMinimum,
            ::drawActorAttributeStopSpellResolution, // TODO
            //::drawActorAttributeStopSpellResolutionMaximum,
            //::drawActorAttributeStopSpellResolutionMinimum,
            ::drawActorAttributeStrength,
            ::drawActorAttributeTurn,
            //::drawActorAttributeTurnsSleep,
            //::drawActorAttributeTurnsSleepMaximum,
            //::drawActorAttributeTurnsSleepMinimum,
            ::drawActorAttributeTurnsSleepRange,  // TODO
            //::drawActorAttributeTurnsStopSpell,
            //::drawActorAttributeTurnsStopSpellMaximum,
            //::drawActorAttributeTurnsStopSpellMinimum,
            ::drawActorAttributeTurnsStopSpellRange,  // TODO
        )
        drawActorAttributes.forEachIndexed { _, drawActorAttribute ->
            drawActorAttribute(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
            yPosition += lineHeight
        }
        return yPosition
    }

    private fun drawActorAttributeActions(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Actions: ${actor.actions.size}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeAgility(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Agility: ${actor.agility}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeAllegiance(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Allegiance: ${actor.allegiance}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeBreathe(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Range: ${actor.breatheFireRangeMinimum}-${actor.breatheFireRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeBreatheFireRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Range Maximum: ${actor.breatheFireRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeBreatheFireRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Range Minimum: ${actor.breatheFireRangeMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeBreatheFireScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Scale: ${actor.breatheFireScale}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeBreatheFireShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Shift: ${actor.breatheFireShift}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeBufferedImage(actor: Actor, graphics2D: Graphics2D, rectangle: Rectangle) {
        actor.bufferedImage?.let { bufferedImage: BufferedImage ->
            val x = (rectangle.centerX - (bufferedImage.width / 2))
            val y = (rectangle.centerY - (bufferedImage.height / 2))
            drawImage(bufferedImage, graphics2D, x.toInt(), y.toInt())
        }
    }

    private fun drawActorAttributeCanReceiveExcellentAttack(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Can Receive Excellent Attack: ${actor.canReceiveExcellentAttack}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeEvasionRequirement(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Evasion Requirement: ${actor.evasionRequirementMinimum}-${actor.evasionRequirementMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeEvasionRequirementMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Evasion Requirement Maximum: ${actor.evasionRequirementMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeEvasionRequirementMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Evasion Requirement Minimum: ${actor.evasionRequirementMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeExcellentAttackRequirement(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value =
            "Excellent Attack Requirement: ${actor.excellentAttackRequirementMinimum}-${actor.excellentAttackRequirementMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeExcellentAttackRequirementMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Excellent Attack Requirement Maximum: ${actor.excellentAttackRequirementMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeExcellentAttackRequirementMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Excellent Attack Requirement Minimum: ${actor.excellentAttackRequirementMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHealMoreScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal More Scale: ${actor.healMoreScale}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHealMoreShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal More Shift: ${actor.healMoreShift}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHealRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal Range: ${actor.healRangeMinimum}-${actor.healRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHealRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal Range Maximum: ${actor.healRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHealRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal Range Minimum: ${actor.healRangeMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHerbCount(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Count: ${actor.herbCount}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHealScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal Scale: ${actor.healScale}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHealShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal Shift: ${actor.healShift}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHerbRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Range: ${actor.herbRangeMinimum}-${actor.herbRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHerbRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Range Maximum: ${actor.herbRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHerbRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Range Minimum: ${actor.herbRangeMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHerbScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Scale: ${actor.herbScale}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHerbShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Shift: ${actor.herbShift}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHitPoints(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hit Points: ${actor.hitPoints}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHitPointsMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hit Points Maximum: ${actor.hitPointsMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHitPointsPercentage(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hit Points Percentage: ${actor.hitPointsPercentage}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHitPointsRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hit Points: ${actor.hitPoints}-${actor.hitPointsMaximum} (${actor.hitPointsPercentage}%)"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtMoreScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt More Scale: ${actor.hurtMoreScale}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtMoreShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt More Shift: ${actor.hurtMoreShift}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Range: ${actor.hurtRangeMinimum}-${actor.hurtRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Range Maximum: ${actor.hurtRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Range Minimum: ${actor.hurtRangeMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtRequirement(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Requirement: ${actor.hurtRequirementMinimum}-${actor.hurtRequirementMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtRequirementMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Requirement Maximum: ${actor.hurtRequirementMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtRequirementMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Requirement Minimum: ${actor.hurtRequirementMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtResistance(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Resistance: ${actor.hurtResistanceMinimum}-${actor.hurtResistanceMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtResistanceMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Resistance Maximum: ${actor.hurtResistanceMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtResistanceMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Resistance Minimum: ${actor.hurtResistanceMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Scale: ${actor.hurtScale}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeHurtShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Shift: ${actor.hurtShift}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeIsRunning(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Is Running: ${if (actor.isRunning) "Y" else "N"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeMagicPoints(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Points: ${actor.magicPoints}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeMagicPointsMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Points Maximum: ${actor.magicPointsMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeMagicPointsPercentage(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Points Percentage: ${actor.magicPointsPercentage}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeMagicPointsRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Points: ${actor.magicPoints}-${actor.magicPointsMaximum} (${actor.magicPointsPercentage})"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeMagicPotionCount(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion Count: ${actor.magicPotionCount}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeMagicPotionRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion: ${actor.magicPotionRangeMaximum}-${actor.magicPotionRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeMagicPotionRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion Range Maximum: ${actor.magicPotionRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeMagicPotionRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion Range Minimum: ${actor.magicPotionRangeMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeMagicPotionScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion Scale: ${actor.magicPotionScale}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeMagicPotionShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion Shift: ${actor.magicPotionShift}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeName(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Name: ${actor.name ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeRunRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Run Range: ${actor.runRangeMinimum}-${actor.runRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeRunRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Run Range Maximum: ${actor.runRangeMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeRunRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Run Range Minimum: ${actor.runRangeMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeRunShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Run Shift: ${actor.runShift}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeSleepRequirement(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Requirement: ${actor.sleepRequirementMinimum}-${actor.sleepRequirementMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeSleepRequirementMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Requirement Maximum: ${actor.sleepRequirementMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeSleepRequirementMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Requirement Minimum: ${actor.sleepRequirementMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeSleepResistance(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resistance: ${actor.sleepResistanceMinimum}-${actor.sleepResistanceMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeSleepResistanceMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resistance Maximum: ${actor.sleepResistanceMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeSleepResistanceMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resistance Minimum: ${actor.sleepResistanceMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeSleepResolution(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resolution: ${actor.sleepResolutionMinimum}-${actor.sleepResolutionMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeSleepResolutionMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resolution Maximum: ${actor.sleepResolutionMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeSleepResolutionMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resolution Minimum: ${actor.sleepResolutionMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeStopSpellRequirement(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Requirement: ${actor.stopSpellRequirementMinimum}-${actor.stopSpellRequirementMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeStopSpellRequirementMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Requirement Maximum: ${actor.stopSpellRequirementMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeStopSpellRequirementMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Requirement Minimum: ${actor.stopSpellRequirementMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeStopSpellResistance(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resistance: ${actor.stopSpellResistanceMinimum}-${actor.stopSpellResistanceMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeStopSpellResistanceMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resistance Maximum: ${actor.stopSpellResistanceMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeStopSpellResistanceMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resistance Minimum: ${actor.stopSpellResistanceMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeStopSpellResolution(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resolution: ${actor.stopSpellResolutionMinimum}-${actor.stopSpellResolutionMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeStopSpellResolutionMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resolution Maximum: ${actor.stopSpellResolutionMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeStopSpellResolutionMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resolution Minimum: ${actor.stopSpellResolutionMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeStrength(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Strength: ${actor.strength}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeTurn(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turn: ${actor.turn}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeTurnsSleep(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Sleep: ${actor.turnsSleep}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeTurnsSleepMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Sleep Maximum: ${actor.turnsSleepMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeTurnsSleepMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Sleep Minimum: ${actor.turnsSleepMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeTurnsSleepRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Sleep: ${actor.turnsSleep} ${actor.turnsSleepMinimum}-${actor.turnsSleepMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeTurnsStopSpell(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Stop Spell: ${actor.turnsStopSpell}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeTurnsStopSpellMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Stop Spell Maximum: ${actor.turnsStopSpellMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeTurnsStopSpellMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Stop Spell Minimum: ${actor.turnsStopSpellMinimum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawActorAttributeTurnsStopSpellRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value =
            "Turns Stop Spell: ${actor.turnsStopSpell} ${actor.turnsStopSpellMinimum}-${actor.turnsStopSpellMaximum}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }


    private fun drawActorAttributeBufferedImageThumbnail(
        bufferedImage: BufferedImage,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        val image: Image = bufferedImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH)
        val x: Int = (rectangle.centerX - (image.getWidth(null) / 2)).toInt()
        val y: Int = (rectangle.centerY - (image.getHeight(null) / 2)).toInt()
        drawImage(image, graphics2D, x, y - 60)
    }

    private fun drawActorEquipment(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        x: Int,
        y: Int,
    ): Int {
        var yPosition: Int = drawArmorAttribute(
            actor.armor, color, font, fontMetrics, graphics2D, lineHeight, x, y
        )
        yPosition = drawShieldAttribute(
            color, font, fontMetrics, graphics2D, lineHeight, actor.shield, x, yPosition
        )
        yPosition = drawWeaponAttribute(
            color, font, fontMetrics, graphics2D, lineHeight, actor.weapon, x, yPosition
        )
        return yPosition
    }

    private fun drawArmorAttribute(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        x: Int,
        y: Int,
    ): Int {
        val xPosition: Int = x + lineHeight
        var yPosition: Int = y + lineHeight
        drawString(Color.YELLOW, font, fontMetrics, graphics2D, "Armor", xPosition, yPosition)
        yPosition += lineHeight
        val drawArmorMethods = listOf(
            ::drawArmorAttributeBlocksSleep,
            ::drawArmorAttributeBlocksStopSpell,
            ::drawArmorAttributeBreatheFireReduction,
            ::drawArmorAttributeDefense,
            ::drawArmorAttributeHurtReduction,
            ::drawArmorAttributeId,
            ::drawArmorAttributeName
        )
        drawArmorMethods.forEachIndexed { _, drawArmorMethod ->
            drawArmorMethod(armor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
            yPosition += lineHeight
        }
        return yPosition
    }

    private fun drawArmorAttributeBlocksSleep(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Blocks Sleep: ${if (armor?.blocksSleep == true) "Y" else "N"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawArmorAttributeBlocksStopSpell(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Blocks Stop Spell: ${if (armor?.blocksStopSpell == true) "Y" else "N"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawArmorAttributeBreatheFireReduction(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Reduction: ${armor?.breatheFireReduction ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawArmorAttributeDefense(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Defense: ${armor?.defense ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawArmorAttributeHurtReduction(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Reduction: ${armor?.hurtReduction ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawArmorAttributeId(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Identifier: ${armor?.id ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawArmorAttributeName(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Name: ${armor?.name ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawImage(image: Image, graphics2D: Graphics2D, x: Int, y: Int) {
        graphics2D.drawImage(image, x, y, null)
    }

    private fun drawRetrieveActorIterator(
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        val message = "Retrieving actors (${actors.size}) for current turn!"

        // Set the font with the desired size
        val scaledFont = font.deriveFont(fontSize)
        graphics2D.font = scaledFont

        // Get font metrics after setting the font
        val fontMetrics = graphics2D.fontMetrics

        // Calculate the position for centered text
        val textBounds: Rectangle2D = fontMetrics.getStringBounds(message, graphics2D)
        val x: Float = (rectangle.width - textBounds.width).toFloat() / 2
        val y: Float = (rectangle.height - textBounds.height).toFloat() / 2 + fontMetrics.ascent

        // Set color and draw the string
        graphics2D.color = Color.WHITE
        graphics2D.drawString(message, x, y)
    }

    private fun drawRetrieveIndexedAction(
        actor: Actor,
        actorIndex: Int,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        drawActor(actor, actorIndex, font, fontMetrics, graphics2D, rectangle)
        actor.actions.random().ability?.let { ability: Ability<Actor, Actor> ->
            drawAbilityIdentifier(ability, font, fontMetrics, graphics2D, rectangle)
        }
        val message = "Deciding action to perform"
        val messageX = (rectangle.centerX - (fontMetrics.stringWidth(message) / 2))
        val messageY = (rectangle.centerY + 60)
        graphics2D.drawString(message, messageX.toInt(), messageY.toInt())
    }

    private fun drawRetrieveIndexedActor(
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        val message = "Retrieving next actor!"
        val x: Int = (rectangle.centerX - (fontMetrics.stringWidth(message) / 2)).toInt()
        val y: Int = (rectangle.y + 30)
        graphics2D.color = Color.WHITE
        graphics2D.drawString(message, x, y)
    }

    private fun drawRetrieveTargetActor(
        ability: Ability<Actor, Actor>,
        actor: Actor,
        actorIndex: Int,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
        targetActor: Actor,
    ) {
        drawActorAttributeBufferedImage(actor, graphics2D, rectangle)
        targetActor.bufferedImage?.let { bufferedImage: BufferedImage ->
            drawActorAttributeBufferedImageThumbnail(bufferedImage, graphics2D, rectangle)
        }
        val message =
            "${actor.name ?: actor.simpleName}:${actor.id} uses ${ability.simpleName}:${ability.id} on ${targetActor.name ?: targetActor.simpleName}:${targetActor.id}!"
        val x: Int = (rectangle.centerX - (fontMetrics.stringWidth(message) / 2)).toInt()
        val y: Int = (rectangle.centerY + 30).toInt()
        graphics2D.color = if (System.currentTimeMillis() % 2 == 0L) Color.ORANGE else Color.WHITE
        graphics2D.drawString(message, x, y)
    }

    private fun drawRetrieveTargetedActors(
        action: Action<Actor, Actor>,
        actionIndex: Int,
        actor: Actor,
        actorIndex: Int,
        actors: Collection<Actor>,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        drawActor(actor, actorIndex, font, fontMetrics, graphics2D, rectangle)

        if (targetActor == null || System.currentTimeMillis() % 4 == 0L) {
            targetActor = actors.random()
        }

        targetActor?.let { targetActor: Actor ->
            targetActor.bufferedImage?.let { bufferedImage: BufferedImage ->
                drawActorAttributeBufferedImageThumbnail(bufferedImage, graphics2D, rectangle)
            }
        }

        action.ability?.let { ability: Ability<Actor, Actor> ->
            drawAbilityIdentifier(ability, font, fontMetrics, graphics2D, rectangle)
        }

        val message = "Decide actors to target for ${action.ability?.simpleName ?: "Action"}"
        val messageX = (rectangle.centerX - (fontMetrics.stringWidth(message) / 2))
        val messageY = (rectangle.centerY + 60)
        graphics2D.color = Color.WHITE
        graphics2D.drawString(message, messageX.toInt(), messageY.toInt())
    }

    private fun drawRetrieveTargetedActorsOrder(
        action: Action<Actor, Actor>,
        actionIndex: Int,
        actor: Actor,
        actorIndex: Int,
        actors: Collection<Actor>,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        drawActor(actor, actorIndex, font, fontMetrics, graphics2D, rectangle)
        targetActor?.let { targetActor: Actor ->
            targetActor.bufferedImage?.let { bufferedImage: BufferedImage ->
                drawActorAttributeBufferedImageThumbnail(bufferedImage, graphics2D, rectangle)
            }
        }
        action.ability?.let { ability: Ability<Actor, Actor> ->
            drawAbilityIdentifier(ability, font, fontMetrics, graphics2D, rectangle)
        }
        val message = "Sort targets for ${action.ability?.simpleName ?: "Action"}"
        val messageX = (rectangle.centerX - (fontMetrics.stringWidth(message) / 2))
        val messageY = (rectangle.centerY + 60)
        graphics2D.color = Color.WHITE
        graphics2D.drawString(message, messageX.toInt(), messageY.toInt())
    }

    private fun drawRetrieveReviewable(
        actor: Actor,
        actorIndex: Int,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
        reviewable: Reviewable,
        targetActor: Actor,
    ) {
        drawActorAttributeBufferedImage(actor, graphics2D, rectangle)
        targetActor.bufferedImage?.let { bufferedImage: BufferedImage ->
            drawActorAttributeBufferedImageThumbnail(bufferedImage, graphics2D, rectangle)
        }
        drawString(
            Color.CYAN,
            font,
            fontMetrics,
            graphics2D,
            reviewable.simpleName,
            rectangle.centerX.toInt(),
            (rectangle.centerY + 20).toInt()
        )
    }

    private fun drawShieldAttribute(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        shield: Shield?,
        x: Int,
        y: Int,
    ): Int {
        val xPosition: Int = x + lineHeight
        var yPosition: Int = y + lineHeight
        drawString(Color.YELLOW, font, fontMetrics, graphics2D, "Shield", xPosition, yPosition)
        yPosition += lineHeight
        val drawShieldMethods = listOf(
            ::drawShieldAttributeDefense, ::drawShieldAttributeId, ::drawShieldAttributeName
        )
        drawShieldMethods.forEachIndexed { _, drawShieldMethod ->
            drawShieldMethod(color, font, fontMetrics, graphics2D, shield, xPosition, yPosition)
            yPosition += lineHeight
        }
        return yPosition
    }

    private fun drawShieldAttributeDefense(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        shield: Shield?,
        x: Int,
        y: Int,
    ) {
        val value = "Defense: ${shield?.defense ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawShieldAttributeId(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        shield: Shield?,
        x: Int,
        y: Int,
    ) {
        val value = "Identifier: ${shield?.id ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawShieldAttributeName(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        shield: Shield?,
        x: Int,
        y: Int,
    ) {
        val value = "Name: ${shield?.name ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawString(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        string: String,
        x: Int,
        y: Int,
    ) {
        graphics2D.color = color
        graphics2D.drawString(string, x, y)
    }

    private fun drawWeaponAttribute(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        weapon: Weapon?,
        x: Int,
        y: Int,
    ): Int {
        val xPosition: Int = x + lineHeight
        var yPosition: Int = y + lineHeight
        drawString(Color.YELLOW, font, fontMetrics, graphics2D, "Weapon", xPosition, yPosition)
        yPosition += lineHeight
        val drawWeaponMethods = listOf(
            ::drawWeaponAttributeAttack,
            ::drawWeaponAttributeId,
            ::drawWeaponAttributeName,
        )
        drawWeaponMethods.forEachIndexed { _, drawWeaponMethod ->
            drawWeaponMethod(color, font, fontMetrics, graphics2D, weapon, xPosition, yPosition)
            yPosition += lineHeight
        }
        return yPosition
    }

    private fun drawWeaponAttributeAttack(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        weapon: Weapon?,
        x: Int,
        y: Int,
    ) {
        val value = "Attack: ${weapon?.attack ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawWeaponAttributeId(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        weapon: Weapon?,
        x: Int,
        y: Int,
    ) {
        val value = "Identifier: ${weapon?.id ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawWeaponAttributeName(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        weapon: Weapon?,
        x: Int,
        y: Int,
    ) {
        val value = "Name: ${weapon?.name ?: "?"}"
        drawString(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun process() {
        when (stateType) {
            StateType.RESET -> processReset()
            StateType.RETRIEVE_ACTOR_ITERATOR -> processActorIterator()
            StateType.RETRIEVE_INDEXED_ACTOR -> processIndexedActor()
            StateType.RETRIEVE_INDEXED_ACTION -> processIndexedAction()
            StateType.RETRIEVE_TARGETED_ACTORS -> processTargetedActors()
            StateType.RETRIEVE_TARGET_ACTOR -> processTargetActor()
            StateType.RETRIEVE_REVIEWABLE -> processReviewable()
        }
    }

    private fun processActorIterator() {
        actorIterator = battleSystem.getActorIterator(actors)
        stateType = StateType.RETRIEVE_INDEXED_ACTOR
    }

    private fun processIndexedAction() {
        indexedValueAction = battleSystem.getIndexedAction(actors, indexedValueActor!!)
        stateType =
            if (indexedValueAction != null) StateType.RETRIEVE_TARGETED_ACTORS else StateType.RETRIEVE_INDEXED_ACTOR
    }

    private fun processIndexedActor() {
        indexedValueActor = battleSystem.getIndexedActor(actorIterator!!)
        stateType = if (indexedValueActor != null) StateType.RETRIEVE_INDEXED_ACTION else StateType.RESET
    }

    private fun processReset() {
        allegiances.clear()
        actors = actors.filterIndexed { index: Int, actor: Actor ->
            val checkValue: Boolean = !battleSystem.checkActorRemoval(actor, index)
            if (checkValue) {
                allegiances.add(actor.allegiance)
            }
            checkValue
        }
        if (allegiances.size > 1) {
            stateType = StateType.RETRIEVE_ACTOR_ITERATOR
        } else {
            jButtonNext.isEnabled = false
        }
    }

    private fun processReviewable() {
        reviewable = battleSystem.getReviewable(indexedValueAction!!, indexedValueActor!!, targetActor!!)
        stateType = StateType.RETRIEVE_INDEXED_ACTOR
    }

    private fun processTargetActor() {
        targetActor = battleSystem.getActionTarget(targetedActors!!)
        stateType = if (targetActor != null) StateType.RETRIEVE_REVIEWABLE else StateType.RETRIEVE_TARGET_ACTOR
        if (stateType == StateType.RETRIEVE_REVIEWABLE) {
            reviewable = battleSystem.getReviewable(indexedValueAction!!, indexedValueActor!!, targetActor!!)
        }
    }

    private fun processTargetedActors() {
        targetedActors = battleSystem.getActionTargetOrder(
            battleSystem.getActionTargets(actors, indexedValueAction!!, indexedValueActor!!), indexedValueAction!!
        )
        stateType = if (targetedActors != null) StateType.RETRIEVE_TARGET_ACTOR else StateType.RETRIEVE_INDEXED_ACTOR
    }

    private fun processTargetedActorsOrder() {
        targetedActors = battleSystem.getActionTargetOrder(targetedActors!!, indexedValueAction!!)
        stateType = if (targetedActors != null) StateType.RETRIEVE_TARGET_ACTOR else StateType.RETRIEVE_INDEXED_ACTOR
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        draw(graphics2D, font, fontMetrics, rectangle)
        g.drawImage(bufferedImage, 0, 0, null)
    }

    override fun run() {
        repaint()
    }
}
