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
                    pipIndexUrl:'https://pypi.pacificclimate.org/simple']
    Map processed = utils.processParams(defaults, params)

    // set up some items used in the commands below
    String pip = pytils.getPipString(processed.pythonVersion)
    Map containerDataArgs = dockerUtils.getContainerArgs(containerData)

    withDockerServer([uri: proccessed.serverUri]) {
        def pytainer = docker.image(imageName)

        pytainer.inside(containerDataArgs) {
            if (proccessed.gitExecInstall) {
                pytils.installGitExecutable()
                // sh 'apt-get update'
                // sh 'apt-get install -y git'
            }

            withEnv(["PIP_INDEX_URL=${proccessed.pipIndexUrl}"]) {
                pytils.installRequirements(pip, requirementsFiles)
                // sh "${pip} install ${required}"
                // sh "${pip} install -e ."
            }

            if (proccessed.buildDocs) {
                pytils.buildDocs()
                // sh 'python setup.py install'
                // sh 'python setup.py build_sphinx'
                // sh 'python setup.py install'
            }

            pytils.runPytest(pytestArgs)
            // sh "py.test ${pytestArgs}"
        }
    }
}
