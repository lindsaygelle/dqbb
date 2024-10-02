package dqbb

import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics2D

class GUI2DAction {
    fun draw(
        actions: Collection<Action<Actor, Actor>>, color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        x: Int,
        y: Int,
    ): Int {
        val xPosition: Int = x - lineHeight
        var yPosition: Int = y + lineHeight
        drawValue(Color.YELLOW, font, fontMetrics, graphics2D, "ABILITIES", xPosition, yPosition)
        yPosition += lineHeight
        actions.forEachIndexed { index: Int, action: Action<Actor, Actor> ->
            action.ability?.let { ability: Ability<Actor, Actor> ->
                drawValue(color, font, fontMetrics, graphics2D, "${ability.simpleName}-${index}", xPosition, yPosition)
                yPosition += lineHeight
            }
        }
        return yPosition
    }

    private fun drawValue(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        string: String,
        x: Int,
        y: Int,
    ) {
        val xPosition = x - fontMetrics.stringWidth(string)
        graphics2D.color = color
        graphics2D.drawString(string, xPosition, y)
    }
}