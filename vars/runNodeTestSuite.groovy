import org.pcic.NodeUtils


/**
 * Install node packages and run test command
 *
 * @param node jenkins node plugin name to use
 * @param command test command to run
 */
def call(String node, String command) {
    NodeUtils nodeUtils = new NodeUtils(this)
    nodejs(node) {
        nodeUtils.installAndRun(command)
    }
}
