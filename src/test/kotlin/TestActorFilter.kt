import dqbb.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class TestActorFilter {
    private lateinit var actor: Actor

    @BeforeTest
    fun before() {
        actor = Actor()
    }

    @Test
    fun testAlly() {
        val otherActor = Actor()
        otherActor.allegiance = 1
        actor.allegiance = otherActor.allegiance
        for (agility in (0..100)) {
            val actorFilter = ActorFilter(
                actorCriteria = listOf(
                    ActorCriterion(
                        actorChecks = listOf(
                            ActorCheck(
                                attributeName = AttributeName.AGILITY,
                                operatorType = OperatorType.EQUAL,
                                value = agility
                            )
                        )
                    )
                ),
                selectionType = SelectionType.ALLY
            )
            otherActor.agility = agility
            actor.agility = otherActor.agility
            val actors = actorFilter.filter(actor, listOf(actor, otherActor))
            assertEquals(2, actors.size)
            assertContains(actors, actor)
            assertContains(actors, otherActor)
        }
    }

    @Test
    fun testEnemy() {
        val otherActor = Actor()
        otherActor.allegiance = 1
        actor.allegiance = 0
        for (agility in (0..100)) {
            val actorFilter = ActorFilter(
                actorCriteria = listOf(
                    ActorCriterion(
                        actorChecks = listOf(
                            ActorCheck(
                                attributeName = AttributeName.AGILITY,
                                operatorType = OperatorType.EQUAL,
                                value = agility
                            )
                        )
                    )
                ),
                selectionType = SelectionType.ENEMY
            )
            otherActor.agility = agility
            actor.agility = otherActor.agility
            val actors = actorFilter.filter(actor, listOf(actor, otherActor))
            assertContains(actors, otherActor)
            assertEquals(1, actors.size)
        }
    }

    @Test
    fun testSelf() {
        for (agility in (0..100)) {
            val actorFilter = ActorFilter(
                actorCriteria = listOf(
                    ActorCriterion(
                        actorChecks = listOf(
                            ActorCheck(
                                attributeName = AttributeName.AGILITY,
                                operatorType = OperatorType.EQUAL,
                                value = agility
                            )
                        )
                    )
                ),
                selectionType = SelectionType.SELF
            )
            actor.agility = agility
            val actors = actorFilter.filter(actor, listOf(actor))
            assertContains(actors, actor)
            assertEquals(1, actors.size)
        }
    }
}