package dqbb

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import javax.swing.JPanel

open class Panel() : JPanel(),
    Identifier {
    protected val logger: Logger = LogManager.getLogger(this::class.simpleName)

    override fun setName(name: String?) {
        super.setName(name)
        logger.debug(
            "id={} name={} simpleName={}", id, this.name, simpleName
        )
    }

    override fun setVisible(aFlag: Boolean) {
        super.setVisible(aFlag)
        logger.debug(
            "id={} isVisible={} simpleName={}", id, isVisible, simpleName
        )
    }
}