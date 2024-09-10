package dqbb

import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.JButton

class PanelBattle(private val actors: MutableCollection<Actor>) : Panel(),
    Runnable {
    private val allegiances: MutableSet<Int> = mutableSetOf()
    private var actorIterator: Iterator<IndexedValue<Actor>>? = null
    private var battleSystem: BattleSystem = BattleSystem()
    private var bufferedImage: BufferedImage = BufferedImage(480, 320, BufferedImage.TYPE_INT_RGB)
    private var indexedValueAction: IndexedValue<Action<Actor, Actor>>? = null
    private var targetedActors: Collection<Actor>? = null
    private var indexedValueActor: IndexedValue<Actor>? = null
    private val jButtonNext = JButton("NEXT")

    init {
        layout = BorderLayout()
        preferredSize = Dimension(bufferedImage.width, bufferedImage.height)
        add(jButtonNext, BorderLayout.SOUTH)

        jButtonNext.addActionListener {
            processNextStep()
        }
    }

    private fun draw(graphics2D: Graphics2D) {
        indexedValueActor?.let {
            graphics2D.color = Color.BLACK
            graphics2D.fillRect(0, 0, width, height)
            drawActor(it.value, graphics2D.fontMetrics, graphics2D, it.index)
            graphics2D.dispose()
        }
    }

    private fun drawActor(actor: Actor, fontMetrics: FontMetrics, graphics2D: Graphics2D, index: Int) {

        val actorImageX = (width - actor.bufferedImageWidth) / 2
        val actorImageY = (height - actor.bufferedImageHeight) / 2
        graphics2D.drawImage(actor.bufferedImage, actorImageX, actorImageY, null)

        // Draw actor information
        val lineHeight = 20

        drawActorArmor(actor.armor, fontMetrics, graphics2D, lineHeight, width - 20, 20)
        drawActorAttributes(actor, fontMetrics, graphics2D, lineHeight, 20, 20)
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
        actor: Actor, fontMetrics: FontMetrics, graphics2D: Graphics2D, lineHeight: Int, x: Int, y: Int,
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
        graphics2D.drawString("INDEX: ${actor.index}", x, yPosition)
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

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        draw(bufferedImage.graphics as Graphics2D)
        g.drawImage(bufferedImage, 0, 0, null)
    }

    private enum class BattleState {
        SETUP,
        SELECT_ACTOR,
        SELECT_ACTION,
        SELECT_ACTION_TARGET,
        EXECUTE_ACTION,
        ORDER_TARGETS,
        CLEANUP
    }


    private var currentState: BattleState = BattleState.SETUP
    private fun processNextStep() {
        when (currentState) {
            BattleState.SETUP -> {
                actorIterator = battleSystem.getActors(actors)
                currentState = BattleState.SELECT_ACTOR
                jButtonNext.text = currentState.name
            }

            BattleState.SELECT_ACTOR -> {
                if (actorIterator?.hasNext() == true) {
                    indexedValueActor = battleSystem.getActor(actorIterator!!)
                    currentState = BattleState.SELECT_ACTION
                    jButtonNext.text = currentState.name
                } else {
                    currentState = BattleState.CLEANUP
                    jButtonNext.text = currentState.name
                }
            }

            BattleState.SELECT_ACTION -> {
                indexedValueActor?.let { actor ->
                    indexedValueAction = battleSystem.getAction(actor.value, actors, actor.index)
                    // need to handle no valid action
                    if (indexedValueAction != null) {
                        currentState = BattleState.SELECT_ACTION_TARGET
                        jButtonNext.text = currentState.name
                    } else {
                        println("cannot find an action for this actor!!!!")
                        currentState = BattleState.SELECT_ACTOR
                        jButtonNext.text = currentState.name
                    }
                }
            }

            BattleState.SELECT_ACTION_TARGET -> {
                indexedValueAction?.let { indexedValueAction ->
                    val action: Action<Actor, Actor> = indexedValueAction.value
                    val actionIndex: Int = indexedValueAction.index
                    indexedValueActor?.let { indexedValueActor ->
                        val actor: Actor = indexedValueActor.value
                        val actorIndex: Int = indexedValueActor.index
                        targetedActors = battleSystem.getActionTargets(action, actionIndex, actor, actors, actorIndex)
                        currentState = BattleState.ORDER_TARGETS
                        jButtonNext.text = currentState.name
                    }
                }
            }

            BattleState.ORDER_TARGETS -> {
                indexedValueAction?.let { indexedValueAction ->
                    val action: Action<Actor, Actor> = indexedValueAction.value
                    val actionIndex: Int = indexedValueAction.index
                    indexedValueActor?.let { indexedValueActor ->
                        val actor: Actor = indexedValueActor.value
                        val actorIndex: Int = indexedValueActor.index
                        targetedActors?.let {
                            targetedActors =
                                battleSystem.getActionTargetOrder(action, actionIndex, actor, it, actorIndex)
                            currentState = BattleState.EXECUTE_ACTION
                            jButtonNext.text = currentState.name
                        }
                    }
                }
            }

            BattleState.EXECUTE_ACTION -> {
                indexedValueAction?.let { indexedValueAction ->
                    val action: Action<Actor, Actor> = indexedValueAction.value
                    indexedValueActor?.let { indexedValueActor ->
                        val actor: Actor = indexedValueActor.value
                        val actorIndex: Int = indexedValueActor.index
                        targetedActors?.let {
                            graphics.drawString(action.ability?.simpleName ?: "?", width / 2, 20)
                            action.ability?.use(actor, it.first())
                            currentState = BattleState.SELECT_ACTOR
                            jButtonNext.text = currentState.name
                        }
                    }
                }
            }

            BattleState.CLEANUP -> {
                allegiances.clear()
                actors.removeAll { actor: Actor ->
                    val remove: Boolean = battleSystem.checkActorRemoval(actor, actor.index ?: -1)
                    if (!remove) {
                        allegiances.add(actor.allegiance)
                    }
                    remove
                }
                if (allegiances.size > 1) {
                    indexedValueAction = null
                    indexedValueActor = null
                    currentState = BattleState.SETUP
                    jButtonNext.text = "Start Next Round"
                } else {
                    jButtonNext.text = "Battle Ended"
                    jButtonNext.isEnabled = false
                }
            }
        }
        repaint()
    }

    override fun run() {
        repaint()
    }
}