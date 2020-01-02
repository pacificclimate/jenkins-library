import org.pcic.GitUtils
import org.pcic.DockerUtils
import org.pcic.util.Utils


/**
 * Given an image publish it with the appropriate tags to the PCIC Docker hub.
 *
 * @param image image obeject to publish
 * @param credentialsId identification string of credentials in jenkins
 * @param params map containing any of the optional arguments:
 *               serverUri: URI of the server to publish with
 *               registryUrl: URL of the registry
 */
def call(image, credentialsId, Map params=[:]) {
    GitUtils gitUtils = new GitUtils(this)
    DockerUtils dockerUtils = new DockerUtils(this)
    Utils utils = new Utils(this)

    Map defaults = [serverUri: PCIC_DOCKER_DEV01, registryUrl: '']
    Map processed = utils.processParams(defaults, params)

    String branch = utils.getBranchName()
    ArrayList gitTags = gitUtils.isCommitTagged()
    ArrayList dockerTags = dockerUtils.determineTags(branch, gitTags)

    withDockerServer([uri: processed.serverUri]){
        docker.withRegistry(processed.registryUrl, credentialsId) {
            dockerTags.each { tag ->
                image.push(tag)
            }
        }
    }
}
