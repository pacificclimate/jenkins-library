import pcic.utils

/**
 * Given an image publish it with a tag to the PCIC docker registry.
 *
 * @param image image obeject to publish
 * @param tags list of tags to publish image with
 * @param credentialsId identification string of credentials in jenkins
 * @param argMap map containing any of the optional arguments (serverUri,
 *               registryUrl)
 */
def call(image, ArrayList tags, credentialsId, Map argMap=[:]) {
    Map defaults = [serverUri: PCIC_DOCKER_DEV01, registryUrl: '']
    Map args = utils.applyOptionalParameters(defaults, argMap)

    withDockerServer([uri: args.serverUri]){
        docker.withRegistry(args.registryUrl, credentialsId) {
            tags.each { tag ->
                image.push(tag)
            }
        }
    }
}
