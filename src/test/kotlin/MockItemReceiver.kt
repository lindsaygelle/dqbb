import dqbb.Receiver
import java.util.UUID

internal class MockItemReceiver : Receiver {
    override val uuid: UUID = UUID.randomUUID()
}
