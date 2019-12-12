/**
 * Install node packages and run test command
 *
 * @param node jenkins node plugin name to use
 * @param command test command to run
 */
def call(String node, String command) {
    nodejs(node) {
        sh 'npm install'
        sh "npm run ${command}"
    }
}
