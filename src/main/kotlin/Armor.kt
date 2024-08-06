package dqbb

class Armor {
    var blocksSleep: Boolean = false
    var blocksStopSpell: Boolean = false
    var breatheFireReduction: Int = 1
        set(value) {
            field = maxOf(1, value)
        }
    var defense: Int = 1
        set(value) {
            field = maxOf(1, value)
        }
    var hurtReduction: Int = 1
        set(value) {
            field = maxOf(1, value)
        }
}