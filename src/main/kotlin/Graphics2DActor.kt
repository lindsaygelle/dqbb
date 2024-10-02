package dqbb

import java.awt.*
import java.awt.Image
import java.awt.image.BufferedImage

class Graphics2DActor {
    fun draw(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        lineHeight: Int,
        x: Int,
        y: Int,
    ): Int {
        val xPosition: Int = x + lineHeight
        var yPosition: Int = y + lineHeight
        drawValue(Color.YELLOW, font, fontMetrics, graphics2D, "ATTRIBUTES", xPosition, yPosition)
        yPosition += lineHeight
        drawActions(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawAgility(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawAllegiance(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawBreatheFireRangeMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawBreatheFireRangeMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawBreathe(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawBreatheFireScale(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawBreatheFireShift(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawEvasionRequirement(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawEvasionRequirementMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawEvasionRequirementMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawExcellentAttackRequirement(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawExcellentAttackRequirementMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawExcellentAttackRequirementMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHealMoreScale(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHealMoreShift(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHealRange(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHealRangeMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHealRangeMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHerbCount(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHealScale(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHealShift(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHerbRange(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHerbRangeMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHerbRangeMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHerbScale(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHerbShift(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHitPoints(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHitPointsMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHitPointsPercentage(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtMoreScale(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtMoreShift(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtRange(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtRangeMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtRangeMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtRequirement(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtRequirementMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtRequirementMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtResistance(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtResistanceMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtResistanceMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtScale(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawHurtShift(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawIsRunning(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawMagicPoints(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawMagicPointsMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawMagicPointsPercentage(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawMagicPotionCount(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawMagicPotionRange(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawMagicPotionRangeMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawMagicPotionRangeMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawMagicPotionScale(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawMagicPotionShift(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawName(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawRunRange(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawRunRangeMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawRunRangeMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawRunShift(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawSleepRequirement(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawSleepRequirementMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawSleepRequirementMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawSleepResistance(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawSleepResistanceMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawSleepResistanceMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawSleepResolution(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawSleepResolutionMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawSleepResolutionMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawStopSpellRequirement(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawStopSpellRequirementMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawStopSpellRequirementMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawStopSpellResistance(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawStopSpellResistanceMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawStopSpellResistanceMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawStopSpellResolution(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawStopSpellResolutionMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawStopSpellResolutionMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawStrength(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawTurn(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawTurnsSleep(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawTurnsSleepMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawTurnsSleepMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight //drawTurnsSleepRange(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        //yPosition += lineHeight
        drawTurnsStopSpell(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawTurnsStopSpellMaximum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight
        drawTurnsStopSpellMinimum(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        yPosition += lineHeight //drawTurnsStopSpellRange(actor, color, font, fontMetrics, graphics2D, xPosition, yPosition)
        //yPosition += lineHeight
        return yPosition
    }

    private fun drawActions(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Actions: ${actor.actions.size}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawAgility(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Agility: ${actor.agility}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawAllegiance(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Allegiance: ${actor.allegiance}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawBreathe(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Range: ${actor.breatheFireRangeMinimum}-${actor.breatheFireRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawBreatheFireRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Range Maximum: ${actor.breatheFireRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }


    private fun drawBreatheFireRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Range Minimum: ${actor.breatheFireRangeMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawBreatheFireScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Scale: ${actor.breatheFireScale}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawBreatheFireShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Breathe Fire Shift: ${actor.breatheFireShift}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    fun drawBufferedImage(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        actor.bufferedImage?.let { bufferedImage: BufferedImage ->
            val x = (rectangle.centerX - (bufferedImage.width / 2))
            val y = (rectangle.centerY - (bufferedImage.height / 2))
            graphics2D.drawImage(bufferedImage, x.toInt(), y.toInt(), null)
        }
    }

    fun drawBufferedImageThumbnail(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        rectangle: Rectangle,
    ) {
        actor.bufferedImage?.let { bufferedImage: BufferedImage ->
            val image: Image = bufferedImage.getScaledInstance(30, 30, Image.SCALE_DEFAULT)
            val imageWidth: Int = image.getWidth(null)
            val imageHeight: Int = image.getHeight(null)
            val x: Double = rectangle.centerX - (imageWidth / 2)
            val y: Double = rectangle.centerY - (imageHeight / 2)
            graphics2D.drawImage(image, x.toInt(), y.toInt() - imageHeight, null)
        }
    }

    private fun drawEvasionRequirement(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Evasion Requirement: ${actor.evasionRequirementMinimum}-${actor.evasionRequirementMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawEvasionRequirementMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Evasion Requirement Maximum: ${actor.evasionRequirementMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawEvasionRequirementMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Evasion Requirement Minimum: ${actor.evasionRequirementMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawExcellentAttackRequirement(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Excellent Attack Requirement: ${actor.excellentAttackRequirementMinimum}-${actor.excellentAttackRequirementMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawExcellentAttackRequirementMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Excellent Attack Requirement Maximum: ${actor.excellentAttackRequirementMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawExcellentAttackRequirementMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Excellent Attack Requirement Minimum: ${actor.excellentAttackRequirementMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHealMoreScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal More Scale: ${actor.healMoreScale}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHealMoreShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal More Shift: ${actor.healMoreShift}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHealRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal Range: ${actor.healRangeMinimum}-${actor.healRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHealRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal Range Maximum: ${actor.healRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHealRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal Range Minimum: ${actor.healRangeMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHerbCount(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Count: ${actor.herbCount}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHealScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal Scale: ${actor.healScale}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHealShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Heal Shift: ${actor.healShift}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHerbRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Range: ${actor.herbRangeMinimum}-${actor.herbRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHerbRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Range Maximum: ${actor.herbRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHerbRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Range Minimum: ${actor.herbRangeMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHerbScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Scale: ${actor.herbScale}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHerbShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Herb Shift: ${actor.herbShift}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHitPoints(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hit Points: ${actor.hitPoints}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHitPointsMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hit Points Maximum: ${actor.hitPointsMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHitPointsPercentage(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hit Points Percentage: ${actor.hitPointsPercentage}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtMoreScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt More Scale: ${actor.hurtMoreScale}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtMoreShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt More Shift: ${actor.hurtMoreShift}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Range: ${actor.hurtRangeMinimum}-${actor.hurtRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Range Maximum: ${actor.hurtRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Range Minimum: ${actor.hurtRangeMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtRequirement(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Requirement: ${actor.hurtRequirementMinimum}-${actor.hurtRequirementMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtRequirementMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Requirement Maximum: ${actor.hurtRequirementMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtRequirementMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Requirement Minimum: ${actor.hurtRequirementMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtResistance(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Resistance: ${actor.hurtResistanceMinimum}-${actor.hurtResistanceMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtResistanceMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Resistance Maximum: ${actor.hurtResistanceMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtResistanceMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Resistance Minimum: ${actor.hurtResistanceMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Scale: ${actor.hurtScale}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawHurtShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Hurt Shift: ${actor.hurtShift}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawIsRunning(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Is Running: ${if (actor.isRunning) "Y" else "N"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawMagicPoints(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Points: ${actor.magicPoints}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawMagicPointsMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Points Maximum: ${actor.magicPointsMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawMagicPointsPercentage(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Points Percentage: ${actor.magicPointsPercentage}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawMagicPotionCount(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion Count: ${actor.magicPotionCount}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawMagicPotionRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion: ${actor.magicPotionRangeMaximum}-${actor.magicPotionRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawMagicPotionRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion Range Maximum: ${actor.magicPotionRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawMagicPotionRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion Range Minimum: ${actor.magicPotionRangeMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawMagicPotionScale(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion Scale: ${actor.magicPotionScale}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawMagicPotionShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Magic Potion Shift: ${actor.magicPotionShift}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawName(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Name: ${actor.name ?: "?"}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawRunRange(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Run Range: ${actor.runRangeMinimum}-${actor.runRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawRunRangeMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Run Range Maximum: ${actor.runRangeMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawRunRangeMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Run Range Minimum: ${actor.runRangeMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawRunShift(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Run Shift: ${actor.runShift}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawSleepRequirement(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Requirement: ${actor.sleepRequirementMinimum}-${actor.sleepRequirementMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawSleepRequirementMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Requirement Maximum: ${actor.sleepRequirementMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawSleepRequirementMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Requirement Minimum: ${actor.sleepRequirementMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawSleepResistance(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resistance: ${actor.sleepResistanceMinimum}-${actor.sleepResistanceMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawSleepResistanceMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resistance Maximum: ${actor.sleepResistanceMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawSleepResistanceMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resistance Minimum: ${actor.sleepResistanceMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawSleepResolution(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resolution: ${actor.sleepResolutionMinimum}-${actor.sleepResolutionMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawSleepResolutionMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resolution Maximum: ${actor.sleepResolutionMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawSleepResolutionMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Sleep Resolution Minimum: ${actor.sleepResolutionMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawStopSpellRequirement(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Requirement: ${actor.stopSpellRequirementMinimum}-${actor.stopSpellRequirementMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawStopSpellRequirementMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Requirement Maximum: ${actor.stopSpellRequirementMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawStopSpellRequirementMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Requirement Minimum: ${actor.stopSpellRequirementMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawStopSpellResistance(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resistance: ${actor.stopSpellResistanceMinimum}-${actor.stopSpellResistanceMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawStopSpellResistanceMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resistance Maximum: ${actor.stopSpellResistanceMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawStopSpellResistanceMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resistance Minimum: ${actor.stopSpellResistanceMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawStopSpellResolution(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resolution: ${actor.stopSpellResolutionMinimum}-${actor.stopSpellResolutionMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawStopSpellResolutionMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resolution Maximum: ${actor.stopSpellResolutionMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawStopSpellResolutionMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Stop Spell Resolution Minimum: ${actor.stopSpellResolutionMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawStrength(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Strength: ${actor.strength}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawTurn(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turn: ${actor.turn}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawTurnsSleep(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Sleep: ${actor.turnsSleep}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawTurnsSleepMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Sleep Maximum: ${actor.turnsSleepMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawTurnsSleepMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Sleep Minimum: ${actor.turnsSleepMinimum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawTurnsStopSpell(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Stop Spell: ${actor.turnsStopSpell}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawTurnsStopSpellMaximum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Stop Spell Maximum: ${actor.turnsStopSpellMaximum}"
        drawValue(color, font, fontMetrics, graphics2D, value, x, y)
    }

    private fun drawTurnsStopSpellMinimum(
        actor: Actor,
        color: Color,
        font: Font,
        fontMetrics: FontMetrics,
        graphics2D: Graphics2D,
        x: Int,
        y: Int,
    ) {
        val value = "Turns Stop Spell Minimum: ${actor.turnsStopSpellMinimum}"
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
        graphics2D.color = color
        graphics2D.drawString(string, x, y)
    }
}