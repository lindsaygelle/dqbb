package dqbb

import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics2D

class GUI2DWeapon {
    fun draw(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        weapon: Weapon?,
        x: Int,
        y: Int,
    ): Int {
        val xPosition: Int = x - lineHeight
        var yPosition: Int = y + lineHeight
        drawValue(Color.YELLOW, font, fontMetrics, graphics2D, "WEAPON", xPosition, yPosition)
        yPosition += lineHeight
        drawAttack(color, font, fontMetrics, graphics2D, weapon, xPosition, yPosition)
        yPosition += lineHeight
        drawId(color, font, fontMetrics, graphics2D, weapon, xPosition, yPosition)
        yPosition += lineHeight
        drawName(color, font, fontMetrics, graphics2D, weapon, xPosition, yPosition)
        yPosition += lineHeight
        return yPosition
    }

    private fun drawAttack(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        weapon: Weapon?,
        x: Int,
        y: Int,
    ) {
        val value = "Attack: ${weapon?.attack ?: "?"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawId(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        weapon: Weapon?,
        x: Int,
        y: Int,
    ) {
        val value = "Identifier: ${weapon?.id ?: "?"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawName(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        weapon: Weapon?,
        x: Int,
        y: Int,
    ) {
        val value = "Name: ${weapon?.name ?: "?"}"
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