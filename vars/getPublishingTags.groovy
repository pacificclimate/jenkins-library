import org.pcic.GitUtils
import org.pcic.DockerUtils


/**
 * If the master branch has been tagged we also add the `latest` tag.  Otherwise
 * we just use the branch name as the tag.
 *
 * @return dockerTags a list of the tags for the Docker image
 */
def call() {
    GitUtils gitUtils = new GitUtils(this)
    DockerUtils dockerUtils = new DockerUtils(this)

    String name
    if (BRANCH_NAME.contains('PR')) {
        name = CHANGE_BRANCH
    } else {
        name = BRANCH_NAME
    }

    ArrayList gitTags = gitUtils.isCommitTagged()
    ArrayList dockerTags = dockerUtils.getPublishingTags(name, gitTags)
    return dockerTags
}
