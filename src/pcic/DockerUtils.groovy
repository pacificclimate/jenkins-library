package pcic

import pcic.utils


class DockerUtils implements Serializable {

    private script

    DockerUtils(script) {
        this.script = script
    }


    @NonCPS
    public buildWithServer(String imageName, String serverUri) {
        def image

        this.script.docker.withDockerServer([uri: serverUri]) {
            image = docker.build(imageName, '--pull .')
        }

        return image
    }

    public void publishWithServer(image, ArrayList tags, credentialsId, Map argMap) {
        Map defaults = [serverUri: PCIC_DOCKER_DEV01, registryUrl: '']
        Map args = utils.applyOptionalParameters(defaults, argMap)

        withDockerServer([uri: args.serverUri]){
            docker.withRegistry(args.registryUrl, credentialsId) {
                tags.each { tag ->
                    image.push(tag)
                }
            }
        }
    }

    public void removeFromServer(String imageName, String serverUri) {
        withDockerServer([uri: serverUri]){
            sh "docker rmi ${imageName}"
        }
    }
}
