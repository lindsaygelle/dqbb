package dqbb

import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.JButton

class PanelBattle(private var actors: Collection<Actor>) : Panel(),
        Runnable {
    private enum class StateType {
        RESET,
        RETRIEVE_ACTOR_ITERATOR,
        RETRIEVE_ACTOR_TARGETS,
        RETRIEVE_INDEXED_ACTION,
        RETRIEVE_INDEXED_ACTOR,
        SORT_ACTOR_TARGETS,
        USE_INDEXED_ACTION,
    }

    private val allegiances: MutableSet<Int> = mutableSetOf()
    private var actorIterator: Iterator<IndexedValue<Actor>>? = null
    private var battleSystem: BattleSystem = BattleSystem()
    private var bufferedImage: BufferedImage = BufferedImage(480, 320, BufferedImage.TYPE_INT_RGB)
    private var indexedValueAction: IndexedValue<Action<Actor, Actor>>? = null
    private var indexedValueActor: IndexedValue<Actor>? = null
    private var stateType: StateType = StateType.RESET
    private var targetedActors: Collection<Actor>? = null
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
            drawActor(it.value, it.index, graphics2D.fontMetrics, graphics2D, it.index)
            graphics2D.dispose()
        }
    }

    private fun drawAbility(simpleName: String) {
        if (indexedValueAction != null) {
            val abilitySimpleName = indexedValueAction!!.value.simpleName

        }
    }

    private fun drawActorActionSleep(actor: Actor, graphics2D: Graphics2D) {

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

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        draw(bufferedImage.graphics as Graphics2D)
        g.drawImage(bufferedImage, 0, 0, null)
    }


    private fun processNextStep() {
        when (stateType) {
            StateType.RETRIEVE_ACTOR_ITERATOR -> {
                actorIterator = battleSystem.getActors(actors)
                stateType = StateType.RETRIEVE_INDEXED_ACTOR
                jButtonNext.text = stateType.name
            }

            StateType.RETRIEVE_INDEXED_ACTOR -> {
                if (actorIterator?.hasNext() == true) {
                    indexedValueActor = battleSystem.getActor(actorIterator!!)
                    stateType = StateType.RETRIEVE_INDEXED_ACTION
                    jButtonNext.text = stateType.name
                } else {
                    stateType = StateType.RESET
                    jButtonNext.text = stateType.name
                }
            }

            StateType.RETRIEVE_INDEXED_ACTION -> {
                indexedValueActor?.let { indexedValueActor ->
                    indexedValueAction =
                        battleSystem.getAction(actors, indexedValueActor)
                    if (indexedValueAction != null) {
                        stateType = StateType.RETRIEVE_ACTOR_TARGETS
                        jButtonNext.text = stateType.name
                    } else {
                        println("cannot find an action for this actor!!!!")
                        stateType = StateType.RETRIEVE_INDEXED_ACTOR
                        jButtonNext.text = stateType.name
                    }
                }
            }

            StateType.RETRIEVE_ACTOR_TARGETS -> {
                indexedValueAction?.let { indexedValueAction ->
                    indexedValueActor?.let { indexedValueActor ->
                        targetedActors = battleSystem.getActionTargets(actors, indexedValueAction, indexedValueActor)
                        stateType = StateType.SORT_ACTOR_TARGETS
                        jButtonNext.text = stateType.name
                    }
                }
            }

            StateType.SORT_ACTOR_TARGETS -> {
                indexedValueAction?.let { indexedValueAction ->
                    indexedValueActor?.let { indexedValueActor ->
                        targetedActors?.let {
                            targetedActors =
                                battleSystem.getActionTargetOrder(it, indexedValueAction, indexedValueActor)
                            stateType = StateType.USE_INDEXED_ACTION
                            jButtonNext.text = stateType.name
                        }
                    }
                }
            }

            StateType.USE_INDEXED_ACTION -> {
                indexedValueAction?.let { indexedValueAction ->
                    indexedValueActor?.let { indexedValueActor ->
                        targetedActors?.let {
                            val reviewable: Reviewable? =
                                indexedValueAction.value.ability?.use(indexedValueActor.value, it.first())
                            println(reviewable)
                            stateType = StateType.RETRIEVE_INDEXED_ACTOR
                            jButtonNext.text = stateType.name
                        }
                    }
                }
            }

            StateType.RESET -> {
                allegiances.clear()
                actors = actors.filterIndexed { index: Int, actor: Actor ->
                    val checkValue: Boolean = !battleSystem.checkActorRemoval(actor, index)
                    if (checkValue) {
                        allegiances.add(actor.allegiance)
                    }
                    checkValue
                }
                if (allegiances.size > 1) {
                    indexedValueAction = null
                    indexedValueActor = null
                    stateType = StateType.RETRIEVE_ACTOR_ITERATOR
                    jButtonNext.text = "Start Next Round"
                } else {
                    jButtonNext.text = "Battle Ended"
                    jButtonNext.isEnabled = false
                }
            }
        }
    }

    override fun run() {
        repaint()
    }
}