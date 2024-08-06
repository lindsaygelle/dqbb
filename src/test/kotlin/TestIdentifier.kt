import dqbb.Identifier
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals


internal class TestIdentifier {
    private val uuid: UUID = UUID.nameUUIDFromBytes("DRAGON_QUEST".toByteArray())
    private val identifier: Identifier = MockIdentifier(
        uuid = uuid
    )

    @Test
    fun uuid() {
        assertEquals(
            uuid, identifier.uuid
        )
    }
}