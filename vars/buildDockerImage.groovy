/**
 * Build a docker image given the name and server URI.  By default this function
 * will build this image on docker-dev01.
 *
 * @param image_name the name of the image
 * @param server_uri URI of the docker server to build on
 * @return image the built docker image
 */
def call(String imageName, String serverUri = PCIC_DOCKER_DEV01) {
    def image
    withDockerServer([uri: serverUri]) {
        image = docker.build(imageName)
    }
    return image
}
