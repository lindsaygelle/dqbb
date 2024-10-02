package dqbb

import java.awt.*
import java.awt.Image
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
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

    private val bufferedImage: BufferedImage = BufferedImage(720, 680, BufferedImage.TYPE_INT_RGB)

    private val bufferedImageField: BufferedImage = ImageIO.read(javaClass.getResource("/scenes/FIELD.png"))

    private val graphics2D: Graphics2D = bufferedImage.graphics as Graphics2D

    private val graphics2DAction: Graphics2DAction = Graphics2DAction()

    private val graphics2DActor: Graphics2DActor = Graphics2DActor()

    private val graphics2DArmor: Graphics2DArmor = Graphics2DArmor()

    private val graphics2DItem: Graphics2DItem = Graphics2DItem()

    private val graphics2DShield: Graphics2DShield = Graphics2DShield()

    private val graphics2DWeapon: Graphics2DWeapon = Graphics2DWeapon()

    private val font: Font = Font.createFont(
        Font.TRUETYPE_FONT, javaClass.getResourceAsStream("/fonts/DRAGON_QUEST.ttf")
    )

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

    private var temporaryActor: Actor? = null

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
                                    actors,
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
        graphics2D.drawImage(
            bufferedImageField,
            (rectangle.centerX - (bufferedImageField.width / 2)).toInt(),
            (rectangle.centerY - (bufferedImageField.height / 2)).toInt(),
            null,
        )
        val lineHeight = 10
        graphics2DActor.draw(
            actor, Color.WHITE, font, fontMetrics, graphics2D, lineHeight, rectangle.x, rectangle.y
        )
        var yPosition = graphics2DAction.draw(
            actor.actions, Color.WHITE, font, fontMetrics, graphics2D, lineHeight, rectangle.width, rectangle.y
        )
        yPosition = graphics2DArmor.draw(
            actor.armor, Color.WHITE, font, fontMetrics, graphics2D, lineHeight, rectangle.width, yPosition
        )
        yPosition = graphics2DItem.draw(
            Color.WHITE, font, fontMetrics, graphics2D, actor.items, lineHeight, rectangle.width, yPosition
        )
        yPosition = graphics2DShield.draw(
            Color.WHITE, font, fontMetrics, graphics2D, lineHeight, actor.shield, rectangle.width, yPosition
        )
        graphics2DWeapon.draw(
            Color.WHITE, font, fontMetrics, graphics2D, lineHeight, actor.weapon, rectangle.width, yPosition
        )
        graphics2DActor.drawBufferedImage(actor, Color.WHITE, font, fontMetrics, graphics2D, rectangle)
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
        actors: Collection<Actor>,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
        targetActor: Actor,
    ) {
        graphics2DActor.drawBufferedImage(actor, Color.WHITE, font, fontMetrics, graphics2D, rectangle)
        if (temporaryActor == null || System.currentTimeMillis() % 4 == 0L) {
            temporaryActor = actors.random()
        }

        temporaryActor?.let { temporaryActor: Actor ->
            graphics2DActor.drawBufferedImageThumbnail(
                temporaryActor, Color.WHITE, font, fontMetrics, graphics2D, rectangle
            )
        }
        val message = "${actor.name ?: actor.simpleName}:${actor.id} uses ${ability.simpleName}:${ability.id} on ${targetActor.name ?: targetActor.simpleName}:${targetActor.id}!"
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

        if (temporaryActor == null || System.currentTimeMillis() % 4 == 0L) {
            temporaryActor = actors.random()
        }

        temporaryActor?.let { temporaryActor: Actor ->
            graphics2DActor.drawBufferedImageThumbnail(
                temporaryActor, Color.WHITE, font, fontMetrics, graphics2D, rectangle
            )
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
            graphics2DActor.drawBufferedImageThumbnail(
                targetActor, Color.WHITE, font, fontMetrics, graphics2D, rectangle
            )
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
        graphics2DActor.drawBufferedImage(actor, Color.WHITE, font, fontMetrics, graphics2D, rectangle)
        graphics2DActor.drawBufferedImageThumbnail(targetActor, Color.WHITE, font, fontMetrics, graphics2D, rectangle)
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
        stateType = if (indexedValueAction != null) StateType.RETRIEVE_TARGETED_ACTORS else StateType.RETRIEVE_INDEXED_ACTOR
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
