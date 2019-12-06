/**
 * Build a docker image given the name
 *
 * @param image_name the name of the image
 * @return image the built docker image
 */
def call(String image_name='my-image') {
    def image
    withDockerServer([uri: PCIC_DOCKER]) {
        image = docker.build(image_name)
    }

    return image
}
