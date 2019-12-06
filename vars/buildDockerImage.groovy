def call(String image_name) {
    def image
    withDockerServer([uri: PCIC_DOCKER]) {
        image = docker.build(image_name)
    }

    return image
}
