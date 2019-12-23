import org.pcic.DockerUtils

/**
 * Clean up image on dev01
 *
 * @param image_name name of the image to clean up
 * @param server_uri URI of the docker server to build on
 */
def call(String imageName, String serverUri = PCIC_DOCKER_DEV01) {
    DockerUtils dockerUtils = new DockerUtils(this)
    withDockerServer([uri: serverUri]){
        dockerUtils.removeImage(imageName)
    }
}
