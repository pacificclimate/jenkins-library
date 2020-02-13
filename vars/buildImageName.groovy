import org.pcic.DockerUtils
import org.pcic.util.Utils


/**
 * Given the image base name construct a full name
 *
 * @param base name of the tool
 * @return fully constructed name for docker
 */
def call(String base, Map options = [:]) {
    Utils utils = new Utils(this)
    DockerUtils dockerUtils = new DockerUtils(this)

    Map args = utils.getUpdatedArgs(['dockerRegistry', 'dockerTag'], options)

    return dockerUtils.buildImageName(args.dockerRegistry, base, args.dockerTag)
}
