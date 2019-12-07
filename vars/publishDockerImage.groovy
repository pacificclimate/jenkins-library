/**
 * Given an image publish it with a tag to the PCIC docker registry.
 *
 * @param image to publish
 * @param tags list of tags to publish image with
 * @param server_uri URI of the docker server to build on
 */
def call(image, ArrayList tags, String server_uri = PCIC_DOCKER_DEV01) {
    withDockerServer([uri: server_uri]){
        docker.withRegistry('', 'PCIC_DOCKERHUB_CREDS') {
            tags.each { tag ->
                image.push(tag)
            }
        }
    }
}
