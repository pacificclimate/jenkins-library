import org.pcic.DockerUtils
import org.pcic.util.Utils


/**
 * Construct a docker image name given the name of the tool
 *
 * @param base name of the tool
 * @param options map containing optional arguments:
 *                dockerRegistry: name of the docker registry to push to
 *                dockerTag: default tag to build the image with
 * @return fully constructed name for docker
 */
def call(String base, Map options = [:]) {
    Utils utils = new Utils(this)
    DockerUtils dockerUtils = new DockerUtils(this)
    Map args = utils.getUpdatedArgs(['dockerRegistry', 'dockerTag'], options)

    return dockerUtils.buildImageName(args.dockerRegistry, base, args.dockerTag)
}
