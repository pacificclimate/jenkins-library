/**
 * Given an image publish it with a tag to the PCIC docker registry.
 *
 * @param image to publish
 * @param tags list of tags to publish image with
 */
def call(image, tags) {
    withDockerServer([uri: PCIC_DOCKER]){
        docker.withRegistry('', 'PCIC_DOCKERHUB_CREDS') {
            tags.each { tag ->
                image.push(tag)
            }
        }
    }
}
