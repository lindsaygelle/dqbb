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
        draw(bufferedImage.graphics as Graphics2D)
        g.drawImage(bufferedImage, 0, 0, null)
    }

    /*
    private fun draw(graphics2D: Graphics2D) {
        indexedValueActor?.let {
            graphics2D.color = Color.BLACK
            graphics2D.fillRect(0, 0, width, height)
            drawActor(it.value, it.index, graphics2D.fontMetrics, graphics2D, it.index)
            graphics2D.dispose()
        }
    }

     */

    private fun draw(graphics2D: Graphics2D) {
        // Draw the background for all states
        graphics2D.color = Color.BLACK
        graphics2D.fillRect(0, 0, width, height)

        when (stateType) {
            StateType.RETRIEVE_ACTOR_ITERATOR -> {
                // Display text saying "Getting actors"
                graphics2D.color = Color.WHITE
                graphics2D.drawString("Getting actors...", width / 2 - 50, height / 2)
            }
            StateType.RETRIEVE_INDEXED_ACTOR -> {
                graphics2D.color = Color.WHITE
                graphics2D.drawString("Retrieving next actors...", width / 2 - 50, height / 2)
            }
            StateType.RETRIEVE_INDEXED_ACTION -> {
                indexedValueActor?.let {
                    // Draw text "Deciding..." above the actor's head
                    val actorImageX = (width - it.value.bufferedImageWidth) / 2
                    val actorImageY = (height - it.value.bufferedImageHeight) / 2
                    drawActor(it.value, it.index, graphics2D.fontMetrics, graphics2D, it.index)

                    graphics2D.color = Color.WHITE
                    graphics2D.drawString("Deciding...", actorImageX, actorImageY - 20)
                }
            }
            StateType.RETRIEVE_TARGETED_ACTORS -> {
                indexedValueActor?.let {
                    // Draw the actor as before
                    val actorImageX = (width - it.value.bufferedImageWidth) / 2
                    val actorImageY = (height - it.value.bufferedImageHeight) / 2
                    drawActor(it.value, it.index, graphics2D.fontMetrics, graphics2D, it.index)

                    // Randomly select an actor every 2 seconds and draw its sprite in a small box above the main actor
                    val randomActor = actors.random()
                        // Define the box size and position for the random actor sprite
                        val boxWidth = 25
                        val boxHeight = 25
                        val boxX = actorImageX
                        val boxY = actorImageY - boxHeight - 10

                        // Draw the box
                        graphics2D.color = Color.YELLOW
                        graphics2D.drawRect(boxX, boxY, boxWidth, boxHeight)

                        // Draw the random actor's sprite inside the box, scaling if necessary
                        val randomActorImage = randomActor.bufferedImage
                        val scaledImage = randomActorImage?.getScaledInstance(boxWidth, boxHeight, Image.SCALE_SMOOTH)
                        graphics2D.drawImage(scaledImage, boxX, boxY, null)

                }
            }

            StateType.RETRIEVE_TARGETED_ACTORS_ORDER -> {
                indexedValueActor?.let {
                    // Reuse the animation for targeted actors
                    val actorImageX = (width - it.value.bufferedImageWidth) / 2
                    val actorImageY = (height - it.value.bufferedImageHeight) / 2
                    drawActor(it.value, it.index, graphics2D.fontMetrics, graphics2D, it.index)

                    val randomActor = actors.random()
                    val shouldRedraw = System.currentTimeMillis() / 2000 % 2 == 0L
                    if (shouldRedraw) {
                        graphics2D.color = Color.YELLOW
                        graphics2D.drawString("Reordering targets: ${randomActor.id}", actorImageX, actorImageY - 20)
                    }
                }
            }
            else -> {
                // Default draw the actor in other states
                indexedValueActor?.let {
                    drawActor(it.value, it.index, graphics2D.fontMetrics, graphics2D, it.index)
                }
            }
        }

        graphics2D.dispose() // Cleanup after drawing
    }


    private fun drawActor(actor: Actor, actorIndex: Int, fontMetrics: FontMetrics, graphics2D: Graphics2D, index: Int) {

        val actorImageX = (width - actor.bufferedImageWidth) / 2
        val actorImageY = (height - actor.bufferedImageHeight) / 2
        graphics2D.drawImage(actor.bufferedImage, actorImageX, actorImageY, null)

        // Draw actor information
        val lineHeight = 20

        drawActorArmor(actor.armor, fontMetrics, graphics2D, lineHeight, width - 20, 20)
        drawActorAttributes(actor, actorIndex, fontMetrics, graphics2D, lineHeight, 20, 20)
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