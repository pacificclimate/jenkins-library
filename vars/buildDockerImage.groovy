/**
 * Build a docker image given the name and server URI.  By default this function
 * will build this image on docker-dev01.
 *
 * @param image_name the name of the image
 * @param server_uri URI of the docker server to build on
 * @return image the built docker image
 */
def call(image_name = 'my-image', String server_uri = PCIC_DOCKER_DEV01) {
    def image
    
    withDockerServer([uri: server_uri]) {
        image = docker.build(image_name)
    }

    return image
}
