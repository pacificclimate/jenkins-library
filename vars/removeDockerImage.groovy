import org.pcic.DockerUtils

/**
 * Remove docker image from docker server
 *
 * @param imageName name of the image to remove
 * @param serverUri URI of the docker server to build on
 */
def call(String imageName, String serverUri = PCIC_DOCKER_DEV01) {
    DockerUtils dockerUtils = new DockerUtils(this)

    withDockerServer([uri: serverUri]){
        String id = dockerUtils.getImageId(imageName)
        ArrayList images = dockerUtils.getDeletableImages(imageName)

        // Delete images using the name:tag format
        for (String image : images) {
            dockerUtils.removeImage(image)
        }

        /**
         * In some cases when an image that already has a tag gets tagged again
         * a <none> tag is produced.  Images tagged this way can only be
         * removed by using the image id.  We perform this in a try/catch block
         * since <none> images do not always appear.
         */
        try {
            dockerUtils.removeImage(id)
        } catch(Exception e) {
            echo "${e}"
        }
    }
}
