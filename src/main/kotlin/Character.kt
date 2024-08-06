package dqbb

const val AGILITY_MINIMUM: Int = 3
const val BREATHE_FIRE_RANGE_MINIMUM: Int = 0
const val BREATHE_FIRE_SCALE_MINIMUM: Int = 16
const val BREATHE_FIRE_SHIFT_MINIMUM: Int = 7
const val DAMAGE_RESISTANCE_MINIMUM: Int = 1
const val HEAL_MORE_SCALE_MINIMUM: Int = 85
const val HEAL_MORE_SHIFT_MINIMUM: Int = 15
const val HEAL_RANGE_MINIMUM: Int = 0
const val HEAL_SCALE_MINIMUM: Int = 1
const val HEAL_SHIFT_MINIMUM: Int = 1
const val HERB_SCALE_MINIMUM: Int = 23
const val HERB_SHIFT_MINIMUM: Int = 15
const val HIT_POINTS_MINIMUM: Int = 1
const val HURT_MORE_SCALE_MINIMUM: Int = 85
const val HURT_MORE_SHIFT_MINIMUM: Int = 15
const val HURT_RANGE_MINIMUM: Int = 0
const val HURT_REQUIREMENT_MINIMUM: Int = 0
const val HURT_SCALE_MINIMUM: Int = 3
const val HURT_SHIFT_MINIMUM: Int = 7
const val MAGIC_POINTS_MINIMUM: Int = 0
const val SLEEP_REQUIREMENT_MINIMUM: Int = 0
const val STATUS_RESISTANCE_MINIMUM: Int = 0
const val STOP_SPELL_REQUIREMENT_MINIMUM: Int = 0
const val STRENGTH_MINIMUM: Int = 5
const val TURNS_SLEEP_MINIMUM: Int = 0
const val TURNS_STOP_SPELL_MINIMUM: Int = 0


