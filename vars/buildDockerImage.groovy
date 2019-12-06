def call(String image_name, String server_uri) {
    def image
    withDockerServer([uri: server_uri]) {
        image = docker.build(image_name)
    }

    return image
}
