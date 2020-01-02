import org.pcic.GitUtils


/**
 * Check if the branch is master and tags are present
 *
 * @return boolean
 */
def call() {
    GitUtils gitUtils = new GitUtils(this)
    def tags = gitUtils.isCommitTagged()

    if (BRANCH_NAME == 'master' && !tags.isEmpty()) {
        return true
    } else {
        return false
    }
}
