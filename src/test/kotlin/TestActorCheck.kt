import dqbb.Actor
import dqbb.ActorCheck
import dqbb.AttributeName
import dqbb.OperatorType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestActorCheck {
    private lateinit var actor: Actor
    private lateinit var actorCheck: ActorCheck

    @BeforeTest
    fun before() {
        actor = Actor()
    }

    @Test
    fun testEqual() {
        val agility = 1
        actorCheck = ActorCheck(
            attributeName = AttributeName.AGILITY,
            operatorType = OperatorType.EQUAL,
            value = agility
        )
        actor.agility = agility
        assertEquals(true, actorCheck.check(actor))
    }

    @Test
    fun testGreaterThan() {
        val agility = 1
        actorCheck = ActorCheck(
            attributeName = AttributeName.AGILITY,
            operatorType = OperatorType.GREATER_THAN,
            value = agility
        )
        actor.agility = agility + 1
        assertEquals(true, actorCheck.check(actor))
    }

    @Test
    fun testGreaterThanEqual() {
        val agility = 1
        actorCheck = ActorCheck(
            attributeName = AttributeName.AGILITY,
            operatorType = OperatorType.GREATER_THAN_EQUAL,
            value = agility
        )
        actor.agility = agility
        assertEquals(true, actorCheck.check(actor))
        actor.agility += 1
        assertEquals(true, actorCheck.check(actor))
    }

    @Test
    fun testLessThan() {
        val agility = 1
        actorCheck = ActorCheck(
            attributeName = AttributeName.AGILITY,
            operatorType = OperatorType.LESS_THAN,
            value = agility
        )
        actor.agility = agility - 1
        assertEquals(true, actorCheck.check(actor))
    }

    @Test
    fun testLessThanEqual() {
        val agility = 1
        actorCheck = ActorCheck(
            attributeName = AttributeName.AGILITY,
            operatorType = OperatorType.LESS_THAN_EQUAL,
            value = agility
        )
        actor.agility = agility
        assertEquals(true, actorCheck.check(actor))
        actor.agility -= 1
        assertEquals(true, actorCheck.check(actor))
    }

    @Test
    fun testNot() {
        val agility = 1
        actorCheck = ActorCheck(
            attributeName = AttributeName.AGILITY,
            operatorType = OperatorType.NOT,
            value = agility
        )
        actor.agility = agility + 1
        assertEquals(true, actorCheck.check(actor))
    }
}