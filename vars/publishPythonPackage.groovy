import pcic.Utils


def call(String imageName, String credentialsId, Map argMap=[:]) {
    // collect any optional variables
    Map defaults = [pythonVersion: 3,
                    serverUri: PCIC_DOCKER_DEV01,
                    repoUrl: 'https://pypi.pacificclimate.org/']
    Map args = applyOptionalParameters(defaults, argsMap)

    // set up some items used in the commands below
    String pip = getPipString(args.pythonVersion)

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
