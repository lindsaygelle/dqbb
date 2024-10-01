package dqbb

import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics2D

class Graphics2DShield {
    fun draw(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        shield: Shield?,
        x: Int,
        y: Int,
    ): Int {
        val xPosition: Int = x - lineHeight
        var yPosition: Int = y + lineHeight
        drawValue(Color.YELLOW, font, fontMetrics, graphics2D, "SHIELD", xPosition, yPosition)
        yPosition += lineHeight
        drawDefense(color, font, fontMetrics, graphics2D, shield, xPosition, yPosition)
        yPosition += lineHeight
        drawId(color, font, fontMetrics, graphics2D, shield, xPosition, yPosition)
        yPosition += lineHeight
        drawName(color, font, fontMetrics, graphics2D, shield, xPosition, yPosition)
        yPosition += lineHeight
        return yPosition
    }

    private fun drawDefense(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        shield: Shield?,
        x: Int,
        y: Int,
    ) {
        val value = "Defense: ${shield?.defense ?: "?"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawId(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        shield: Shield?,
        x: Int,
        y: Int,
    ) {
        val value = "Identifier: ${shield?.id ?: "?"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawName(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        shield: Shield?,
        x: Int,
        y: Int,
    ) {
        val value = "Name: ${shield?.name ?: "?"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
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