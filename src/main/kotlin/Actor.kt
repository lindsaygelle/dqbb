package dqbb

import java.util.UUID

class Actor : BattleInvoker,
    BattleReceiver {
    var actorActions: List<ActorAction> = listOf()
        set(value) {
            field = value.sortedByDescending { actorAction -> actorAction.priorityType.ordinal }
        }

    override var agility: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    var allegiance: Int = 0

    override var armor: Armor? = null

    override var breatheFireRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var breatheFireRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(breatheFireRangeMaximum, value))
        }

    override var breatheFireScale: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var breatheFireShift: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var evasionRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var evasionRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(evasionRequirementMaximum, value))
        }

    override var healMoreScale: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var healMoreShift: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var healScale: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var healShift: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var healRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var healRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(healRangeMaximum, value))
        }

    override var herbRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var herbRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(herbRangeMaximum, value))
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
            field = maxOf(0, minOf(hitPointsMaximum, value))
        }

    override var hitPointsMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var hurtMoreScale: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var hurtMoreShift: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var hurtScale: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var hurtShift: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var hurtRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var hurtRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(hurtRangeMaximum, value))
        }

    override var hurtRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var hurtRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(hurtRequirementMaximum, value))
        }

    override var hurtResistanceMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var hurtResistanceMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(hurtResistanceMaximum, value))
        }

    var index: Int = -1

    override val items: MutableMap<ItemName, Int> = mutableMapOf()

    override var magicPoints: Int = 0
        set(value) {
            field = maxOf(0, minOf(magicPointsMaximum, value))
        }

    override var magicPointsMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var runRangeMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var runRangeMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(runRangeMaximum, value))
        }

    override var runShift: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var shield: Shield? = null

    override var sleepRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var sleepRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(sleepRequirementMaximum, value))
        }

    override var sleepResistanceMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var sleepResistanceMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(sleepResistanceMaximum, value))
        }

    override var sleepResolutionMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var sleepResolutionMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(sleepResolutionMaximum, value))
        }

    override var statusRunning: Boolean = false

    override var strength: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var stopSpellRequirementMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var stopSpellRequirementMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(stopSpellRequirementMaximum, value))
        }

    override var stopSpellResistanceMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var stopSpellResistanceMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(stopSpellResistanceMaximum, value))
        }

    override var stopSpellResolutionMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var stopSpellResolutionMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(stopSpellResolutionMaximum, value))
        }

    var turnsBattle: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var turnsSleep: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsSleepMaximum, value))
        }

    override var turnsSleepMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var turnsSleepMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsSleepMaximum, value))
        }

    override var turnsStopSpell: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsStopSpellMaximum, value))
        }

    override var turnsStopSpellMaximum: Int = 0
        set(value) {
            field = maxOf(0, value)
        }

    override var turnsStopSpellMinimum: Int = 0
        set(value) {
            field = maxOf(0, minOf(turnsStopSpellMaximum, value))
        }

    override val uuid: UUID = UUID.randomUUID()

    override var weapon: Weapon? = null

    fun performAction(actors: Collection<Actor>): Boolean {
        return actorActions.any { actorAction ->
            actorAction.invoke(this, actors)
        }
    }

    fun provideAttribute(attributeName: AttributeName): Int {
        return when (attributeName) {
            AttributeName.AGILITY -> agility
            AttributeName.ALLEGIANCE -> allegiance
            AttributeName.BREATHE_FIRE_RANGE_MAXIMUM -> breatheFireRangeMaximum
            AttributeName.BREATHE_FIRE_RANGE_MINIMUM -> breatheFireRangeMinimum
            AttributeName.BREATHE_FIRE_SCALE -> breatheFireScale
            AttributeName.BREATHE_FIRE_SHIFT -> breatheFireShift
            AttributeName.ARMOR_DEFENSE -> armor?.defense ?: 0
            AttributeName.EVASION_REQUIREMENT_MAXIMUM -> evasionRequirementMaximum
            AttributeName.EVASION_REQUIREMENT_MINIMUM -> evasionRequirementMinimum
            AttributeName.HEAL_MORE_SCALE -> healMoreScale
            AttributeName.HEAL_MORE_SHIFT -> healMoreShift
            AttributeName.HEAL_RANGE_MAXIMUM -> healRangeMaximum
            AttributeName.HEAL_RANGE_MINIMUM -> healRangeMinimum
            AttributeName.HEAL_SCALE -> healScale
            AttributeName.HEAL_SHIFT -> healShift
            AttributeName.HERB_COUNT -> items.getOrDefault(ItemName.HERB, 0)
            AttributeName.HERB_RANGE_MAXIMUM -> herbRangeMaximum
            AttributeName.HERB_RANGE_MINIMUM -> herbRangeMinimum
            AttributeName.HERB_SCALE -> herbScale
            AttributeName.HERB_SHIFT -> herbShift
            AttributeName.HIT_POINTS -> hitPoints
            AttributeName.HIT_POINTS_MAXIMUM -> hitPointsMaximum
            AttributeName.HIT_POINTS_PERCENTAGE -> hitPointsPercentage
            AttributeName.HURT_MORE_SCALE -> hurtMoreScale
            AttributeName.HURT_MORE_SHIFT -> hurtMoreShift
            AttributeName.HURT_RANGE_MAXIMUM -> hurtRangeMaximum
            AttributeName.HURT_RANGE_MINIMUM -> hurtRangeMinimum
            AttributeName.HURT_REQUIREMENT_MAXIMUM -> hurtRequirementMaximum
            AttributeName.HURT_REQUIREMENT_MINIMUM -> hurtRequirementMinimum
            AttributeName.HURT_RESISTANCE_MAXIMUM -> hurtResistanceMaximum
            AttributeName.HURT_RESISTANCE_MINIMUM -> hurtResistanceMinimum
            AttributeName.HURT_SCALE -> hurtScale
            AttributeName.HURT_SHIFT -> hurtShift
            AttributeName.ITEM_COUNT -> items.size
            AttributeName.MAGIC_POINTS -> magicPoints
            AttributeName.MAGIC_POINTS_MAXIMUM -> magicPointsMaximum
            AttributeName.MAGIC_POINTS_PERCENTAGE -> magicPointsPercentage
            AttributeName.RUN_RANGE_MAXIMUM -> runRangeMaximum
            AttributeName.RUN_RANGE_MINIMUM -> runRangeMinimum
            AttributeName.RUN_SHIFT -> runShift
            AttributeName.SHIELD_DEFENSE -> shield?.defense ?: 0
            AttributeName.SLEEP_REQUIREMENT_MAXIMUM -> sleepRequirementMaximum
            AttributeName.SLEEP_REQUIREMENT_MINIMUM -> sleepRequirementMinimum
            AttributeName.SLEEP_RESISTANCE_MAXIMUM -> sleepResistanceMaximum
            AttributeName.SLEEP_RESISTANCE_MINIMUM -> sleepResistanceMinimum
            AttributeName.SLEEP_RESOLUTION_MAXIMUM -> sleepResolutionMaximum
            AttributeName.SLEEP_RESOLUTION_MINIMUM -> sleepResolutionMinimum
            AttributeName.STOP_SPELL_REQUIREMENT_MAXIMUM -> stopSpellRequirementMaximum
            AttributeName.STOP_SPELL_REQUIREMENT_MINIMUM -> stopSpellRequirementMinimum
            AttributeName.STOP_SPELL_RESISTANCE_MAXIMUM -> stopSpellResistanceMaximum
            AttributeName.STOP_SPELL_RESISTANCE_MINIMUM -> stopSpellResistanceMinimum
            AttributeName.STOP_SPELL_RESOLUTION_MAXIMUM -> stopSpellResolutionMaximum
            AttributeName.STOP_SPELL_RESOLUTION_MINIMUM -> stopSpellResolutionMinimum
            AttributeName.TURNS_SLEEP -> turnsSleep
            AttributeName.TURNS_SLEEP_MAXIMUM -> turnsSleepMaximum
            AttributeName.TURNS_SLEEP_MINIMUM -> turnsSleepMinimum
            AttributeName.TURNS_STOP_SPELL -> turnsStopSpell
            AttributeName.TURNS_STOP_SPELL_MAXIMUM -> turnsStopSpellMaximum
            AttributeName.TURNS_STOP_SPELL_MINIMUM -> turnsStopSpellMinimum
            AttributeName.WEAPON_ATTACK -> weapon?.attack ?: 0
        }
    }
}
