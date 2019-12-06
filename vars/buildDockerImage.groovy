/**
 * Build a docker image given the name
 *
 * @param image_name the name of the image
 * @return image the built docker image
 */
def call(String image_name='my-image', server_uri=PCIC_DOCKER) {
    def image
    withDockerServer([uri: server_uri]) {
        image = docker.build(image_name)
    }

    return image
}
