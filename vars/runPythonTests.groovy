/**
 * Does the necessary Python installations then runs the test suite.
 */
def call(String image_name, String server_uri = PCIC_DOCKER, \
         String install_args, boolean self_install, String test_args, \
         String python_version = '3') {
    withDockerServer([uri: server_uri]) {
        def pytainer = docker.image(image_name)

        String pip
        if (python_version == '3') {
            pip = 'pip3'
        } else {
            pip = 'pip'
        }

        pytainer.inside('-u root') {
            withEnv(['PIP_INDEX_URL=https://pypi.pacificclimate.org/simple']) {
                sh "${pip} install ${install_args}"

                if (self_install) {
                    sh "${pip} install -e ."
                }
            }

            sh "py.test ${test_args}" 
        }
    }
}
