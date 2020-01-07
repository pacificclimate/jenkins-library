import org.pcic.GitHubUtils
import org.pcic.util.Utils

/**
 * Run a stage and send a commit status report to github
 *
 * @param context the name of the stage to be displayed in the commit status
 * @param closure the stage steps to perform
 */
def call(String context, Closure closure) {
    GitHubUtils gitHubUtils = new GitHubUtils(this)
    Utils utils = new Utils(this)

    // Run stage
    stage(context) {
        try {
            gitHubUtils.setBuildStatus(context, 'In progress...', 'PENDING')
            // Run the steps given to this stage
            closure()
            gitHubUtils.setBuildStatus(context, 'Success', 'SUCCESS')
        } catch (Exception e) {
            gitHubUtils.setBuildStatus(context, utils.getExceptionType(e),
                                       'FAILURE')
            this.error(e.toString())
        }
    }
}
