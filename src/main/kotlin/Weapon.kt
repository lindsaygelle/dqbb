package dqbb

class Weapon {
    var attack: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
}