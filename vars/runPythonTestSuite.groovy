import org.pcic.PythonUtils
import org.pcic.DockerUtils
import org.pcic.util.Utils


/**
 * Run a python test suite along with any other required installs
 *
 * @param imageName name of python docker image
 * @param requirementsFiles list of requirements files to be installed
 * @param pytestArgs args to give to pytest
 * @param options map containing any of the optional arguments:
 *                serverUri: URI of the server to publish with
 *                pythonVersion: Version of python being used in the project
 *                aptPackages: List of packages to install
 *                buildDocs: Set to true is sphinx docs need to be built
 *                containerData: Generally left as default, `pdp` for pdp data
 *                               from storage
 *                pipIndexUrl: URL of pip index to use during installation
 */
def call(String imageName, ArrayList requirementsFiles, String pytestArgs, Map options = [:]) {
    Utils utils = new Utils(this)
    PythonUtils pytils = new PythonUtils(this)
    DockerUtils dockerUtils = new DockerUtils(this)

    ArrayList defaults = ['serverUri', 'pythonVersion', 'aptPackages',
                          'buildDocs', 'containerData', 'pipIndexUrl']
    Map args = utils.getUpdatedArgs(defaults, options)

    // set up some items used in the commands below
    String pip = pytils.applyPythonVersion('pip', args.pythonVersion)
    String python = pytils.applyPythonVersion('python', args.pythonVersion)
    String containerDataArgs = dockerUtils.getContainerArgs(args.containerData)

    withDockerServer([uri: args.serverUri]) {
        def pytainer = docker.image(imageName)

        pytainer.inside(containerDataArgs) {
            if (args.aptPackages.size() > 0) {
                utils.installAptPackages(args.aptPackages)
            }

            withEnv(["PIP_INDEX_URL=${args.pipIndexUrl}"]) {
                pytils.installRequirements(pip, requirementsFiles)
            }

            if (args.buildDocs) {
                pytils.buildDocs(python)
            }

            pytils.runPytest(pytestArgs)
        }
    }
}
