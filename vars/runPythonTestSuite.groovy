import org.pcic.PythonUtils
import org.pcic.DockerUtils
import org.pcic.util.Utils


/**
 * Run a python test suite along with any other required installs
 *
 * @param imageName name of python docker image
 * @param requirementsFiles list of requirements files to be installed
 * @param pytestArgs list of args to give pytest
 * @param params map containing any of the optional arguments:
 *               serverUri: URI of the server to publish with
 *               pythonVersion: Version of python being used in the project
 *               gitExecInstall: Set to true if git executable needs to be
 *                               installed
 *               buildDocs: Set to true is sphinx docs need to be built
 *               containerData: Generally left as default, `pdp` for pdp data
 *                              from storage
 *               pipIndexUrl: URL of pip index to use during installation
 */
def call(String imageName, ArrayList requirementsFiles, String pytestArgs, Map params=[:]) {
    Utils utils = new Utils(this)
    PythonUtils pytils = new PythonUtils(this)
    DockerUtils dockerUtils = new DockerUtils(this)

    // collect any optional variables
    Map defaults = [serverUri: PCIC_DOCKER_DEV01,
                    pythonVersion: 3,
                    gitExecInstall: false,
                    buildDocs: false,
                    containerData: 'default',
                    pipIndexUrl:'https://pypi.pacificclimate.org/simple/',
                    egg: true]
    Map processed = utils.processParams(defaults, params)

    // set up some items used in the commands below
    String pip = pytils.getPipString(processed.pythonVersion)
    String containerDataArgs = dockerUtils.getContainerArgs(processed.containerData)

    withDockerServer([uri: processed.serverUri]) {
        def pytainer = docker.image(imageName)

        pytainer.inside(containerDataArgs) {
            if (processed.gitExecInstall) {
                pytils.installGitExecutable()
            }

            withEnv(["PIP_INDEX_URL=${processed.pipIndexUrl}"]) {
                pytils.installRequirements(pip, requirementsFiles, processed.egg)
            }

            if (processed.buildDocs) {
                pytils.buildDocs()
            }

            pytils.runPytest(pytestArgs)
        }
    }
}
