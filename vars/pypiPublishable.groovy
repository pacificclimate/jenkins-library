import org.pcic.GitUtils


def call() {
    GitUtils gitUtils = new GitUtils(this)
    def tags = gitUtils.isCommitTagged()

    if (BRANCH_NAME == 'master' && !tags.isEmpty()) {
        return true
    } else {
        return false
    }
}
