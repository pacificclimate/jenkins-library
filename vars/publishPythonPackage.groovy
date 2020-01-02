import org.pcic.PythonUtils
import org.pcic.util.Utils


/**
 * Publish python package to pypi
 *
 * @param imageName name of python image
 * @param credentialsId  identification string of credentials in jenkins
 * @param params map containing any of the optional arguments:
 *              pythonVersion: Version of python being used in the project
 *              serverUri: URI of the server to publish with
 *              pypiUrl: URL of the pypi server to push to
 */
def call(String imageName, String credentialsId, Map params=[:]) {
    Utils utils = new Utils(this)
    PythonUtils pytils = new PythonUtils(this)
    // collect any optional variables
    Map defaults = [pythonVersion: 3,
                    serverUri: PCIC_DOCKER_DEV01,
                    pypiUrl: 'https://pypi.pacificclimate.org/']
    Map processed = utils.processParams(defaults, params)

    // set up some items used in the commands below
    String pip = pytils.getPipString(processed.pythonVersion)

    withDockerServer([uri: processed.serverUri]) {
        def pytainer = docker.image(imageName)

        pytainer.inside {
            // get twine
            pytils.preparePackage(pip)
            // sh "${pip} install twine wheel"
            //
            // // Build
            // sh 'python setup.py sdist bdist_wheel'

            // Release
            withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                pytils.releasePackage(processed.pypiUrl, USERNAME, PASSWORD)
                // sh "twine upload --repository-url ${processed.pypiUrl} --skip-existing -u ${USERNAME} -p ${PASSWORD} dist/*"
            }
        }
    }
}
