import dqbb.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestActorCriterion {
    private lateinit var actor: Actor

    @BeforeTest
    fun before() {
        actor = Actor()
    }

    @Test
    fun testMatchAll() {
        val actorCriterion = ActorCriterion(
            actorChecks = listOf(
                ActorCheck(
                    attributeName = AttributeName.AGILITY,
                    operatorType = OperatorType.EQUAL,
                    priorityType = PriorityType.HIGH,
                    value = 1
                ),
                ActorCheck(
                    attributeName = AttributeName.HIT_POINTS_MAXIMUM,
                    operatorType = OperatorType.EQUAL,
                    priorityType = PriorityType.LOW,
                    value = 1
                )
            ),
            matchType = MatchType.ALL
        )
        actor.agility = 1
        actor.hitPointsMaximum = 1
        assertEquals(true, actorCriterion.check(actor))
    }

    @Test
    fun testMatchAny() {
        val actorCriterion = ActorCriterion(
            actorChecks = listOf(
                ActorCheck(
                    attributeName = AttributeName.AGILITY,
                    operatorType = OperatorType.EQUAL,
                    priorityType = PriorityType.HIGH,
                    value = Int.MAX_VALUE
                ),
                ActorCheck(
                    attributeName = AttributeName.HIT_POINTS_MAXIMUM,
                    operatorType = OperatorType.EQUAL,
                    priorityType = PriorityType.LOW,
                    value = Int.MAX_VALUE / 2
                )
            ),
            matchType = MatchType.ANY
        )
        actor.agility = 0
        actor.hitPointsMaximum = Int.MAX_VALUE / 2
        assertEquals(true, actorCriterion.check(actor))
    }
}