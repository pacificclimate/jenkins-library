/**
 * Given an image publish it with a tag to the PCIC docker registry.
 *
 * @param image to publish
 * @param tags list of tags to publish image with
 * @param server_uri URI of the docker server to build on
 * @param registry_url URL of a registry
 */
def call(image, ArrayList tags, credentials, String server_uri = PCIC_DOCKER_DEV01, String registry_url = '') {
    withDockerServer([uri: server_uri]){
        docker.withRegistry(registry_url, credentials) {
            tags.each { tag ->
                image.push(tag)
            }
        }
    }
}
