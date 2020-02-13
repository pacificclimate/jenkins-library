import org.pcic.DockerUtils

/**
 * Remove image from docker-dev01
 *
 * @param image_name name of the image to clean up
 * @param server_uri URI of the docker server to build on
 */
def call(String imageName, String serverUri = PCIC_DOCKER_DEV01) {
    DockerUtils dockerUtils = new DockerUtils(this)

    withDockerServer([uri: serverUri]){
        String id = dockerUtils.getImageId(imageName)
        ArrayList images = dockerUtils.getDeletableImages(imageName)

        for (String image : images) {
            dockerUtils.removeImage(image)
        }

        try {
            dockerUtils.removeImage(id)
        } catch(Exception e) {
            echo "${e}"
        }
    }
}
