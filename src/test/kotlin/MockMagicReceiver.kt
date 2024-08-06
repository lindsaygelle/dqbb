import dqbb.Receiver
import java.util.UUID

internal class MockMagicReceiver : Receiver {
    override val uuid: UUID = UUID.randomUUID()
}