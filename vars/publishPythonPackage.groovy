import pcic.utils

/**
 * Publish python package to pypi
 *
 * @param imageName name of python image
 * @param credentialsId  identification string of credentials in jenkins
 * @param argMap map containing any of the optional arguments (pythonVersion,
 *               serverUri, repoUrl)
 */
def call(String imageName, String credentialsId, Map argMap=[:]) {
    // collect any optional variables
    Map defaults = [pythonVersion: 3,
                    serverUri: PCIC_DOCKER_DEV01,
                    repoUrl: 'https://pypi.pacificclimate.org/']
    Map args = utils.applyOptionalParameters(defaults, argsMap)

    // set up some items used in the commands below
    String pip = utils.getPipString(args.pythonVersion)

    withDockerServer([uri: args.serverUri]) {
        def pytainer = docker.image(imageName)

        pytainer.inside {
            // get twine
            sh "${pip} install twine wheel"

            // Build
            sh 'python setup.py sdist bdist_wheel'

            // Release
            withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                sh "twine upload --repository-url ${args.repoUrl} --skip-existing -u ${USERNAME} -p ${PASSWORD} dist/*"
            }
        }
    }
}
