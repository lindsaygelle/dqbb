package dqbb

interface InvokerStopSpell : InvokerMagic {
    var stopSpellRequirementMaximum: Int
    var stopSpellRequirementMinimum: Int
}