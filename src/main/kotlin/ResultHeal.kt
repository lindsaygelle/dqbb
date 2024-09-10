package dqbb

class ResultHeal : Result() {
    var hitPoints: Int? = null
        set(value) {
            field = value
            logger.debug(
                "hitPoints={} id={} simpleName={}", field, id, simpleName
            )
        }
}