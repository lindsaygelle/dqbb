import dqbb.Actor
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestActor {
    private lateinit var actor: Actor

    @BeforeTest
    fun createActor() {
        actor = Actor()
    }

    @Test
    fun testAgility() {
        actor.agility = -10
        assertEquals(0, actor.agility)
        actor.agility = 1
        assertEquals(1, actor.agility)
    }
}