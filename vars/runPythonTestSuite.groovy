import pcic.utils


def call(String imageName, ArrayList requirementsFiles,  ArrayList pytestArgs, Map argMap=[:]) {
    // collect any optional variables
    Map defaults = [serverUri: PCIC_DOCKER_DEV01,
                    pythonVersion: 3,
                    gitExecInstall: false,
                    buildDocs: false,
                    containerData: 'default',
                    pipIndexUrl:'https://pypi.pacificclimate.org/simple']
    Map args = applyOptionalParameters(defaults, argMap)

    // set up some items used in the commands below
    String pip = getPipString(args.pythonVersion)
    String required = '-r ' + requirementsFiles.join(' -r ')
    String testArgs = pytestArgs.join(' ')
    Map containerDataArgs = ['default': '-u root',
                             'pdp': '-u root --volumes-from pdp_data --env-file /storage/data/projects/comp_support/jenkins/pdp_envs/pdp_deployment.env']

    withDockerServer([uri: args.serverUri]) {
        def pytainer = docker.image(imageName)

        pytainer.inside(containerDataArgs[args.containerData]) {
            if (args.gitExecInstall) {
                sh 'apt-get update'
                sh 'apt-get install -y git'
            }

            withEnv(["PIP_INDEX_URL=${args.pipIndexUrl}"]) {
                sh "${pip} install ${required}"
                sh "${pip} install -e ."
            }

            if (args.buildDocs) {
                sh 'python setup.py install'
                sh 'python setup.py build_sphinx'
                sh 'python setup.py install'
            }

            sh "py.test ${testArgs}"
        }
    }
}