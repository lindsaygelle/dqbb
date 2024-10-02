package dqbb

import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics2D

class GUI2DArmor {
    fun draw(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        x: Int,
        y: Int,
    ): Int {
        val xPosition: Int = x - lineHeight
        var yPosition: Int = y + lineHeight
        drawValue(Color.YELLOW, font, fontMetrics, graphics2D, "ARMOR", xPosition, yPosition)
        yPosition += lineHeight
        drawBlocksSleep(armor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawBlocksStopSpell(armor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawBreatheFireReduction(armor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawDefense(armor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtReduction(armor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawId(armor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawName(armor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        return yPosition
    }

    private fun drawBlocksSleep(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Blocks Sleep: ${if (armor?.blocksSleep == true) "Y" else "N"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawBlocksStopSpell(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Blocks Stop Spell: ${if (armor?.blocksStopSpell == true) "Y" else "N"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawBreatheFireReduction(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Reduction: ${armor?.breatheFireReduction ?: "?"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawDefense(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Defense: ${armor?.defense ?: "?"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtReduction(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Reduction: ${armor?.hurtReduction ?: "?"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawId(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Identifier: ${armor?.id ?: "?"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawName(
        armor: Armor?,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Name: ${armor?.name ?: "?"}"
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