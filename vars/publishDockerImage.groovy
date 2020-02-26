import org.pcic.GitUtils
import org.pcic.DockerUtils
import org.pcic.util.Utils


/**
 * Publish a docker image with the appropriate tags
 *
 * @param image image object to publish
 * @param credentialsId identification string of credentials in jenkins
 * @param options map containing any of the optional arguments:
 *                serverUri: URI of the server to publish with
 *                registryUrl: URL of the registry
 */
def call(image, credentialsId, Map options = [:]) {
    DockerUtils dockerUtils = new DockerUtils(this)
    Utils utils = new Utils(this)

    Map args = utils.getUpdatedArgs(['serverUri', 'registryUrl'], options)
    ArrayList tags = dockerUtils.getTags()

    withDockerServer([uri: args.serverUri]){
        docker.withRegistry(args.registryUrl, credentialsId) {
            // Publish the default image
            image.push()

            // Then check for other tags we may need to publish
            tags.each { tag ->
                image.push(tag)
            }
        }
    }
}
