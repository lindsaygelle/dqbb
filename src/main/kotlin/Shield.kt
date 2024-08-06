package dqbb

class Shield {
    var defense: Int = 0
        set(value) {
            field = maxOf(0, value)
        }
}