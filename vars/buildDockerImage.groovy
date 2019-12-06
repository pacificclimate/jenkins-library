def build_image(image_name, server_uri) {
    def image
    withDockerServer([uri: server_uri]) {
        image = docker.build(image_name)
    }

    return image
}
