import org.pcic.GitUtils


/**
 * Perform a checkout for code and a fetch for tags
 */
def call() {
    GitUtils gitUtils = new GitUtils(this)
    checkout scm
    gitUtils.gitFetch()
}