class Character(
    agility: Int? = null,
    var allegiance: Int,
    override var armor: Armor? = null,
    breatheFireRangeMaximum: Int? = null,
    breatheFireRangeMinimum: Int? = null,
    breatheFireScale: Int? = null,
    breatheFireShift: Int? = null,
    damageResistance: Int? = null,
    private val decisions: Collection<Decision>,
    healMoreRangeMaximum: Int? = null,
    healMoreRangeMinimum: Int? = null,
    healMoreScale: Int? = null,
    healMoreShift: Int? = null,
    healRangeMaximum: Int? = null,
    healRangeMinimum: Int? = null,
    healScale: Int? = null,
    healShift: Int? = null,
    herbScale: Int? = null,
    herbShift: Int? = null,
    hitPoints: Int? = null,
    hitPointsMaximum: Int? = null,
    hurtMoreRangeMaximum: Int? = null,
    hurtMoreRangeMinimum: Int? = null,
    hurtMoreRequirementMaximum: Int? = null,
    hurtMoreRequirementMinimum: Int? = null,
    hurtMoreScale: Int? = null,
    hurtMoreShift: Int? = null,
    hurtRangeMaximum: Int? = null,
    hurtRangeMinimum: Int? = null,
    hurtRequirementMaximum: Int? = null,
    hurtRequirementMinimum: Int? = null,
    hurtScale: Int? = null,
    hurtShift: Int? = null,
    magicPoints: Int? = null,
    magicPointsMaximum: Int? = null,
    override var name: String = "DEFAULT",
    override var shield: Shield? = null,
    sleepRequirementMaximum: Int? = null,
    sleepRequirementMinimum: Int? = null,
    statusResistance: Int? = null,
    stopSpellRequirementMaximum: Int? = null,
    stopSpellRequirementMinimum: Int? = null,
    strength: Int? = null,
    turnsSleep: Int? = null,
    turnsSleepMaximum: Int? = null,
    turnsSleepMinimum: Int? = null,
    turnsStopSpell: Int? = null,
    turnsStopSpellMaximum: Int? = null,
    turnsStopSpellMinimum: Int? = null,
    override var weapon: Weapon? = null,
) : BattleInvoker, BattleReceiver {
    override var agility: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var breatheFireRangeMaximum: Int = 0
        set(value) {
            field = maxOf((BREATHE_FIRE_RANGE_MINIMUM + 1), value)
        }
    override var breatheFireRangeMinimum: Int = 0
        set(value) {
            field = maxOf(BREATHE_FIRE_RANGE_MINIMUM, minOf(value, breatheFireRangeMaximum))
        }
    override var breatheFireScale: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var breatheFireShift: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var damageResistance: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var healMoreRangeMaximum: Int = 0
        set(value) {
            field = maxOf((HEAL_RANGE_MINIMUM + 1), value)
        }
    override var healMoreRangeMinimum: Int = 0
        set(value) {
            field = maxOf(HEAL_RANGE_MINIMUM, minOf(value, healMoreRangeMaximum))
        }
    override var healMoreScale: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var healMoreShift: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var healRangeMaximum: Int = 0
        set(value) {
            field = maxOf((HEAL_RANGE_MINIMUM + 1), value)
        }
    override var healRangeMinimum: Int = 0
        set(value) {
            field = maxOf(HEAL_RANGE_MINIMUM, minOf(value, healRangeMaximum))
        }
    override var healScale: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var healShift: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var herbRangeMaximum: Int = 0
        set(value) {
            field = maxOf((HEAL_RANGE_MINIMUM + 1), value)
        }
    override var herbRangeMinimum: Int = 0
        set(value) {
            field = maxOf(HEAL_RANGE_MINIMUM, minOf(value, herbRangeMaximum))
        }
    override var herbScale: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var herbShift: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var hitPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(value, hitPointsMaximum))
        }
    override var hitPointsMaximum: Int = 0
        set(value) {
            field = maxOf(HIT_POINTS_MINIMUM, value)
        }
    override val hitPointsPercentage: Int
        get() = toPercentage(hitPoints, hitPointsMaximum)
    override var hurtMoreRangeMaximum: Int = 0
        set(value) {
            field = maxOf((HURT_RANGE_MINIMUM + 1), value)
        }
    override var hurtMoreRangeMinimum: Int = 0
        set(value) {
            field = maxOf(HURT_RANGE_MINIMUM, minOf(value, hurtMoreRangeMaximum))
        }
    override var hurtMoreRequirementMaximum: Int = 0
        set(value) {
            field = maxOf((HURT_REQUIREMENT_MINIMUM + 1), value)
        }
    override var hurtMoreRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(HURT_REQUIREMENT_MINIMUM, minOf(value, hurtMoreRequirementMaximum))
        }
    override var hurtMoreScale: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var hurtMoreShift: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var hurtRangeMaximum: Int = 0
        set(value) {
            field = maxOf((HURT_RANGE_MINIMUM + 1), value)
        }
    override var hurtRangeMinimum: Int = 0
        set(value) {
            field = maxOf(HURT_RANGE_MINIMUM, minOf(value, hurtRangeMaximum))
        }
    override var hurtRequirementMaximum: Int = 0
        set(value) {
            field = maxOf((HURT_REQUIREMENT_MINIMUM + 1), value)
        }
    override var hurtRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(HURT_REQUIREMENT_MINIMUM, minOf(value, hurtRequirementMaximum))
        }
    override var hurtScale: Int = 0
        set(value) {
            field = maxOf(HURT_SCALE_MINIMUM, value)
        }
    override var hurtShift: Int = 0
        set(value) {
            field = maxOf(HURT_SHIFT_MINIMUM, value)
        }
    override var magicPoints: Int = 0
        set(value) {
            field = maxOf(MAGIC_POINTS_MINIMUM, minOf(value, magicPointsMaximum))
        }
    override var magicPointsMaximum: Int = 0
        set(value) {
            field = maxOf(MAGIC_POINTS_MINIMUM, value)
        }
    override val magicPointsPercentage: Int
        get() = toPercentage(magicPoints, magicPointsMaximum)
    override var sleepRequirementMaximum: Int = 0
        set(value) {
            field = maxOf((SLEEP_REQUIREMENT_MINIMUM + 1), value)
        }
    override var sleepRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(SLEEP_REQUIREMENT_MINIMUM, minOf(value, sleepRequirementMaximum))
        }
    override var statusResistance: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override val statusSleep: Boolean
        get() = turnsStopSpell > 0
    override val statusStopSpell: Boolean
        get() = turnsSleep > 0
    override var stopSpellRequirementMaximum: Int = 0
        set(value) {
            field = maxOf((STOP_SPELL_REQUIREMENT_MINIMUM + 1), value)
        }
    override var stopSpellRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(STOP_SPELL_REQUIREMENT_MINIMUM, minOf(stopSpellRequirementMaximum, value))
        }
    override var strength: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
    override var turnsSleep: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsSleepMaximum, value))
        }
    override var turnsSleepMaximum: Int = 0
        set(value) {
            field = maxOf((TURNS_SLEEP_MINIMUM + 1), value)
        }
    override var turnsSleepMinimum: Int = 0
        set(value) {
            field = maxOf(TURNS_SLEEP_MINIMUM, minOf(turnsSleepMaximum, value))
        }
    override val turnsSleepPercentage: Int
        get() = toPercentage(turnsSleep, turnsSleepMaximum)
    override var turnsStopSpell: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsStopSpellMaximum, value))
        }
    override var turnsStopSpellMaximum: Int = 0
        set(value) {
            field = maxOf((TURNS_STOP_SPELL_MINIMUM + 1), value)
        }
    override var turnsStopSpellMinimum: Int = 0
        set(value) {
            field = maxOf(TURNS_STOP_SPELL_MINIMUM, minOf(turnsStopSpellMaximum, value))
        }
    override val turnsStopSpellPercentage: Int
        get() = toPercentage(turnsStopSpell, turnsStopSpellMaximum)

    private fun toPercentage(value: Int, valueMaximum: Int): Int {
        return ((value.toDouble() / valueMaximum) * 100).toInt()
    }

    override fun toString(): String {
        return "agility=$agility " +
               "allegiance=$allegiance " +
               "armor.defense=${armor?.defense} " +
               "breatheFireRangeMaximum=$breatheFireRangeMaximum " +
               "breatheFireRangeMinimum=$breatheFireRangeMinimum " +
               "breatheFireScale=$breatheFireScale " +
               "breatheFireShift=$breatheFireShift " +
               "class=${super.toString()} " +
               "damageResistance=$damageResistance " +
               "hashCode=${hashCode()} " +
               "healMoreRangeMaximum=$healMoreRangeMaximum " +
               "healMoreRangeMinimum=$healMoreRangeMinimum " +
               "healMoreScale=$healMoreScale healMoreShift=$healMoreShift " +
               "healScale=$healScale " +
               "healShift=$healShift " +
               "healRangeMaximum=$healRangeMaximum " +
               "healRangeMinimum=$healRangeMinimum " +
               "herbScale=$herbScale " +
               "herbShift=$herbShift " +
               "hitPoints=$hitPoints " +
               "hitPointsMaximum=$hitPointsMaximum " +
               "hurtMoreRangeMaximum=$hurtMoreRangeMaximum " +
               "hurtMoreRangeMinimum=$hurtMoreRangeMinimum " +
               "hurtMoreRequirementMaximum=$hurtMoreRequirementMaximum " +
               "hurtMoreRequirementMinimum=$hurtMoreRequirementMinimum " +
               "hurtMoreScale=$hurtMoreScale " +
               "hurtMoreShift=$hurtMoreShift " +
               "hurtRangeMaximum=$hurtRangeMaximum " +
               "hurtRangeMinimum=$hurtRangeMinimum " +
               "hurtRequirementMaximum=$hurtRequirementMaximum " +
               "hurtRequirementMinimum=$hurtRequirementMinimum " +
               "hurtScale=$hurtScale " +
               "hurtShift=$hurtShift " +
               "magicPoints=$magicPoints " +
               "magicPointsMaximum=$magicPointsMaximum " +
               "shield.defense=${shield?.defense} " +
               "sleepRequirementMaximum=$sleepRequirementMaximum " +
               "sleepRequirementMinimum=$sleepRequirementMinimum " +
               "statusResistance=$statusResistance " +
               "stopSpellRequirementMaximum=$stopSpellRequirementMaximum " +
               "stopSpellRequirementMinimum=$stopSpellRequirementMinimum " +
               "strength=$strength " +
               "turnsSleep=$turnsSleep " +
               "turnsSleepMaximum=$turnsSleepMaximum " +
               "turnsSleepMinimum=$turnsSleepMinimum " +
               "turnsStopSpell=$turnsStopSpell " +
               "turnsStopSpellMaximum=$turnsStopSpellMaximum" +
               "turnsStopSpellMinimum=$turnsStopSpellMinimum " +
               "weapon.attack=${weapon?.attack}"
    }

    init {
        this.agility = (agility ?: AGILITY_MINIMUM)
        this.breatheFireRangeMaximum = (breatheFireRangeMaximum ?: BREATHE_FIRE_RANGE_MINIMUM)
        this.breatheFireRangeMinimum = (breatheFireRangeMinimum ?: BREATHE_FIRE_RANGE_MINIMUM)
        this.breatheFireScale = (breatheFireScale ?: BREATHE_FIRE_SCALE_MINIMUM)
        this.breatheFireShift = (breatheFireShift ?: BREATHE_FIRE_SHIFT_MINIMUM)
        this.damageResistance = (damageResistance ?: DAMAGE_RESISTANCE_MINIMUM)
        this.healMoreScale = (healMoreScale ?: HEAL_MORE_SCALE_MINIMUM)
        this.healMoreShift = (healMoreShift ?: HEAL_MORE_SHIFT_MINIMUM)
        this.healRangeMaximum = (healRangeMaximum ?: HEAL_RANGE_MINIMUM)
        this.healRangeMinimum = (healRangeMinimum ?: HEAL_RANGE_MINIMUM)
        // Default to healRangeMaximum
        this.healMoreRangeMaximum = (healMoreRangeMaximum ?: this.healRangeMaximum)
        // Default to healRangeMinimum
        this.healMoreRangeMinimum = (healMoreRangeMinimum ?: this.healRangeMinimum)
        this.healScale = (healScale ?: HEAL_SCALE_MINIMUM)
        this.healShift = (healShift ?: HEAL_SHIFT_MINIMUM)
        this.herbScale = (herbScale ?: HERB_SCALE_MINIMUM)
        this.herbShift = (herbShift ?: HERB_SHIFT_MINIMUM)
        this.hitPointsMaximum = (hitPointsMaximum ?: HEAL_RANGE_MINIMUM)
        // Default to hitPointsMaximum
        this.hitPoints = (hitPoints ?: this.hitPointsMaximum)
        this.hurtMoreScale = (hurtMoreScale ?: HURT_MORE_SCALE_MINIMUM)
        this.hurtMoreShift = (hurtMoreShift ?: HURT_MORE_SHIFT_MINIMUM)
        this.hurtRangeMaximum = (hurtRangeMaximum ?: HURT_RANGE_MINIMUM)
        this.hurtRangeMinimum = (hurtRangeMinimum ?: HURT_RANGE_MINIMUM)
        this.hurtRequirementMaximum = (hurtRequirementMaximum ?: HURT_REQUIREMENT_MINIMUM)
        this.hurtRequirementMinimum = (hurtRequirementMinimum ?: HURT_REQUIREMENT_MINIMUM)
        // Default to hurtRangeMaximum
        this.hurtMoreRangeMaximum = (hurtMoreRangeMaximum ?: this.hurtRangeMaximum)
        // Default to hurtRangeMinimum
        this.hurtMoreRangeMinimum = (hurtMoreRangeMinimum ?: this.hurtRangeMinimum)
        // Default to hurtRangeMaximum
        this.hurtMoreRequirementMaximum = (hurtMoreRequirementMaximum ?: this.hurtRequirementMaximum)
        // Default to hurtRangeMinimum
        this.hurtMoreRequirementMinimum = (hurtMoreRequirementMinimum ?: this.hurtRequirementMinimum)
        this.hurtScale = (hurtScale ?: HURT_SCALE_MINIMUM)
        this.hurtShift = (hurtShift ?: HURT_SHIFT_MINIMUM)
        this.magicPointsMaximum = (magicPointsMaximum ?: MAGIC_POINTS_MINIMUM)
        // Default to magicPointsMaximum
        this.magicPoints = (magicPoints ?: this.magicPointsMaximum)
        this.sleepRequirementMaximum = (sleepRequirementMaximum ?: SLEEP_REQUIREMENT_MINIMUM)
        this.sleepRequirementMinimum = (sleepRequirementMinimum ?: SLEEP_REQUIREMENT_MINIMUM)
        this.statusResistance = (statusResistance ?: STATUS_RESISTANCE_MINIMUM)
        this.stopSpellRequirementMaximum = (stopSpellRequirementMaximum ?: STOP_SPELL_REQUIREMENT_MINIMUM)
        this.stopSpellRequirementMinimum = (stopSpellRequirementMinimum ?: STOP_SPELL_REQUIREMENT_MINIMUM)
        this.strength = (strength ?: STRENGTH_MINIMUM)
        this.turnsSleepMaximum = (turnsSleepMaximum ?: TURNS_SLEEP_MINIMUM)
        this.turnsSleepMinimum = (turnsSleepMinimum ?: TURNS_SLEEP_MINIMUM)
        this.turnsSleep = (turnsSleep ?: 0)
        this.turnsStopSpellMaximum = (turnsStopSpellMaximum ?: TURNS_STOP_SPELL_MINIMUM)
        this.turnsStopSpellMinimum = (turnsStopSpellMinimum ?: TURNS_STOP_SPELL_MINIMUM)
        this.turnsStopSpell = (turnsStopSpell ?: 0)
    }
}