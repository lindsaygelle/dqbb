package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Armor : BreatheFireReducer,
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
                "id={} blocksSleep={}", id, field
            )
        }
    override var blocksStopSpell: Boolean = false
        set(value) {
            field = value
            logger.debug(
                "id={} blocksStopSpell={}", id, field
            )
        }
    override var breatheFireReduction: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} breatheFireReduction={}", id, field
            )
        }
    override var defense: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} defense={}", id, field
            )
        }
    override var hurtReduction: Int = 0
        set(value) {
            field = maxOf(0, value)
            logger.debug(
                "id={} hurtReduction={}", id, field
            )
        }
    private val logger: Logger = LogManager.getLogger(this::class.simpleName)
    override var name: String? = null
        set(value) {
            field = value
            logger.debug(
                "id={} name={}", id, field
            )
        }

    override fun toString(): String {
        return "blocksSleep=$blocksSleep blocksStopSpell=$blocksStopSpell breatheFireReduction=$breatheFireReduction defense=$defense hurtReduction=$hurtReduction id=$id name=$name simpleName=$simpleName"
    }
}