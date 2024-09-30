package dqbb

import java.awt.*
import java.awt.Image
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
        RETRIEVE_TARGETED_ACTORS_ORDER,
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

    private val bufferedImage: BufferedImage = BufferedImage(480, 320, BufferedImage.TYPE_INT_RGB)

    private val graphics2D: Graphics2D = bufferedImage.graphics as Graphics2D

    private val fontMetrics: FontMetrics = graphics2D.fontMetrics

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

    private val jButtonNext = JButton("NEXT")

    init {
        layout = BorderLayout()
        preferredSize = Dimension(bufferedImage.width, bufferedImage.height)
        add(jButtonNext, BorderLayout.SOUTH)

        jButtonNext.addActionListener {
            process()
            jButtonNext.text = stateType.name
        }
    }

    private fun drawAbilityIdentifier(
        ability: Ability<Actor, Actor>,
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

    private fun drawActorBufferedImage(bufferedImage: BufferedImage, graphics2D: Graphics2D, rectangle: Rectangle) {
        val x = (rectangle.centerX - (bufferedImage.width / 2))
        val y = (rectangle.centerY - (bufferedImage.height / 2))
        drawImage(bufferedImage, graphics2D, x.toInt(), y.toInt())
    }

    private fun drawActorIdentifier(
        actor: Actor,
        actorIndex: Int,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        val message = "${actor?.name ?: actor.simpleName} (ID:${actor.id}) (INDEX:$actorIndex)"
        val x: Int = (rectangle.centerX - (fontMetrics.stringWidth(message) / 2)).toInt()
        val y: Int = (rectangle.y + 30)
        graphics2D.color = Color.WHITE
        graphics2D.drawString(message, x, y)
    }

    private fun drawActorBufferedImageThumbnail(
        bufferedImage: BufferedImage,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        val image: Image = bufferedImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH)
        val x: Int = (rectangle.centerX - (image.getWidth(null) / 2)).toInt()
        val y: Int = (rectangle.centerY - (image.getHeight(null) / 2)).toInt()
        drawImage(image, graphics2D, x, y - 60)
    }

    private fun drawArmor(
        armor: Armor?,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        rectangle: Rectangle,
    ) {
        val x: Int = rectangle.x + 10
        val y: Int = rectangle.y + 10
        drawArmorBlocksSleep(armor, fontMetrics, graphics2D, x, y)
        drawArmorBlocksStopSpell(armor, fontMetrics, graphics2D, x, y + lineHeight)
        drawArmorBreatheFireReduction(armor, fontMetrics, graphics2D, x, y + (lineHeight * 2))
        drawArmorDefense(armor, fontMetrics, graphics2D, x, y + (lineHeight * 3))
        drawArmorHurtReduction(armor, fontMetrics, graphics2D, x, y + (lineHeight * 4))
        drawArmorId(armor, fontMetrics, graphics2D, x, y + (lineHeight * 5))
        drawArmorName(armor, fontMetrics, graphics2D, x, y + (lineHeight * 6))
    }

    private fun drawArmorBlocksSleep(
        armor: Armor?,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "BLOCKS SLEEP: ${if (armor?.blocksSleep == true) "Y" else "N"}"
        graphics2D.drawString(value, x, y)
    }

    private fun drawArmorBlocksStopSpell(
        armor: Armor?,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "BLOCKS STOP SPELL: ${if (armor?.blocksStopSpell == true) "Y" else "N"}"
        graphics2D.drawString(value, x, y)
    }

    private fun drawArmorBreatheFireReduction(
        armor: Armor?,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "BREATHE FIRE REDUCTION: ${armor?.breatheFireReduction ?: 0}"
        graphics2D.drawString(value, x, y)
    }

    private fun drawArmorDefense(armor: Armor?, fontMetrics: FontMetrics, graphics2D: Graphics2D, x: Int, y: Int) {
        val value = "DEFENSE: ${armor?.defense ?: "?"}"
        graphics2D.drawString(value, x, y)
    }

    private fun drawArmorHurtReduction(
        armor: Armor?,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "HURT REDUCTION: ${armor?.hurtReduction ?: 0}"
        graphics2D.drawString(value, x, y)
    }

    private fun drawArmorId(armor: Armor?, fontMetrics: FontMetrics, graphics2D: Graphics2D, x: Int, y: Int) {
        val value = "ID: ${armor?.id ?: "?"}"
        graphics2D.drawString(value, x, y)
    }

    private fun drawArmorName(armor: Armor?, fontMetrics: FontMetrics, graphics2D: Graphics2D, x: Int, y: Int) {
        val value = "NAME: ${armor?.name ?: "?"}"
        graphics2D.drawString(value, x, y)
    }

    private fun drawImage(image: Image, graphics2D: Graphics2D, x: Int, y: Int) {
        graphics2D.drawImage(image, x, y, null)
    }

    private fun drawRetrieveActorIterator(graphics2D: Graphics2D, fontMetrics: FontMetrics, rectangle: Rectangle) {
        val message = "Retrieving actors (${actors.size}) for current turn!"
        val x: Int = (rectangle.centerX - (fontMetrics.stringWidth(message) / 2)).toInt()
        val y: Int = (rectangle.y + 30)
        graphics2D.color = Color.WHITE
        graphics2D.drawString(message, x, y)
    }

    private fun drawRetrieveIndexedAction(
        actor: Actor,
        actorIndex: Int,
        graphics2D: Graphics2D,
        fontMetrics: FontMetrics,
        rectangle: Rectangle,
    ) { // drawActorAttributes(actor, actorIndex, fontMetrics, graphics2D, 10, 10, 10)
        drawArmor(actor.armor, fontMetrics, graphics2D, 10, rectangle)
        actor.bufferedImage?.let { bufferedImage: BufferedImage ->
            drawActorBufferedImage(bufferedImage, graphics2D, rectangle)
        }
        actor.actions.random().ability?.let { ability: Ability<Actor, Actor> ->
            drawAbilityIdentifier(ability, fontMetrics, graphics2D, rectangle)
        }
        drawActorIdentifier(actor, actorIndex, fontMetrics, graphics2D, rectangle)

        val message = "Deciding action to perform"
        val messageX = (rectangle.centerX - (fontMetrics.stringWidth(message) / 2))
        val messageY = (rectangle.centerY + 60)
        graphics2D.drawString(message, messageX.toInt(), messageY.toInt())
    }

    private fun drawRetrieveIndexedActor(graphics2D: Graphics2D, fontMetrics: FontMetrics, rectangle: Rectangle) {
        val message = "Retrieving first actor"
        val x: Int = (rectangle.centerX - (fontMetrics.stringWidth(message) / 2)).toInt()
        val y: Int = (rectangle.y + 30)
        graphics2D.color = Color.WHITE
        graphics2D.drawString(message, x, y)
    }

    private fun drawRetrieveTargetActor(
        ability: Ability<Actor, Actor>,
        actor: Actor,
        actorIndex: Int,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
        targetActor: Actor,
    ) { // drawActorAttributes(actor, actorIndex, fontMetrics, graphics2D, 10, 10, 10)
        drawActorIdentifier(actor, actorIndex, fontMetrics, graphics2D, rectangle)
        drawArmor(actor.armor, fontMetrics, graphics2D, 10, rectangle)
        actor.bufferedImage?.let { bufferedImage: BufferedImage ->
            drawActorBufferedImage(bufferedImage, graphics2D, rectangle)
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
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) { // drawActorAttributes(actor, actorIndex, fontMetrics, graphics2D, 10, 10, 10)
        drawActorIdentifier(actor, actorIndex, fontMetrics, graphics2D, rectangle)
        drawArmor(actor.armor, fontMetrics, graphics2D, 10, rectangle)
        actor.bufferedImage?.let { bufferedImage: BufferedImage ->
            drawActorBufferedImage(bufferedImage, graphics2D, rectangle)
        }

        actors.random().bufferedImage?.let { bufferedImage: BufferedImage ->
            drawActorBufferedImageThumbnail(bufferedImage, graphics2D, rectangle)
        }
        action.ability?.let { ability: Ability<Actor, Actor> ->
            drawAbilityIdentifier(ability, fontMetrics, graphics2D, rectangle)
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
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) { // drawActorAttributes(actor, actorIndex, fontMetrics, graphics2D, 10, 10, 10)
        drawActorIdentifier(actor, actorIndex, fontMetrics, graphics2D, rectangle)
        drawArmor(actor.armor, fontMetrics, graphics2D, 10, rectangle)
        actor.bufferedImage?.let { bufferedImage: BufferedImage ->
            drawActorBufferedImage(bufferedImage, graphics2D, rectangle)
        }
        actors.random().bufferedImage?.let { bufferedImage: BufferedImage ->
            drawActorBufferedImageThumbnail(bufferedImage, graphics2D, rectangle)
        }
        action.ability?.let { ability: Ability<Actor, Actor> ->
            drawAbilityIdentifier(ability, fontMetrics, graphics2D, rectangle)
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
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
        reviewable: Reviewable,
        targetActor: Actor,
    ) { // drawActorAttributes(actor, actorIndex, fontMetrics, graphics2D, 10, 10, 10)
        drawActorIdentifier(actor, actorIndex, fontMetrics, graphics2D, rectangle)
        actor.bufferedImage?.let { bufferedImage: BufferedImage ->
            drawActorBufferedImage(bufferedImage, graphics2D, rectangle)
        }
        targetActor.bufferedImage?.let { bufferedImage: BufferedImage ->
            drawActorBufferedImageThumbnail(bufferedImage, graphics2D, rectangle)
        }
    }

    private fun process() {
        when (stateType) {
            StateType.RESET -> processReset()
            StateType.RETRIEVE_ACTOR_ITERATOR -> processActorIterator()
            StateType.RETRIEVE_INDEXED_ACTOR -> processIndexedActor()
            StateType.RETRIEVE_INDEXED_ACTION -> processIndexedAction()
            StateType.RETRIEVE_TARGETED_ACTORS -> processTargetedActors()
            StateType.RETRIEVE_TARGETED_ACTORS_ORDER -> processTargetedActorsOrder()
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
    }

    private fun processTargetedActors() {
        targetedActors = battleSystem.getActionTargets(actors, indexedValueAction!!, indexedValueActor!!)
        stateType = if (targetedActors != null) StateType.RETRIEVE_TARGETED_ACTORS_ORDER else StateType.RETRIEVE_INDEXED_ACTOR
    }

    private fun processTargetedActorsOrder() {
        targetedActors = battleSystem.getActionTargetOrder(targetedActors!!, indexedValueAction!!)
        stateType = if (targetedActors != null) StateType.RETRIEVE_TARGET_ACTOR else StateType.RETRIEVE_INDEXED_ACTOR
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        draw(graphics2D, fontMetrics, rectangle)
        g.drawImage(bufferedImage, 0, 0, null)
    }

    private fun draw(
        graphics2D: Graphics2D,
        fontMetrics: FontMetrics,
        rectangle: Rectangle,
    ) {
        graphics2D.color = Color.BLACK
        graphics2D.fillRect(0, 0, width, height)

        when (stateType) {
            StateType.RETRIEVE_ACTOR_ITERATOR -> {
                drawRetrieveActorIterator(graphics2D, fontMetrics, rectangle)
            }

            StateType.RETRIEVE_INDEXED_ACTOR -> {
                drawRetrieveIndexedActor(graphics2D, fontMetrics, rectangle)
            }

            StateType.RETRIEVE_INDEXED_ACTION -> {
                indexedValueActor?.let { indexedValueActor: IndexedValue<Actor> ->
                    drawRetrieveIndexedAction(
                        indexedValueActor.value, indexedValueActor.index, graphics2D, fontMetrics, rectangle
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
                            fontMetrics,
                            graphics2D,
                            rectangle
                        )
                    }
                }
            }

            StateType.RETRIEVE_TARGETED_ACTORS_ORDER -> {
                indexedValueActor?.let { indexedValueActor: IndexedValue<Actor> ->
                    indexedValueAction?.let { indexedValueAction: IndexedValue<Action<Actor, Actor>> ->
                        drawRetrieveTargetedActorsOrder(
                            indexedValueAction.value,
                            indexedValueAction.index,
                            indexedValueActor.value,
                            indexedValueActor.index,
                            actors,
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

    private fun drawActor(
        actor: Actor,
        actorIndex: Int,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        index: Int,
    ) {

        val actorImageX = (width - actor.bufferedImageWidth) / 2
        val actorImageY = (height - actor.bufferedImageHeight) / 2
        graphics2D.drawImage(actor.bufferedImage, actorImageX, actorImageY, null)

        // Draw actor information
        val lineHeight = 20

        drawActorArmor(
            actor.armor, fontMetrics, graphics2D, lineHeight, width - 20, 20
        ) // drawActorAttributes(actor, actorIndex, fontMetrics, graphics2D, lineHeight, 20, 20)
        drawActorShield(fontMetrics, graphics2D, lineHeight, actor.shield, width - 20, height - 20)
        drawActorWeapon(fontMetrics, graphics2D, lineHeight, actor.weapon, 20, height - 20)
    }

    private fun drawActorArmor(
        armor: Armor?, fontMetrics: FontMetrics, graphics2D: Graphics2D, lineHeight: Int, x: Int, y: Int,
    ) {
        var yPosition = y
        graphics2D.color = Color.WHITE

        var line = "ARMOR"
        graphics2D.color = Color.YELLOW
        graphics2D.drawString(line, (x - fontMetrics.stringWidth(line)), yPosition)
        yPosition += lineHeight

        graphics2D.color = Color.WHITE

        line = "BREATHE FIRE ${armor?.breatheFireReduction ?: 0}"
        graphics2D.drawString(line, (x - fontMetrics.stringWidth(line)), yPosition)
        yPosition += lineHeight

        line = "BLOCKS SLEEP ${if (armor?.blocksSleep == true) "Y" else "N"}"
        graphics2D.drawString(line, (x - fontMetrics.stringWidth(line)), yPosition)
        yPosition += lineHeight

        line = "BLOCKS STOP SPELL ${if (armor?.blocksStopSpell == true) "Y" else "N"}"
        graphics2D.drawString(line, (x - fontMetrics.stringWidth(line)), yPosition)
        yPosition += lineHeight

        line = "DEFENSE ${armor?.defense ?: 0}"
        graphics2D.drawString(line, (x - fontMetrics.stringWidth(line)), yPosition)
        yPosition += lineHeight

        line = "HURT REDUCTION ${armor?.hurtReduction ?: 0}"
        graphics2D.drawString(line, (x - fontMetrics.stringWidth(line)), yPosition)
        yPosition += lineHeight

        line = "ID ${armor?.id ?: "?"}"
        graphics2D.drawString(line, (x - fontMetrics.stringWidth(line)), yPosition)
        yPosition += lineHeight

        line = "NAME ${armor?.name ?: "?"}"
        graphics2D.drawString(line, (x - fontMetrics.stringWidth(line)), yPosition)
    }

    private fun drawActorAttributes(
        actor: Actor,
        actorIndex: Int,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        x: Int,
        y: Int,
    ) {
        var yPosition = y

        graphics2D.color = Color.YELLOW
        graphics2D.drawString("ATTRIBUTES", x, yPosition)
        yPosition += lineHeight

        graphics2D.color = Color.WHITE

        graphics2D.drawString("ID: ${actor.id}", x, yPosition)
        yPosition += lineHeight
        graphics2D.drawString("ACTIONS: ${actor.actions.size}", x, yPosition)
        yPosition += lineHeight
        graphics2D.drawString("ALLEGIANCE: ${actor.allegiance}", x, yPosition)
        yPosition += lineHeight
        graphics2D.drawString("AGILITY: ${actor.agility}", x, yPosition)
        yPosition += lineHeight
        graphics2D.drawString("HIT_POINTS: ${actor.hitPoints}/${actor.hitPointsMaximum}", x, yPosition)
        yPosition += lineHeight
        graphics2D.drawString("INDEX: $actorIndex", x, yPosition)
        yPosition += lineHeight
        graphics2D.drawString("MAGIC_POINTS: ${actor.magicPoints}/${actor.magicPointsMaximum}", x, yPosition)
        yPosition += lineHeight
        graphics2D.drawString("SLEEP: ${if (actor.turnsSleep > 0) "Y" else "N"}", x, yPosition)
        yPosition += lineHeight
        graphics2D.drawString("STRENGTH: ${actor.strength}", x, yPosition)
        yPosition += lineHeight
    }

    private fun drawActorShield(
        fontMetrics: FontMetrics, graphics2D: Graphics2D, lineHeight: Int, shield: Shield?, x: Int, y: Int,
    ) {
        graphics2D.color = Color.WHITE
        var yPosition = y

        var line = "ID ${shield?.id}"
        graphics2D.drawString(line, (x - fontMetrics.stringWidth(line)), yPosition)
        yPosition -= lineHeight

        line = "DEFENSE ${shield?.defense ?: 0}"
        graphics2D.drawString(line, (x - fontMetrics.stringWidth(line)), yPosition)
        yPosition -= lineHeight

        line = "SHIELD"
        graphics2D.color = Color.YELLOW
        graphics2D.drawString(line, (x - fontMetrics.stringWidth(line)), yPosition)
        yPosition -= lineHeight
    }

    private fun drawActorWeapon(
        fontMetrics: FontMetrics, graphics2D: Graphics2D, lineHeight: Int, weapon: Weapon?, x: Int, y: Int,
    ) {
        graphics2D.color = Color.WHITE
        var yPosition = y

        var line = "ID ${weapon?.id}"
        graphics2D.drawString(line, x, yPosition)
        yPosition -= lineHeight

        line = "ATTACK ${weapon?.attack ?: 0}"
        graphics2D.drawString(line, x, yPosition)
        yPosition -= lineHeight

        line = "WEAPON"
        graphics2D.color = Color.YELLOW
        graphics2D.drawString(line, x, yPosition)
        yPosition -= lineHeight
    }

    override fun run() {
        repaint()
    }
}