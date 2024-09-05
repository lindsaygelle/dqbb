package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Armor() : BreatheFireReducer,
    DefensePointer,
    HurtReducer,
    Identifier,
    Nameable,
    SleepBlocker,
    StopSpellBlocker {
    override var blocksSleep: Boolean = false
        set(value) {
            field = value
            logger.debug(
                "blocksSleep={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var blocksStopSpell: Boolean = false
        set(value) {
            field = value
            logger.debug(
                "blocksStopSpell={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var breatheFireReduction: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "breatheFireReduction={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var defense: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "defense={} id={} simpleName={}", field, id, simpleName
            )
        }

    override var hurtReduction: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "hurtReduction={} id={} simpleName={}", field, id, simpleName
            )
        }

    private val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override var name: String? = null
        set(value) {
            field = value
            logger.debug(
                "id={} name={} simpleName={}", id, field, simpleName
            )
        }

    constructor(
        blocksSleep: Boolean,
        blocksStopSpell: Boolean,
        breatheFireReduction: Int,
        defense: Int,
        hurtReduction: Int,
        name: String? = null,
    ) : this() {
        this.blocksSleep = blocksSleep
        this.blocksStopSpell = blocksStopSpell
        this.breatheFireReduction = breatheFireReduction
        this.defense = defense
        this.hurtReduction = hurtReduction
        this.name = name
    }

    override fun toString(): String {
        return "blocksSleep=$blocksSleep blocksStopSpell=$blocksStopSpell breatheFireReduction=$breatheFireReduction defense=$defense hurtReduction=$hurtReduction id=$id name=$name simpleName=$simpleName"
    }
}