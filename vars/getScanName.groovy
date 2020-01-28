import org.pcic.GitUtils
import org.pcic.DockerUtils
import org.pcic.util.Utils


/**
 * Given the image suffix construct a name that the anchore scanner can use
 *
 * @param imageSuffix suffix for the image name
 * @return scan name for anchore
 */
def call(String imageSuffix) {
    GitUtils gitUtils = new GitUtils(this)
    DockerUtils dockerUtils = new DockerUtils(this)
    Utils utils = new Utils(this)

    String imageName = dockerUtils.buildImageName(imageSuffix)
    String branch = utils.getBranchName()
    ArrayList gitTags = gitUtils.isCommitTagged()
    ArrayList dockerTags = dockerUtils.determineTags(branch, gitTags)

    // `dockerTags` will contain one or more tags.  The image remains the same
    // even with different tags, so we only need to use one to identify the
    // desired image.
    return imageName + ":" + dockerTags[0]
}
