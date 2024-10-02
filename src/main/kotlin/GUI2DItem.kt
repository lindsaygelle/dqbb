package dqbb

import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics2D

class GUI2DItem {
    fun draw(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        items: Map<ItemName, Int>,
        lineHeight: Int,
        x: Int,
        y: Int,
    ): Int {
        val xPosition: Int = x - lineHeight
        var yPosition: Int = y + lineHeight
        drawValue(Color.YELLOW, font, fontMetrics, graphics2D, "ITEMS", xPosition, yPosition)
        yPosition += lineHeight
        drawHerbs(color, font, fontMetrics, graphics2D, items, xPosition, yPosition)
        yPosition += lineHeight
        drawMagicPotions(color, font, fontMetrics, graphics2D, items, xPosition, yPosition)
        yPosition += lineHeight
        return yPosition
    }

    private fun drawHerbs(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        items: Map<ItemName, Int>,
        x: Int,
        y: Int,
    ) {
        val value = "Herbs: ${items.getOrDefault(ItemName.HERB, 0)}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawMagicPotions(
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        items: Map<ItemName, Int>,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potions: ${items.getOrDefault(ItemName.MAGIC_POTION, 0)}"
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