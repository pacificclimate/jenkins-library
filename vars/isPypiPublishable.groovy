import org.pcic.GitUtils
import org.pcic.util.Utils

/**
 * Check if the branch is master and tags are present
 *
 * @return boolean
 */
def call() {
    GitUtils gitUtils = new GitUtils(this)
    Utils utils = new Utils(this)

    def tags = gitUtils.isCommitTagged()
    return utils.isPublishable(tags)
}
