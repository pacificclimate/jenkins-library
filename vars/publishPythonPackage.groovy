import org.pcic.PythonUtils
import org.pcic.util.Utils


/**
 * Publish python package to pypi
 *
 * @param imageName name of python image
 * @param credentialsId identification string of credentials in jenkins
 * @param params map containing any of the optional arguments:
 *               pythonVersion: Version of python being used in the project
 *               serverUri: URI of the server to publish with
 *               pypiUrl: URL of the pypi server to push to
 */
def call(String imageName, String credentialsId, Map params=[:]) {
    Utils utils = new Utils(this)
    PythonUtils pytils = new PythonUtils(this)

    Map args = utils.getUpdatedArgs(['pythonVersion', 'serverUri', 'pypiUrl'],
                                    options)

    // set up some items used in the commands below
    String pip = pytils.getPipString(args.pythonVersion)

    withDockerServer([uri: args.serverUri]) {
        def pytainer = docker.image(imageName)

        pytainer.inside {
            pytils.preparePackage(pip)

            withCredentials([usernamePassword(credentialsId: credentialsId,
                                              usernameVariable: 'USERNAME',
                                              passwordVariable: 'PASSWORD')]) {
                pytils.releasePackage(args.pypiUrl, USERNAME, PASSWORD)
            }
        }
    }
}
