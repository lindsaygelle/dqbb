package dqbb

object QualifierBuilder {
    fun build(qualifierConfig: QualifierConfig): Qualifier? {
        val match = MatchMatcher.match(qualifierConfig.match)
        if (match == null) {
            return null
        }
        val priority = PriorityMatcher.match(qualifierConfig.priority)
        if (priority == null) {
            return null
        }
        val target = TargetMatcher.match(qualifierConfig.target)
        if (target == null) {
            return null
        }
        val checkers = qualifierConfig.checkers.map {
            CheckerBuilder.build(it)
        }
        return Qualifier(
            checkers = checkers.filterNotNull(),
            match = match,
            priority = priority,
            target = target
        )
    }
}